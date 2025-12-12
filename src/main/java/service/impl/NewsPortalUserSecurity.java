package service.impl;

import bean.User;
import dao.DaoException;
import dao.DaoProvider;
import dao.UserDao;
import service.ServiceException;
import service.UserSecurity;

import java.util.UUID;

public class NewsPortalUserSecurity implements UserSecurity {

    private final UserDao userDao = DaoProvider.getInstance().getUserDao();

    @Override
    public User signIn(String email, String password) throws ServiceException {

        try {
            // Получаем пользователя по email
            System.out.println("Поиск пользователя в БД по email: " + email);
            User user = userDao.findByEmail(email);

            if (user == null) {
                System.out.println("Пользователь не найден в БД");
                throw new ServiceException("Пользователь с таким email не найден");
            }

            System.out.println("Пользователь найден: " + user.getEmail());

            // Проверяем пароль
            System.out.println("Проверка пароля через checkPassword...");
            boolean passwordValid = userDao.checkPassword(email, password);
            System.out.println("Результат checkPassword: " + passwordValid);

            if (!passwordValid) {
                System.out.println("Неверный пароль для пользователя: " + email);
                throw new ServiceException("Неверный пароль");
            }

            // Проверяем статус пользователя
            System.out.println("Статус пользователя: " + user.getUserStatusId());
            if (user.getUserStatusId() != 1) { // 1 = активный
                System.out.println("Аккаунт не активен, статус: " + user.getUserStatusId());
                throw new ServiceException("Аккаунт не активен");
            }

            System.out.println("=== SIGN IN SUCCESS ===");
            System.out.println("Возвращаем пользователя: " + user.getEmail());
            return user;

        } catch (DaoException e) {
            System.err.println("Ошибка DAO в signIn: " + e.getMessage());
            e.printStackTrace();
            throw new ServiceException("Ошибка аутентификации", e);
        }
    }

    @Override
    public User authenticateByToken(String email, String token) {


        try {
            User user = userDao.findByEmail(email);

            if (user == null) {
                return null;
            }

            System.out.println("Пользователь найден, получение токена из БД...");
            String storedToken = userDao.getRememberToken(user.getId());
            System.out.println("Токен в БД: " + (storedToken != null ? storedToken.substring(0, Math.min(10, storedToken.length())) + "..." : "null"));

            if (storedToken != null && storedToken.equals(token)) {
                System.out.println("Токен совпадает, аутентификация успешна");
                return user;
            }

            System.out.println("Токен не совпадает или отсутствует");
            return null;

        } catch (DaoException e) {
            System.err.println("Ошибка DAO в authenticateByToken: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String generateRememberToken(User user) {
        System.out.println("=== GENERATE REMEMBER TOKEN START ===");
        System.out.println("Для пользователя: " + user.getEmail() + " (ID: " + user.getId() + ")");

        String token = UUID.randomUUID().toString();
        System.out.println("Сгенерирован токен: " + token);

        try {
            // Сохраняем токен в базу данных
            userDao.saveRememberToken(user.getId(), token);
            System.out.println("Токен сохранен в БД для userId: " + user.getId());
            return token;
        } catch (DaoException e) {
            System.err.println("Ошибка сохранения токена в БД: " + e.getMessage());
            e.printStackTrace();
            return token; // все равно возвращаем токен, даже если сохранение не удалось
        }
    }

    @Override
    public boolean registration(User user) throws ServiceException {

        try {
            // Проверяем, существует ли email
            User existingUser = userDao.findByEmail(user.getEmail());

            if (existingUser != null) {
                throw new ServiceException("Пользователь с таким email уже существует");
            }

            // Создаем пользователя в двух таблицах
            boolean result = userDao.create(user);

            if (result) {
                System.out.println("DEBUG: Пользователь успешно создан: " + user.getEmail());
            } else {
                System.out.println("DEBUG: Не удалось создать пользователя: " + user.getEmail());
            }

            return result;

        } catch (DaoException e) {
            System.err.println("DEBUG: Ошибка DAO при регистрации: " + e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        } catch (Exception e) {
            System.err.println("DEBUG: Общая ошибка при регистрации: " + e.getMessage());
//            e.printStackTrace();
            throw new ServiceException("Ошибка регистрации: " + e.getMessage(), e);
        }
    }
}
