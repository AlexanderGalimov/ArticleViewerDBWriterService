package cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.service;

import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.model.Author;

import java.util.List;

public interface AuthorService {
    Author insert(Author author);

    List<Author> findAll();

    void delete(String id);

    Author findById(String id);

    Author update(Author author);

    Author findByName(String name);
}
