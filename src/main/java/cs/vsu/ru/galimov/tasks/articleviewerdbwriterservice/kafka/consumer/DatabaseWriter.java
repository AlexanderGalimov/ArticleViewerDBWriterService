package cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.kafka.consumer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.component.PdfSaver;
import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.kafka.producer.S3ProcessingProducer;
import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.mapper.ArticleMapper;
import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.mapper.AuthorMapper;
import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.minio.MinioTemplate;
import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.model.Article;
import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.model.Author;
import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.properties.KafkaS3TopicProperties;
import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.service.impl.ArticleServiceImpl;
import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.service.impl.AuthorServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class DatabaseWriter {

    private final ArticleServiceImpl articleService;

    private final AuthorServiceImpl authorService;

    private final MinioTemplate minioTemplate;

    private final ArticleMapper articleMapper;

    private final AuthorMapper authorMapper;

    private final PdfSaver pdfSaver;

    private final Gson gson = new Gson();

    private final S3ProcessingProducer producer;

    private final KafkaS3TopicProperties s3TopicProperties;

    @KafkaListener(topics = "${kafka.input-topic.name}",
            containerFactory = "kafkaListenerContainerFactory",
            concurrency = "${kafka.input-topic.partitions}")
    public void receive(String articleJson) {
        try {
            JsonObject jsonObject = gson.fromJson(articleJson, JsonObject.class);
            Article article = articleMapper.convertJsonToArticle(jsonObject);
            List<Author> authors = authorMapper.convertJsonToAuthor(jsonObject);

            if (articleService.findByPdfParamsTitle(article.getPdfParams().getTitle()) == null && !Objects.equals(article.getPdfParams().getTitle(), "ПРАВИЛА ПУБЛИКАЦИИ ДЛЯ АВТОРОВ")) {
                article.setAuthorIds(new ArrayList<>());
                for (Author author : authors) {
                    Author authorInBase = authorService.findByName(author.getName());
                    if (authorInBase != null) {
                        article.getAuthorIds().add(authorInBase.getId());
                    } else {
                        Author savedAuthor = authorService.insert(author);
                        article.getAuthorIds().add(savedAuthor.getId());
                    }
                }

                String nameForS3 = UUID.randomUUID() + ".pdf";

                article.setUniqUIIDS3(nameForS3);
                articleService.insert(article);

                String link = article.getPdfParams().getLink();
                byte[] serializedPdfParams = serializeS3Pdf(link);
                ByteArrayInputStream inputStream = new ByteArrayInputStream(serializedPdfParams);

                minioTemplate.uploadFile(nameForS3, inputStream);

                producer.send(s3TopicProperties.getName(), nameForS3);
            }
        } catch (Exception e) {
            log.error("Error in kafka listen" + e.getMessage());
        }
    }

    public byte[] serializeS3Pdf(String s3PdfLink) {
        try {
            InputStream pdfInputStream = pdfSaver.openPdfStream(s3PdfLink);

            return IOUtils.toByteArray(pdfInputStream);
        } catch (Exception e) {
            log.error("Error in serialize pdf: " + e.getMessage());
            return null;
        }
    }
}
