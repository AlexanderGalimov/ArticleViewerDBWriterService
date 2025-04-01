package cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;

@Getter
@Setter
@AllArgsConstructor
public class PDFParams {

    private String link;

    @Indexed(unique = true)
    private String title;
}
