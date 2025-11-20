package service.impl;

import bean.News;
import dao.DaoException;
import dao.DaoProvider;
import dao.NewsDao;
import service.NewsService;
import service.ServiceException;

import java.util.List;

public class NewsServiceImpl implements NewsService {

    private final NewsDao newsDao = DaoProvider.getInstance().getNewsDao();

    private final int MAX_AVAILABLE_TOP_NEWS = 10;

    @Override
    public List<News> takeTopNews(int count) throws ServiceException {

        if(count <=0 || count > MAX_AVAILABLE_TOP_NEWS){
            throw new ServiceException("Error message");
        }

        try {
            return newsDao.topNews(count);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
