package cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.component;

import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.kafka.producer.S3ProcessingProducer;
import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.kafka.topic.S3ProcessingTopic;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Component
@Configuration
public class Runner {

    private final S3ProcessingProducer producer;

    private final S3ListExtractor extractor;

    private final S3ProcessingTopic topic;

    @Autowired
    public Runner(S3ProcessingProducer producer, S3ListExtractor extractor, S3ProcessingTopic topic) {
        this.producer = producer;
        this.extractor = extractor;
        this.topic = topic;
    }

    public void run() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        List<String> names = extractor.extractS3List();
        sendMessagesToKafka(names);
    }

    public void sendMessagesToKafka(List<String> names) {
        for (String name : names) {
            System.out.println("sent " + name);
            producer.send(topic.getTopicName(), name);
        }
    }
}
