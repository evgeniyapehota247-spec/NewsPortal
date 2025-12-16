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
        try {
            return newsDao.topNews(count);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<News> findAllNews(int page, int pageSize) throws ServiceException {
        try {
            int offset = (page - 1) * pageSize;
            return newsDao.findAll(offset, pageSize);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getTotalPages(int pageSize) throws ServiceException {
        try {
            int totalCount = newsDao.getTotalCount();
            return (int) Math.ceil((double) totalCount / pageSize);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getTotalNewsCount() throws ServiceException {
        try {
            return newsDao.getTotalCount();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }}

    @Override
    public List<News> getAll() throws ServiceException {
        return List.of();
    }

//    public List<News> getAll() throws ServiceException {
//        try {
//            return newsDao.findAll();
//        } catch (DaoException e) {
//            throw new ServiceException(e);
//        }
//    }

    public News getById(int id) throws ServiceException {
        try {
            return newsDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void create(News news) throws ServiceException {
        try {
            newsDao.save(news);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void update(News news) throws ServiceException {
        try {
            newsDao.update(news);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(int id) throws ServiceException {
        try {
            newsDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

}
