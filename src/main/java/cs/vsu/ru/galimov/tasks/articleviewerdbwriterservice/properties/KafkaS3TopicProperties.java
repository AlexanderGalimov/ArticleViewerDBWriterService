package cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "kafka.s3-topic")
@Component
public class KafkaS3TopicProperties {

    private String name;

    private int partitions;

    private short replicationFactor;
}
