package dao.impl;

import bean.News;
import dao.DaoException;
import dao.NewsDao;
import dao.pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBNewsDao implements NewsDao {

    // Имитация базы данных
    private List<News> allNews = new ArrayList<>();

    public DBNewsDao() {
        // Заполняем тестовыми данными
        for (int i = 1; i <= 10; i++) {
            allNews.add(new News(
                    i,
                    "Новость " + i,
                    "Краткое описание новости " + i,
                    "Полное содержание новости " + i
            ));
        }
    }

    @Override
    public List<News> findAll(int offset, int limit) throws DaoException {
        int end = Math.min(offset + limit, allNews.size());
        return allNews.subList(offset, end);
    }

    @Override
    public int getTotalCount() throws DaoException {
        return allNews.size();
    }

    @Override
    public List<News> topNews(int count) throws DaoException {

        List<News> topNews = new ArrayList<News>();

        topNews.add(new News(
                1,
                "QQQРост промышленного производства в Беларуси",
                "AAAЗа последний квардекс наблюдается устойчивый рост промышленного производства на 5.3% по сравнению с аналогичным периодом прошлого года.",
                "content 1"));
        topNews.add(new News(
                2,
                "Открытие нового музея в Минске",
                "В столице открылся современный музей истории Беларуси с интерактивными экспонатами и цифровыми технологиями",
                "content 2"));
        topNews.add(new News(
                3,
                "Белорусские атлеты завоевали медали на международных соревнованиях",
                "На чемпионате Европы по легкой атлетике белорусские спортсмены показали выдающиеся результаты, завоевав 3 золотые медали.",
                "content 3"));
        topNews.add(new News(
                1,
                "QQQРост промышленного производства в Беларуси",
                "AAAЗа последний квардекс наблюдается устойчивый рост промышленного производства на 5.3% по сравнению с аналогичным периодом прошлого года.",
                "content 4")); topNews.add(new News(
                1,
                "QQQРост промышленного производства в Беларуси",
                "AAAЗа последний квардекс наблюдается устойчивый рост промышленного производства на 5.3% по сравнению с аналогичным периодом прошлого года.",
                "content 5")); topNews.add(new News(
                1,
                "QQQРост промышленного производства в Беларуси",
                "AAAЗа последний квардекс наблюдается устойчивый рост промышленного производства на 5.3% по сравнению с аналогичным периодом прошлого года.",
                "content 6")); topNews.add(new News(
                1,
                "QQQРост промышленного производства в Беларуси",
                "AAAЗа последний квардекс наблюдается устойчивый рост промышленного производства на 5.3% по сравнению с аналогичным периодом прошлого года.",
                "content 7"));




        return topNews;
    }

    private static final String SELECT_ALL =
            "SELECT * FROM news ORDER BY created_at DESC";

    private static final String SELECT_BY_ID =
            "SELECT * FROM news WHERE id = ?";

    private static final String INSERT =
            "INSERT INTO news(title, brief, content) VALUES(?,?,?)";

    private static final String UPDATE =
            "UPDATE news SET title=?, brief=?, content=? WHERE id=?";

//    @Override
//    public List<News> findAll() throws DaoException {
//        List<News> list = new ArrayList<>();
//
//        try (Connection con = ConnectionPool.getInstance().takeConnection();
//             Statement st = con.createStatement();
//             ResultSet rs = st.executeQuery(SELECT_ALL)) {
//
//            while (rs.next()) {
//                list.add(map(rs));
//            }
//
//        } catch (SQLException e) {
//            throw new DaoException(e);
//        }
//        return list;
//    }

    @Override
    public News findById(int id) throws DaoException {
        try (Connection con = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_ID)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return map(rs);
            }
            return null;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void save(News news) throws DaoException {
        try (Connection con = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = con.prepareStatement(INSERT)) {

            ps.setString(1, news.getTitle());
            ps.setString(2, news.getBrief());
            ps.setString(3, news.getContent());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(News news) throws DaoException {
        try (Connection con = ConnectionPool.getInstance().takeConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {

            ps.setString(1, news.getTitle());
            ps.setString(2, news.getBrief());
            ps.setString(3, news.getContent());
            ps.setInt(4, news.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private News map(ResultSet rs) throws SQLException {
        return new News(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("brief"),
                rs.getString("content")
        );
    }

    private static final String DELETE =
            "DELETE FROM news WHERE id = ?";

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

}
