package cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.kafka.topic;

import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.properties.KafkaS3TopicProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@RequiredArgsConstructor
@Configuration
public class S3Topic {

    private final KafkaS3TopicProperties kafkaS3TopicProperties;

    @Bean
    public NewTopic createS3ProcessingTopic() {
        return new NewTopic(kafkaS3TopicProperties.getName(),
                kafkaS3TopicProperties.getPartitions(),
                kafkaS3TopicProperties.getReplicationFactor());
    }
}
