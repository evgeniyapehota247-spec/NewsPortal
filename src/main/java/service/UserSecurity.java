package service;

import bean.User;

public interface UserSecurity {

    User signIn(String email, String password) throws ServiceException;

}
