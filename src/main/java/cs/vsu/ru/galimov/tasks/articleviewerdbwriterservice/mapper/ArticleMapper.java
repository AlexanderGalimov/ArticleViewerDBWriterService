package cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.mapper;

import com.google.gson.JsonObject;
import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.model.*;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {
    public Article convertJsonToArticle(JsonObject jsonObject) {
        JsonObject magazineJson = jsonObject.getAsJsonObject("magazine");
        String magazineName = magazineJson.get("name").getAsString();
        String magazineLink = magazineJson.get("link").getAsString();
        JsonObject departmentMagazineJson = jsonObject.getAsJsonObject("departmentMagazine");
        String departmentMagazineName = departmentMagazineJson.get("name").getAsString();
        String departmentMagazineUrl = departmentMagazineJson.get("url").getAsString();
        String departmentMagazineType = departmentMagazineJson.get("type").getAsString();
        JsonObject dateArchiveJson = jsonObject.getAsJsonObject("dateArchive");
        String dateArchiveInfo = dateArchiveJson.get("info").getAsString();
        String dateArchiveLink = dateArchiveJson.get("link").getAsString();
        JsonObject pdfParamsJson = jsonObject.getAsJsonObject("pdfParams");
        String pdfLink = pdfParamsJson.get("link").getAsString();
        String pdfTitle = pdfParamsJson.get("title").getAsString();

        Article article = new Article();
        article.setMagazine(new Magazine(magazineName, magazineLink));
        article.setDepartmentMagazine(new DepartmentMagazine(departmentMagazineName, departmentMagazineUrl, departmentMagazineType));
        article.setDateArchive(new DateArchive(dateArchiveInfo, dateArchiveLink));
        article.setPdfParams(new PDFParams(pdfLink, pdfTitle));
        article.setFullText("");
        return article;
    }
}
