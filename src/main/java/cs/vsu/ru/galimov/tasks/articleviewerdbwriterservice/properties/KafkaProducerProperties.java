package cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "kafka.producer")
@Component
public class KafkaProducerProperties {

    private int retries;

    private int batchSize;

    private int linger;

    private int bufferMemory;
}
