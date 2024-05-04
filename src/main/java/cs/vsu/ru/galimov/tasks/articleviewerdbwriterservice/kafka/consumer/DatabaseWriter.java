package cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.kafka.consumer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.component.PdfSaver;
import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.mapper.ArticleMapper;
import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.mapper.AuthorMapper;
import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.minio.MinioTemplate;
import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.model.Article;
import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.model.Author;
import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.service.impl.ArticleServiceImpl;
import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.service.impl.AuthorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Slf4j
@Component
public class DatabaseWriter {

    private final ArticleServiceImpl articleService;

    private final AuthorServiceImpl authorService;

    private final MinioTemplate minioTemplate;

    private final ArticleMapper articleMapper;

    private final AuthorMapper authorMapper;

    private final PdfSaver pdfSaver;

    private final Gson gson = new Gson();

    @Autowired
    public DatabaseWriter(ArticleServiceImpl service, AuthorServiceImpl authorService, MinioTemplate minioTemplate, ArticleMapper articleMapper, AuthorMapper authorMapper, PdfSaver pdfSaver) {
        this.articleService = service;
        this.authorService = authorService;
        this.minioTemplate = minioTemplate;
        this.articleMapper = articleMapper;
        this.authorMapper = authorMapper;
        this.pdfSaver = pdfSaver;
    }

    @KafkaListener(topics = "${kafka.topic.name.for-input-topic}", containerFactory = "kafkaListenerContainerFactory", concurrency = "${kafka.topic.partitions.for-input-topic}")
    public void receive(String articleJson) {
        try {
            JsonObject jsonObject = gson.fromJson(articleJson, JsonObject.class);
            Article article = articleMapper.convertJsonToArticle(jsonObject);
            List<Author> authors = authorMapper.convertJsonToAuthor(jsonObject);

            article.setAuthorIds(new ArrayList<>());
            for (Author author: authors){
                Author savedAuthor = authorService.insert(author);
                article.getAuthorIds().add(savedAuthor.getId());
            }

            String nameForS3 = UUID.randomUUID() + ".pdf";

            article.setUniqUIIDS3(nameForS3);
            articleService.insert(article);

            String link = article.getPdfParams().getLink();
            byte[] serializedPdfParams = serializeS3Pdf(link);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(serializedPdfParams);

            minioTemplate.uploadFile(nameForS3, inputStream);

        } catch (Exception e) {
            System.out.println("Error in kafka listen" + e.getMessage());
        }
    }

    public byte[] serializeS3Pdf(String s3PdfLink) {
        try {
            InputStream pdfInputStream = pdfSaver.openPdfStream(s3PdfLink);

            return IOUtils.toByteArray(pdfInputStream);
        } catch (Exception e) {
            System.out.println("Error in serialize pdf: " + e.getMessage());
            return null;
        }
    }
}
