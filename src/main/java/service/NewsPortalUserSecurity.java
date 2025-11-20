package service;

import bean.User;

import java.util.UUID;

public class NewsPortalUserSecurity implements UserSecurity {

    @Override
    public User signIn(String email, String password) throws ServiceException {

        if ("zhenya_247@mail.ru".equals(email) && "123".equals(password)) {
//            throw new ServiceException();
            return new User("ZHENYA", "123");
        }
        return null;
    }

    @Override
    public User authenticateByToken(String email, String token) {
//        User user = userDao.findByEmail(email);
//        if (user != null && token.equals(user.getRememberToken())) {
//            return user;
//        }
//        return null;

        // ВРЕМЕННО: проверяем существование токена (любого)
        if (token != null && !token.isEmpty()) {
            // Для теста - создаем пользователя
            User user = new User("ZHENYA", "123");
            user.setEmail(email);
            return user;
        }
        return null;
    }

    @Override
    public String generateRememberToken(User user) {
        String token = UUID.randomUUID().toString();
        // Сохраняем токен в базу данных
//        user.setRememberToken(token);
//        userDao.update(user);
        System.out.println("Generated token for " + user.getEmail() + ": " + token);
        return token;
    }

//    @Override
//    public User signIn(String email, String password) {
//        User user = userDao.findByEmail(email);
//        if (user != null && user.getPassword().equals(password)) {
//            return user;
//        }
//        return null;
//    }
}
