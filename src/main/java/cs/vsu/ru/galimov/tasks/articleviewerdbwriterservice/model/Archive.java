package cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Archive {
    private String link;

    private ArchiveType type;
}
