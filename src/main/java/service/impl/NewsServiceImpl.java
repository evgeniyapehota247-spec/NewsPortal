package service.impl;

import bean.News;
import dao.DaoException;
import dao.DaoProvider;
import dao.NewsDao;
import service.NewsService;
import service.ServiceException;

import java.util.List;
import java.util.stream.Collectors;

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
        }
    }

    @Override
    public List<News> getAll() throws ServiceException {
        try {
            // Возвращаем все новости (без пагинации)
            // Если в DAO нет метода findAll(), создайте его или используйте существующий
            return newsDao.findAll(0, Integer.MAX_VALUE);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public News getById(int id) throws ServiceException {
        try {
            return newsDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<News> findByAuthorId(int authorId) throws ServiceException {
        try {
            return newsDao.findByAuthorId(authorId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<News> findByStatus(int statusId) throws ServiceException {
        try {
            return newsDao.findByStatus(statusId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<News> findPublishedNews(int page, int pageSize) throws ServiceException {
        try {
            int offset = (page - 1) * pageSize;
            // Получаем все опубликованные новости (статус = 2)
            List<News> allPublished = newsDao.findByStatus(2);

            // Применяем пагинацию
            int start = Math.min(offset, allPublished.size());
            int end = Math.min(offset + pageSize, allPublished.size());

            if (start >= end) {
                return List.of();
            }

            return allPublished.subList(start, end);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void create(News news) throws ServiceException {
        try {
            newsDao.save(news);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
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

    // Дополнительные методы для удобства

    /**
     * Получить опубликованные новости автора
     */
    public List<News> findPublishedByAuthor(int authorId) throws ServiceException {
        try {
            List<News> authorNews = newsDao.findByAuthorId(authorId);
            // Фильтруем только опубликованные (статус = 2)
            return authorNews.stream()
                    .filter(news -> news.getNews_status_id() == 2)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Получить черновики автора
     */
    public List<News> findDraftsByAuthor(int authorId) throws ServiceException {
        try {
            List<News> authorNews = newsDao.findByAuthorId(authorId);
            // Фильтруем только черновики (статус = 1)
            return authorNews.stream()
                    .filter(news -> news.getNews_status_id() == 1)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Получить общее количество новостей автора
     */
    public int getCountByAuthor(int authorId) throws ServiceException {
        try {
            List<News> authorNews = newsDao.findByAuthorId(authorId);
            return authorNews.size();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Получить количество опубликованных новостей автора
     */
    public int getPublishedCountByAuthor(int authorId) throws ServiceException {
        try {
            List<News> authorNews = newsDao.findByAuthorId(authorId);
            return (int) authorNews.stream()
                    .filter(news -> news.getNews_status_id() == 2)
                    .count();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}