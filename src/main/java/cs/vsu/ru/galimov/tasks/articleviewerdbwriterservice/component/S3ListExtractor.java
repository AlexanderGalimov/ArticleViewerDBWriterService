package cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.component;

import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.minio.MinioTemplate;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Component
public class S3ListExtractor {

    private final MinioTemplate template;

    @Autowired
    public S3ListExtractor(MinioTemplate template) {
        this.template = template;
    }


    public List<String> extractS3List() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        List<String> names = new ArrayList<>();
        int RESTARTS = 10;
        int DELAY = 1000;
        for (int i = 0; i < RESTARTS; i++) {
            names = template.getAllObjects();
            if (names.isEmpty()) {
                try {
                    Thread.sleep(DELAY);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                break;
            }
        }

        return names;
    }

}
