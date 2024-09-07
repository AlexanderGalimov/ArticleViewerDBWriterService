package cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice;

import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.kafka.producer.S3ProcessingProducer;
import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.model.Article;
import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.service.ArticleService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class ArticleViewerDbWriterServiceApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ArticleViewerDbWriterServiceApplication.class, args);

        S3ProcessingProducer producer = context.getBean(S3ProcessingProducer.class);
        ArticleService service = context.getBean(ArticleService.class);

        List<Article> articleList = service.findAll();
        for (Article article: articleList) {
            producer.send("s3Topic", article.getUniqUIIDS3());
        }
    }

}
