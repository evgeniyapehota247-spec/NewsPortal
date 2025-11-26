package db;

import java.sql.*;

public class DBConfig {

    public static void main(String[] args) throws ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {

            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/portal?useSSL=false", "root", "root");
            System.out.println("Connected to database");

            st = con.createStatement();

            rs = st.executeQuery("select * from users");

            while (rs.next()) {
                System.out.println(rs.getInt(1) + "\t"
                        + rs.getString(2) + "\t"
                        + rs.getString(3) + "\t"
                        + rs.getString(4) + "\t"
                        + rs.getString(5) + "\t");
            }
//            String sql = "INSERT INTO `portal`.`users` (`email`, `password`, `surname`, `name`) " +
//                    "VALUES " +
//                    "('qqq@gmail.com', '123', 'QIvanov', 'QIvan')," +
//                    "('zzz@gmail.com', '123', 'ZIvanov', 'ZIvan')";

            String sql = "INSERT INTO `portal`.`users` (`email`, `password`, `surname`, `name`) " +
                    "VALUES (?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, "2Ivanov");
            ps.setString(2, "3Ivanov");
            ps.setString(3, "4Ivanov");
            ps.setString(4, "5Ivanov");

            ps.executeUpdate();//save db


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

        }
    }
}
