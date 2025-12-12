package dao.impl;

import bean.User;
import bean.UserDetails;
import dao.DaoException;
import dao.UserDao;
import dao.pool.ConnectionPool;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class DBUserDao implements UserDao {

    private final ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public User findByEmail(String email) throws DaoException {

        String sql = "SELECT " +
                "u.id, " +
                "u.email, " +
                "u.password, " +
                "u.user_status_id," +
                " u.roles_id, " +
                "u.created_at, " +
                "u.updated_at, " +
                "u.remember_token," +
                "ud.firstname, " +
                "ud.lastname, " +
                "ud.dob " +
                "FROM portal.users u " +
                "LEFT JOIN portal.user_details ud ON u.id = ud.users_id " +
                "WHERE u.email = ?";

        try (Connection con = pool.takeConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setUserStatusId(rs.getInt("user_status_id"));
                    user.setRoleId(rs.getInt("roles_id"));
                    user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    user.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                    user.setRememberToken(rs.getString("remember_token"));

                    // Создаем UserDetails
                    UserDetails details = new UserDetails();
                    details.setUserId(user.getId());
                    details.setFirstName(rs.getString("firstname"));
                    details.setLastName(rs.getString("lastname"));

                    Date dob = rs.getDate("dob");
                    if (dob != null) {
                        details.setDob(dob.toLocalDate());
                    }

                    user.setUserDetails(details);
                    System.out.println("Пользователь найден: " + email);
                    return user;
                } else {
                    System.out.println("Пользователь НЕ найден: " + email);
                    return null;
                }
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при поиске пользователя: " + e.getMessage());
            throw new DaoException("Ошибка поиска пользователя по email", e);
        }
    }

    @Override
    public boolean create(User user) throws DaoException {

        Connection con = null;
        try {
            con = pool.takeConnection();
            con.setAutoCommit(false); // Начинаем транзакцию

            // 1. Проверяем, существует ли email
            if (isEmailExists(con, user.getEmail())) {
                throw new DaoException("Пользователь с email '" + user.getEmail() + "' уже существует");
            }

            // 2. Хэшируем пароль
            String salt = BCrypt.gensalt();
            String hashedPassword = BCrypt.hashpw(user.getPassword(), salt);

            // 3. Вставляем в таблицу users
            String userSql = "INSERT INTO portal.users (email, password, user_status_id, roles_id, created_at, updated_at) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            int userId;
            try (PreparedStatement userPs = con.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS)) {
                userPs.setString(1, user.getEmail());
                userPs.setString(2, hashedPassword);
                userPs.setInt(3, user.getUserStatusId());
                userPs.setInt(4, user.getRoleId());
                userPs.setTimestamp(5, Timestamp.valueOf(user.getCreatedAt()));
                userPs.setTimestamp(6, Timestamp.valueOf(user.getUpdatedAt()));

                userPs.executeUpdate();

                // Получаем сгенерированный ID
                try (ResultSet rs = userPs.getGeneratedKeys()) {
                    if (rs.next()) {
                        userId = rs.getInt(1);
                    } else {
                        con.rollback();
                        return false;
                    }
                }
            }

            // 4. Вставляем в таблицу user_details
            UserDetails details = user.getUserDetails();
            String detailsSql = "INSERT INTO portal.user_details (users_id, firstname, lastname, dob) " +
                    "VALUES (?, ?, ?, ?)";

            try (PreparedStatement detailsPs = con.prepareStatement(detailsSql)) {
                detailsPs.setInt(1, userId);
                detailsPs.setString(2, details.getFirstName());
                detailsPs.setString(3, details.getLastName());

                if (details.getDob() != null) {
                    detailsPs.setDate(4, Date.valueOf(details.getDob()));
                } else {
                    detailsPs.setNull(4, Types.DATE);
                }

                detailsPs.executeUpdate();
            }

            con.commit();
            System.out.println("Пользователь зарегистрирован: " + user.getEmail());
            return true;

        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    // Игнорируем ошибку отката
                }
            }
            System.err.println("SQL ошибка при регистрации: " + e.getMessage());
            throw new DaoException("Ошибка регистрации: " + e.getMessage(), e);
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    // Игнорируем ошибку закрытия
                }
            }
        }
    }

    @Override
    public boolean checkPassword(String email, String password) throws DaoException {
        System.out.println("=== CHECK PASSWORD START ===");
        System.out.println("Email: " + email);
        System.out.println("Введенный пароль (первые 10 символов): " +
                (password != null ? password.substring(0, Math.min(10, password.length())) : "null"));

        String sql = "SELECT password FROM portal.users WHERE email = ?";

        try (Connection con = pool.takeConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password");
                    System.out.println("Хэш из БД: " + storedHash);

                    if (storedHash == null) {
                        System.out.println("Хэш из БД NULL!");
                        return false;
                    }

                    System.out.println("Длина хэша: " + storedHash.length());
                    System.out.println("Начало хэша: " +
                            storedHash.substring(0, Math.min(30, storedHash.length())));

                    try {
                        boolean result = BCrypt.checkpw(password, storedHash);
                        System.out.println("Результат BCrypt.checkpw: " + result);

                        // Дополнительная отладка
                        if (!result) {
                            System.out.println("Пароль не совпал. Проверка...");
                            System.out.println("Тест с пустым паролем: " + BCrypt.checkpw("", storedHash));
                            System.out.println("Тест с '123': " + BCrypt.checkpw("123", storedHash));
                            System.out.println("Тест с 'password': " + BCrypt.checkpw("password", storedHash));
                        }

                        return result;
                    } catch (IllegalArgumentException e) {
                        System.err.println("Ошибка BCrypt: " + e.getMessage());
                        System.err.println("Возможно хэш не в BCrypt формате");
                        return false;
                    }
                }
                System.out.println("Пользователь не найден в БД");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Ошибка SQL при проверке пароля: " + e.getMessage());
            throw new DaoException("Ошибка проверки пароля", e);
        }
    }

    @Override
    public void saveRememberToken(int userId, String token) throws DaoException {
        // Если у вас есть таблица для токенов или поле в users
        String sql = "UPDATE portal.users SET remember_token = ? WHERE id = ?";

        try (Connection con = pool.takeConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, token);
            ps.setInt(2, userId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Ошибка сохранения токена", e);
        }
    }

    @Override
    public String getRememberToken(int userId) throws DaoException {
        String sql = "SELECT remember_token FROM portal.users WHERE id = ?";

        try (Connection con = pool.takeConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("remember_token");
                }
            }
            return null;

        } catch (SQLException e) {
            throw new DaoException("Ошибка получения токена", e);
        }
    }

    private boolean isEmailExists(Connection con, String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM portal.users WHERE email = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}