package cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.model;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;


@Data
public class PDFParams {
    private String link;

    @Indexed(unique = true)
    private String title;

    public PDFParams(String link, String title) {
        this.link = link;
        this.title = title;
    }

    public PDFParams() {
    }
}
