package repository;

import bean.News;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository {

    List<News> findAll(int offset, int limit);

    Optional<News> findById(int id);

    List<News> findByAuthorId(int authorId);

    List<News> findByStatus(int statusId);

    List<News> findTopNews(int count);

    void save(News news);

    void update(News news);

    void delete(int id);

    int countAll();

}