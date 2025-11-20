package service;

import bean.News;

import java.util.List;

public interface NewsService {

    List<News> takeTopNews(int count) throws ServiceException;

}
