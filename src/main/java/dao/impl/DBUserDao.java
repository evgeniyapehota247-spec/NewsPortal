package dao.impl;

import bean.RegistrationInfo;
import bean.User;
import dao.DaoException;
import dao.UserDao;
import db.DBConfig;

import java.sql.SQLException;

public class DBUserDao implements UserDao {

    @Override
    public User checkCredentials(String email, String password) throws DaoException {
        return null; // пока не реализовано
    }

    @Override
    public boolean registration(RegistrationInfo info) throws DaoException {
        try {
            // Проверяем не существует ли уже такой email
            if (DBConfig.isEmailExists(info.getEmail())) {
                throw new SQLException("Пользователь с email '" + info.getEmail() + "' уже существует");
            }

            // Вставляем пользователя
            boolean success = DBConfig.insertUser(
                    info.getEmail(),
                    info.getPassword(), // В реальности должен быть хэш!
                    info.getFirstName(),
                    info.getLastName()
            );

            if (success) {
                System.out.println("✅ Пользователь зарегистрирован: " + info.getEmail());
            } else {
                System.out.println("❌ Не удалось зарегистрировать: " + info.getEmail());
            }

            return success;

        } catch (SQLException e) {
            System.err.println("❌ SQL ошибка при регистрации: " + e.getMessage());
            throw new DaoException("Ошибка регистрации: " + e.getMessage(), e);
        }
    }
}