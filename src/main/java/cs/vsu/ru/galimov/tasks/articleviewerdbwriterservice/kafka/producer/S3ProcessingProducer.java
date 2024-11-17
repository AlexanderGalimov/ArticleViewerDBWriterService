package cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.kafka.producer;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class S3ProcessingProducer {

    @Qualifier("kafkaTemplate")
    private final KafkaTemplate<String, String> kafkaTemplate;

    private final Gson gson;

    private final Logger logger = LoggerFactory.getLogger(S3ProcessingProducer.class);

    @Autowired
    public S3ProcessingProducer(KafkaTemplate<String, String> kafkaTemplate, Gson gson) {
        this.kafkaTemplate = kafkaTemplate;
        this.gson = gson;
    }

    public void send(String topic, String name) {
        String nameToJson = convertNameToJson(name);
        kafkaTemplate.send(topic, nameToJson);
        logger.info("Article with name: " + name + " successfully sent");
    }

    private String convertNameToJson(String name) {
        return gson.toJson(name);
    }
}

