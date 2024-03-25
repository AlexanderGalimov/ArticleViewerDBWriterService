package cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.kafka.producer;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class S3ProcessingProducer {

    @Qualifier("kafkaTemplate")
    private final KafkaTemplate<String, String> kafkaTemplate;

    private final Gson gson;

    @Autowired
    public S3ProcessingProducer(KafkaTemplate<String, String> kafkaTemplate, Gson gson) {
        this.kafkaTemplate = kafkaTemplate;
        this.gson = gson;
    }

    public void send(String topic, String name) {
        String nameToJson = convertNameToJson(name);
        System.out.println("sent");
        kafkaTemplate.send(topic, nameToJson);
    }

    private String convertNameToJson(String name) {
        return gson.toJson(name);
    }
}

