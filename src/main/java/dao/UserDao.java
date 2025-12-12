package dao;

import bean.User;

public interface UserDao {

    User findByEmail(String email) throws DaoException;

    boolean create(User user) throws DaoException;

    boolean checkPassword(String email, String password) throws DaoException;

    void saveRememberToken(int userId, String token) throws DaoException;

    String getRememberToken(int userId) throws DaoException;

}
