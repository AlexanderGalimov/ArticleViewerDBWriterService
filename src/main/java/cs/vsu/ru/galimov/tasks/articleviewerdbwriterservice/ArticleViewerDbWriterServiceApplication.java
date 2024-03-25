package cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice;

import io.minio.errors.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.component.Runner;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class ArticleViewerDbWriterServiceApplication {

    public static void main(String[] args) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        ApplicationContext context = SpringApplication.run(ArticleViewerDbWriterServiceApplication.class, args);

        Runner runner = context.getBean(Runner.class);

        runner.run();
    }

}
