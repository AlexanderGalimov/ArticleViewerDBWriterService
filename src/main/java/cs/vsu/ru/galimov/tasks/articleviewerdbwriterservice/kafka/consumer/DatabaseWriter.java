package cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.kafka.consumer;

import com.google.gson.Gson;
import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.minio.MinioTemplate;
import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.model.Article;
import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.UUID;


@Slf4j
@Component
public class DatabaseWriter {

    private final ArticleService service;

    private final MinioTemplate minioTemplate;

    private final Gson gson;

    @Autowired
    public DatabaseWriter(ArticleService service, MinioTemplate minioTemplate, Gson gson) {
        this.service = service;
        this.minioTemplate = minioTemplate;
        this.gson = gson;
    }

    @KafkaListener(topics = "${kafka.topic.name.for-input-topic}", containerFactory = "kafkaListenerContainerFactory", concurrency = "${kafka.topic.partitions.for-input-topic}")
    public void receive(String articleJson) {
        try {
            String nameForS3 = UUID.randomUUID().toString();

            Article article = convertJsonToArticle(articleJson);
            article.setUniqUIIDS3(nameForS3);
            service.insert(article);

            String link = article.getPdfParams().getLink();
            byte[] serializedPdfParams = serializeS3Pdf(link);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(serializedPdfParams);

            minioTemplate.uploadFile(nameForS3, inputStream);

        } catch (Exception e) {
            System.out.println("Error in kafka listen" + e.getMessage());
        }
    }

    private Article convertJsonToArticle(String articleJson) {
        return gson.fromJson(articleJson, Article.class);
    }

    public byte[] serializeS3Pdf(String s3PdfLink) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(s3PdfLink);
        }
        catch (Exception e){
            System.out.println("Error in serialize json listen" + e.getMessage());
        }
        return outputStream.toByteArray();
    }

}
