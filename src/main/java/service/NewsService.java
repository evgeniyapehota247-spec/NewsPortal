package service;

import bean.News;

import java.util.List;

public interface NewsService {

    List<News> takeTopNews(int count) throws ServiceException;

    List<News> findAllNews(int page, int pageSize) throws ServiceException;

    int getTotalPages(int pageSize) throws ServiceException;

    int getTotalNewsCount() throws ServiceException;

    List<News> getAll() throws ServiceException;

    News getById(int id) throws ServiceException;

    List<News> findByAuthorId(int authorId) throws ServiceException;

    List<News> findByStatus(int statusId) throws ServiceException;

    List<News> findPublishedNews(int page, int pageSize) throws ServiceException;

    void create(News news) throws ServiceException;

    void update(News news) throws ServiceException;

    void delete(int id) throws ServiceException;
}