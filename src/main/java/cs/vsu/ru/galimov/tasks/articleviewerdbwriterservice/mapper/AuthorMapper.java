package cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.mapper;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.model.Author;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthorMapper {
    public List<Author> convertJsonToAuthor(JsonObject object){
        List<Author> authors = new ArrayList<>();

        JsonObject pdfParamsObject = object.getAsJsonObject("pdfParams");

        JsonArray authorsArray = pdfParamsObject.getAsJsonArray("authors");

        List<String> stringAuthorsNames = new ArrayList<>();
        for (JsonElement element : authorsArray) {
            stringAuthorsNames.add(element.getAsString());
        }

        for (String authorName: stringAuthorsNames){
            authors.add(new Author(authorName));
        }

        return authors;
    }
}
