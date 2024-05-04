package cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.repository;

import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.model.Author;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends MongoRepository<Author, String> {
    Author findByName(String name);
}
