package service;

import bean.User;

public class NewsPortalUserSecurity implements UserSecurity {

    @Override
    public User signIn(String email, String password) throws ServiceException {

        if ("zhenya_247@mail.ru".equals(email) && "123".equals(password)) {
//            throw new ServiceException();
            return new User("ZHENYA", "123");
        }
        return null;
    }
}
