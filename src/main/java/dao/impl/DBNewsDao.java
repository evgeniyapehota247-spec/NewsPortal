package dao.impl;

import bean.News;
import dao.DaoException;
import dao.NewsDao;
import dao.pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBNewsDao implements NewsDao {

    // Все SQL запросы должны присоединять таблицу user_details
    private static final String SELECT_ALL =
            "SELECT n.*, CONCAT(ud.firstname, ' ', ud.lastname) as author_name " +
                    "FROM news n " +
                    "LEFT JOIN users u ON n.author_id = u.id " +
                    "LEFT JOIN user_details ud ON u.id = ud.users_id " +
                    "ORDER BY n.created_at DESC";

    private static final String SELECT_PAGINATED =
            "SELECT n.*, CONCAT(ud.firstname, ' ', ud.lastname) as author_name " +
                    "FROM news n " +
                    "LEFT JOIN users u ON n.author_id = u.id " +
                    "LEFT JOIN user_details ud ON u.id = ud.users_id " +
                    "ORDER BY n.created_at DESC LIMIT ? OFFSET ?";

    private static final String SELECT_BY_ID =
            "SELECT n.*, CONCAT(ud.firstname, ' ', ud.lastname) as author_name " +
                    "FROM news n " +
                    "LEFT JOIN users u ON n.author_id = u.id " +
                    "LEFT JOIN user_details ud ON u.id = ud.users_id " +
                    "WHERE n.id = ?";

    private static final String SELECT_BY_AUTHOR =
            "SELECT n.*, CONCAT(ud.firstname, ' ', ud.lastname) as author_name " +
                    "FROM news n " +
                    "LEFT JOIN users u ON n.author_id = u.id " +
                    "LEFT JOIN user_details ud ON u.id = ud.users_id " +
                    "WHERE n.author_id = ? " +
                    "ORDER BY n.created_at DESC";

    private static final String INSERT =
            "INSERT INTO news(title, brief, content_path, author_id, news_status_id, publish_date) " +
                    "VALUES(?,?,?,?,?,?)";

    private static final String UPDATE =
            "UPDATE news SET title=?, brief=?, content_path=?, news_status_id=?, publish_date=?, updated_at=NOW() " +
                    "WHERE id=?";

    // Добавьте эту константу DELETE
    private static final String DELETE =
            "DELETE FROM news WHERE id = ?";

    private static final String COUNT_ALL =
            "SELECT COUNT(*) FROM news";

    @Override
    public List<News> findAll(int offset, int limit) throws DaoException {
        List<News> list = new ArrayList<>();

        try (Connection con = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_PAGINATED)) {

            ps.setInt(1, limit);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return list;
    }

    @Override
    public List<News> findByStatus(int statusId) throws DaoException {
        List<News> list = new ArrayList<>();

        String sql = "SELECT n.*, CONCAT(ud.firstname, ' ', ud.lastname) as author_name " +
                "FROM news n " +
                "LEFT JOIN users u ON n.author_id = u.id " +
                "LEFT JOIN user_details ud ON u.id = ud.users_id " +
                "WHERE n.news_status_id = ? " +
                "ORDER BY n.created_at DESC";

        try (Connection con = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, statusId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return list;
    }

    @Override
    public int getTotalCount() throws DaoException {
        try (Connection con = ConnectionPool.getInstance().takeConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(COUNT_ALL)) {

            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<News> topNews(int count) throws DaoException {
        List<News> list = new ArrayList<>();

        String sql = "SELECT n.*, CONCAT(ud.firstname, ' ', ud.lastname) as author_name " +
                "FROM news n " +
                "LEFT JOIN users u ON n.author_id = u.id " +
                "LEFT JOIN user_details ud ON u.id = ud.users_id " +
                "WHERE n.news_status_id = 2 AND n.publish_date <= NOW() " +
                "ORDER BY n.publish_date DESC LIMIT ?";

        try (Connection con = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, count);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return list;
    }

    @Override
    public News findById(int id) throws DaoException {
        try (Connection con = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_ID)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
                return null;
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    // Новый метод для получения новостей по автору
    public List<News> findByAuthorId(int authorId) throws DaoException {
        List<News> list = new ArrayList<>();

        try (Connection con = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_AUTHOR)) {

            ps.setInt(1, authorId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return list;
    }

    @Override
    public void save(News news) throws DaoException {
        try (Connection con = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, news.getTitle());
            ps.setString(2, news.getBrief());
            ps.setString(3, news.getContent_path()); // или news.getContentPath()
            ps.setInt(4, news.getAuthor_id());
            ps.setInt(5, news.getNews_status_id()); // 1 - черновик по умолчанию
            ps.setTimestamp(6, news.getPublish_date() != null ?
                    Timestamp.valueOf(news.getPublish_date()) : Timestamp.valueOf(java.time.LocalDateTime.now()));

            ps.executeUpdate();

            // Получаем сгенерированный ID
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    news.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(News news) throws DaoException {
        System.out.println("Updating news with ID: " + news.getId());
        System.out.println("Title: " + news.getTitle());
        System.out.println("Status: " + news.getNews_status_id());
        System.out.println("Publish date: " + news.getPublish_date());

        try (Connection con = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {

            ps.setString(1, news.getTitle());
            ps.setString(2, news.getBrief());
            ps.setString(3, news.getContent_path());
            ps.setInt(4, news.getNews_status_id());

            if (news.getPublish_date() != null) {
                ps.setTimestamp(5, Timestamp.valueOf(news.getPublish_date()));
            } else {
                ps.setNull(5, Types.TIMESTAMP);
            }

            ps.setInt(6, news.getId());

            int rowsUpdated = ps.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);

        } catch (SQLException e) {
            System.err.println("Error updating news: " + e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        try (Connection con = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = con.prepareStatement(DELETE)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private News map(ResultSet rs) throws SQLException {
        News news = new News();

        news.setId(rs.getInt("id"));
        news.setTitle(rs.getString("title"));
        news.setBrief(rs.getString("brief"));
        news.setContent_path(rs.getString("content_path"));

        // Даты
        Timestamp publishDate = rs.getTimestamp("publish_date");
        if (publishDate != null) {
            news.setPublish_date(publishDate.toLocalDateTime());
        }

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            news.setCreated_at(createdAt.toLocalDateTime());
        }

        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            news.setUpdated_at(updatedAt.toLocalDateTime());
        }

        // Статус и автор
        news.setNews_status_id(rs.getInt("news_status_id"));
        news.setAuthor_id(rs.getInt("author_id"));

        // Добавьте имя автора
//        String authorName = rs.getString("author_name");
//        if (authorName != null) {
//            news.setAuthor_name(authorName);
//        }

        // Преобразуем статус ID в название (исправленный switch для Java 11)
        int statusId = news.getNews_status_id();
        String statusName;
        switch (statusId) {
            case 1:
                statusName = "Черновик";
                break;
            case 2:
                statusName = "Опубликовано";
                break;
            case 3:
                statusName = "На модерации";
                break;
            default:
                statusName = "Неизвестно";
                break;
        }
        news.setStatus_name(statusName);

        return news;
    }
}