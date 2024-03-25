package cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.repository;

import cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.model.Article;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends MongoRepository<Article, String> {
    Article findByPdfParamsTitle(String title);

    Article findByUniqUIIDS3(String uniqUIIDS3);
}
