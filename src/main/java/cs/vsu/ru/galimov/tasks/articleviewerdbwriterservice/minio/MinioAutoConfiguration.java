package cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.minio;

import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.properties.MinioProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MinioAutoConfiguration {

    private final MinioProperties properties;

    @Bean
    public MinioTemplate template() {
        return new MinioTemplate(properties);
    }
}


