package service;

import bean.RegistrationInfo;
import bean.User;

public interface UserSecurity {

    User signIn(String email, String password) throws ServiceException;

    // Новый метод для аутентификации по токену из куки
    User authenticateByToken(String email, String token);

    // Метод для генерации токена "запомнить меня"
    String generateRememberToken(User user);

    boolean registration(RegistrationInfo info) throws ServiceException;
}
