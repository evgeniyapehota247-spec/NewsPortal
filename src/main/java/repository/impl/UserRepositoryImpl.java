package repository.impl;

import bean.User;
import bean.UserDetails;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import repository.UserRepository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> {

        User user = new User();
        user.setId(rs.getInt("id"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setUserStatusId(rs.getInt("user_status_id"));
        user.setRoleId(rs.getInt("roles_id"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            user.setCreatedAt(createdAt.toLocalDateTime());
        }

        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            user.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        user.setRememberToken(rs.getString("remember_token"));

        UserDetails details = new UserDetails();
        details.setUserId(user.getId());
        details.setFirstName(rs.getString("firstname"));
        details.setLastName(rs.getString("lastname"));

        Date dob = rs.getDate("dob");
        if (dob != null) {
            details.setDob(dob.toLocalDate());
        }

        user.setUserDetails(details);
        return user;
    };

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT " +
                "u.id, u.email, u.password, u.user_status_id, u.roles_id, " +
                "u.created_at, u.updated_at, u.remember_token, " +
                "ud.firstname, ud.lastname, ud.dob " +
                "FROM portal.users u " +
                "LEFT JOIN portal.user_details ud ON u.id = ud.users_id " +
                "WHERE u.email = ?";

        try {
            User user = jdbcTemplate.queryForObject(sql, userRowMapper, email);
            return Optional.ofNullable(user);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public boolean create(User user) {
        try {
            if (existsByEmail(user.getEmail())) {
                throw new RuntimeException("Пользователь с email '" + user.getEmail() + "' уже существует");
            }

            String salt = BCrypt.gensalt();
            String hashedPassword = BCrypt.hashpw(user.getPassword(), salt);

            String userSql = "INSERT INTO portal.users (email, password, user_status_id, roles_id, created_at, updated_at) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getEmail());
                ps.setString(2, hashedPassword);
                ps.setInt(3, user.getUserStatusId() != null ? user.getUserStatusId() : 1);
                ps.setInt(4, user.getRoleId() != null ? user.getRoleId() : 1);
                ps.setTimestamp(5, Timestamp.valueOf(
                        user.getCreatedAt() != null ? user.getCreatedAt() : LocalDateTime.now()));
                ps.setTimestamp(6, Timestamp.valueOf(
                        user.getUpdatedAt() != null ? user.getUpdatedAt() : LocalDateTime.now()));
                return ps;
            }, keyHolder);

            int userId = keyHolder.getKey().intValue();

            UserDetails details = user.getUserDetails();
            if (details != null) {
                String detailsSql = "INSERT INTO portal.user_details (users_id, firstname, lastname, dob) " +
                        "VALUES (?, ?, ?, ?)";

                jdbcTemplate.update(detailsSql,
                        userId,
                        details.getFirstName(),
                        details.getLastName(),
                        details.getDob() != null ? Date.valueOf(details.getDob()) : null
                );
            }

            return true;

        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании пользователя: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean checkPassword(String email, String password) {
        String sql = "SELECT password FROM portal.users WHERE email = ?";

        try {
            String storedHash = jdbcTemplate.queryForObject(sql, String.class, email);

            if (storedHash == null) {
                return false;
            }

            return BCrypt.checkpw(password, storedHash);
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public void saveRememberToken(int userId, String token) {
        String sql = "UPDATE portal.users SET remember_token = ? WHERE id = ?";
        jdbcTemplate.update(sql, token, userId);
    }

    @Override
    public String getRememberToken(int userId) {
        String sql = "SELECT remember_token FROM portal.users WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, String.class, userId);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM portal.users WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public Optional<User> findById(int id) {
        String sql = "SELECT " +
                "u.id, u.email, u.password, u.user_status_id, u.roles_id, " +
                "u.created_at, u.updated_at, u.remember_token, " +
                "ud.firstname, ud.lastname, ud.dob " +
                "FROM portal.users u " +
                "LEFT JOIN portal.user_details ud ON u.id = ud.users_id " +
                "WHERE u.id = ?";

        try {
            User user = jdbcTemplate.queryForObject(sql, userRowMapper, id);
            return Optional.ofNullable(user);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void update(User user) {
        String userSql = "UPDATE portal.users SET " +
                "email = ?, user_status_id = ?, roles_id = ?, updated_at = ?, remember_token = ? " +
                "WHERE id = ?";

        String detailsSql = "UPDATE portal.user_details SET " +
                "firstname = ?, lastname = ?, dob = ? " +
                "WHERE users_id = ?";

        jdbcTemplate.update(userSql,
                user.getEmail(),
                user.getUserStatusId(),
                user.getRoleId(),
                Timestamp.valueOf(LocalDateTime.now()),
                user.getRememberToken(),
                user.getId()
        );

        UserDetails details = user.getUserDetails();
        if (details != null) {
            jdbcTemplate.update(detailsSql,
                    details.getFirstName(),
                    details.getLastName(),
                    details.getDob() != null ? Date.valueOf(details.getDob()) : null,
                    user.getId()
            );
        }
    }

    @Override
    public void updatePassword(int userId, String newPassword) {
        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(newPassword, salt);

        String sql = "UPDATE portal.users SET password = ?, updated_at = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                hashedPassword,
                Timestamp.valueOf(LocalDateTime.now()),
                userId
        );
    }

    @Override
    public void delete(int userId) {
        String deleteDetailsSql = "DELETE FROM portal.user_details WHERE users_id = ?";
        String deleteUserSql = "DELETE FROM portal.users WHERE id = ?";

        jdbcTemplate.update(deleteDetailsSql, userId);
        jdbcTemplate.update(deleteUserSql, userId);
    }
}