package service;

import bean.News;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NewsService {

    List<News> findAllNews(int page, int pageSize);

    List<News> findTopNews(int count);

    List<News> findByAuthorId(int authorId);

    List<News> findByStatus(int statusId);

    News findById(int id);

    void save(News news);

    void update(News news);

    void delete(int id);

    int getTotalCount();

    int getTotalPages(int pageSize);

}