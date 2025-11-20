package dao;

import bean.User;

public interface UserDao {

    User checkCredentials(String email, String password) throws DaoException;

    boolean registerUser() throws DaoException;

}
