package web.listeners;

import dao.pool.ConnectionPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class ConnectionPoolListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("=== ConnectionPoolListener: contextInitialized ===");

        // 1. Проверяем наличие драйвера
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ MySQL Driver найден и зарегистрирован");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ MySQL Driver НЕ найден!");
            System.err.println("Добавьте mysql-connector-java.jar в WEB-INF/lib/");
            // Не бросаем исключение дальше, так как listener должен завершиться
            return; // Просто выходим
        }

        // 2. Пробуем создать ConnectionPool
        try {
            ConnectionPool pool = ConnectionPool.getFirstInstance(
                    "jdbc:mysql://127.0.0.1:3306/portal?useSSL=false&serverTimezone=UTC",
                    "root",
                    "root",
                    5);
            System.out.println("✅ ConnectionPool успешно создан");

            // Сохраняем pool в ServletContext для доступа из других мест
            sce.getServletContext().setAttribute("connectionPool", pool);

        } catch (SQLException e) {
            System.err.println("❌ Ошибка создания ConnectionPool: " + e.getMessage());
            e.printStackTrace();
            // Здесь можно выбросить RuntimeException чтобы остановить приложение
            throw new RuntimeException("Не удалось инициализировать ConnectionPool", e);
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("=== ConnectionPoolListener: contextDestroyed ===");

        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            if (pool != null) {
                pool.close();
                System.out.println("✅ ConnectionPool закрыт");
            } else {
                System.out.println("⚠️ ConnectionPool уже null");
            }
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при закрытии ConnectionPool: " + e.getMessage());
            e.printStackTrace();
        }
    }
}