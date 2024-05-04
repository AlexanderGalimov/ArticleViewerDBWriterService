package cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Authors")
public class Author {
    @Id
    private String id;

    private String name;

    public Author(String name) {
        this.name = name;
    }

    public Author() {
    }
}
