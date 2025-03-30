package cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3ProcessingProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, String uniqueName) {
        kafkaTemplate.send(topic, uniqueName);
        log.info("Article with name {} successfully sent", uniqueName);
    }
}

