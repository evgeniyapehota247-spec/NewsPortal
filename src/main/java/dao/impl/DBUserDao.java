package dao.impl;

import bean.User;
import dao.DaoException;
import dao.UserDao;

public class DBUserDao implements UserDao {
    @Override
    public User checkCredentials(String email, String password) throws DaoException {
        return null;
    }

    @Override
    public boolean registerUser() throws DaoException {
        return false;
    }
}
