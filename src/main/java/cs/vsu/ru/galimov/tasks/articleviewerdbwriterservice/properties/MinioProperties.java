package cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

    private String url;

    private String accessKey;

    private String secretKey;

    private String bucketName;
}
