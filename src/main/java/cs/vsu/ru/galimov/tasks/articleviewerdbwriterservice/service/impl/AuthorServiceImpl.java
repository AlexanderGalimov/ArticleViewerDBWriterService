package cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.service.impl;

import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.model.Author;
import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.repository.AuthorRepository;
import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;

    @Override
    public Author insert(Author author) {
        return repository.insert(author);
    }

    @Override
    public List<Author> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(String id) {
        Author author = findById(id);
        if (author != null) {
            repository.delete(author);
        }
    }

    @Override
    public Author findById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Author update(Author author) {
        return repository.save(author);
    }

    @Override
    public Author findByName(String name) {
        return repository.findByName(name);
    }
}
