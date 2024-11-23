package cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ArticleViewerDbWriterServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArticleViewerDbWriterServiceApplication.class, args);
    }
}
