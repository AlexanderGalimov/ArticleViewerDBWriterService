package cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.service;

import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.model.Article;

import java.util.List;

public interface ArticleService {
    Article insert(Article article);

    List<Article> findAll();

    void delete(String id);

    Article findById(String id);

    Article update(Article object);

    Article findByPdfParamsTitle(String title);
}
