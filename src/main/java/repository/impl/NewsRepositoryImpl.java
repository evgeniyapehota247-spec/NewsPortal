package repository.impl;

import bean.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import repository.NewsRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class NewsRepositoryImpl implements NewsRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<News> newsRowMapper = new RowMapper<News>() {
        @Override
        public News mapRow(ResultSet rs, int rowNum) throws SQLException {

            News news = new News();
            news.setId(rs.getInt("id"));
            news.setTitle(rs.getString("title"));
            news.setContent_path(rs.getString("content_path"));
            news.setNews_status_id(rs.getInt("news_status_id"));
            news.setAuthor_id(rs.getInt("author_id"));

            Timestamp createdAt = rs.getTimestamp("created_at");
            if (createdAt != null) {
                news.setCreated_at(createdAt.toLocalDateTime());
            }

            Timestamp updatedAt = rs.getTimestamp("updated_at");
            if (updatedAt != null) {
                news.setUpdated_at(updatedAt.toLocalDateTime());
            }

            Timestamp publishDate = rs.getTimestamp("publish_date");
            if (publishDate != null) {
                news.setPublish_date(publishDate.toLocalDateTime());
            }

            return news;
        }
    };

    @Override
    public List<News> findAll(int offset, int limit) {
        String sql = "SELECT * FROM portal.news ORDER BY created_at DESC LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, newsRowMapper, limit, offset);
    }

    @Override
    public List<News> findTopNews(int count) {
        String sql = "SELECT * FROM portal.news WHERE news_status_id = 2 " +
                "ORDER BY publish_date DESC LIMIT ?";
        return jdbcTemplate.query(sql, newsRowMapper, count);
    }

    @Override
    public List<News> findByAuthorId(int authorId) {
        String sql = "SELECT * FROM portal.news WHERE author_id = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, newsRowMapper, authorId);
    }

    @Override
    public List<News> findByStatus(int statusId) {
        String sql = "SELECT * FROM portal.news WHERE news_status_id = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, newsRowMapper, statusId);
    }

    @Override
    public Optional<News> findById(int id) {
        String sql = "SELECT * FROM portal.news WHERE id = ?";
        try {
            News news = jdbcTemplate.queryForObject(sql, newsRowMapper, id);
            return Optional.ofNullable(news);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(News news) {
        String sql = "INSERT INTO portal.news (" +
                "title, " +
                "brief, " +
                "content_path, " +
                "publish_date," +
                "news_status_id, " +
                "created_at, " +
                "updated_at, " +
                "author_id )" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                news.getTitle(),
                news.getBrief(),
                news.getContent_path(),
                news.getPublish_date() != null ? Timestamp.valueOf(news.getPublish_date()) : null,
                news.getNews_status_id() != null ? news.getNews_status_id() : 1,
                news.getCreated_at() != null ? Timestamp.valueOf(news.getCreated_at()) : Timestamp.valueOf(LocalDateTime.now()),
                news.getUpdated_at() != null ? Timestamp.valueOf(news.getUpdated_at()) : Timestamp.valueOf(LocalDateTime.now()),
                news.getAuthor_id()
        );
    }

    @Override
    public void update(News news) {
        String sql = "UPDATE portal.news SET " +
                "title = ?, " +
                "brief = ?, " +
                "content_path = ?, " +
                "publish_date = ?, " +
                "news_status_id = ?, " +
                "updated_at = ?," +
                "author_id = ?, " +
                "status_name = ?  " +
                "WHERE id = ?";

        jdbcTemplate.update(sql,
                news.getTitle(),
                news.getBrief(),
                news.getContent_path(),
                news.getPublish_date() != null ? Timestamp.valueOf(news.getPublish_date()) : null,
                news.getNews_status_id(),
                Timestamp.valueOf(LocalDateTime.now()),
                news.getAuthor_id(),
                news.getStatus_name(),
                news.getId()
        );
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM portal.news WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public int countAll() {
        String sql = "SELECT COUNT(*) FROM portal.news";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count != null ? count : 0;
    }
}