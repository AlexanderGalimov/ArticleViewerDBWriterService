package cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Authors")
public class Author {
    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    public Author(String name) {
        this.name = name;
    }
}
