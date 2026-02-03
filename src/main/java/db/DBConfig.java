package db;

import java.sql.*;

public class DBConfig {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/portal?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "pehotaroot1234";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Драйвер MySQL не найден", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static boolean insertUser(String email, String password, String firstName, String lastName)
            throws SQLException {

        String sql = "INSERT INTO users (email, password, lastname, firstname, role_id) VALUES (?, ?, ?, ?, 1)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, lastName);
            stmt.setString(4, firstName);

            int rows = stmt.executeUpdate();
            return rows > 0;
        }
    }

    public static boolean isEmailExists(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
}