package service;

import bean.News;

import java.util.List;

public interface NewsService {

    List<News> takeTopNews(int count) throws ServiceException;

    // Добавляем методы пагинации
    List<News> findAllNews(int page, int pageSize) throws ServiceException;
    int getTotalPages(int pageSize) throws ServiceException;
    int getTotalNewsCount() throws ServiceException;
    List<News> getAll() throws ServiceException;

    News getById(int id) throws ServiceException;

    void create(News news) throws ServiceException;

    void update(News news) throws ServiceException;

    void delete(int id) throws ServiceException;


}
