package dao;

import bean.News;

import java.util.List;

public interface NewsDao {

    List<News> topNews(int count) throws DaoException;
}
