package service.impl;

import bean.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.NewsRepository;
import service.NewsService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public List<News> findAllNews(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return newsRepository.findAll(offset, pageSize);
    }

    @Override
    public List<News> findTopNews(int count) {
        return newsRepository.findTopNews(count);
    }

    @Override
    public List<News> findByAuthorId(int authorId) {
        return newsRepository.findByAuthorId(authorId);
    }

    @Override
    public List<News> findByStatus(int statusId) {
        return newsRepository.findByStatus(statusId);
    }

    @Override
    public News findById(int id) {
        Optional<News> newsOptional = newsRepository.findById(id);
        return newsOptional.orElse(null);
    }

    @Override
    public void save(News news) {
        newsRepository.save(news);
    }

    @Override
    public void update(News news) {
        newsRepository.update(news);
    }

    @Override
    public void delete(int id) {
        newsRepository.delete(id);
    }

    @Override
    public int getTotalCount() {
        return newsRepository.countAll();
    }

    @Override
    public int getTotalPages(int pageSize) {
        int totalCount = getTotalCount();
        return (int) Math.ceil((double) totalCount / pageSize);
    }

}