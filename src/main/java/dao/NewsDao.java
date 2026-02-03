package dao;

import bean.News;

import java.util.List;

public interface NewsDao {

    List<News> topNews(int count) throws DaoException;

    List<News> findAll(int offset, int limit) throws DaoException;

    int getTotalCount() throws DaoException;

    News findById(int id) throws DaoException;

    List<News> findByAuthorId(int authorId) throws DaoException;

    List<News> findByStatus(int statusId) throws DaoException; // добавлен этот метод

    void save(News news) throws DaoException;

    void update(News news) throws DaoException;

    void delete(int id) throws DaoException;

}