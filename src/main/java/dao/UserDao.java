package dao;

import bean.RegistrationInfo;
import bean.User;

import java.sql.SQLException;

public interface UserDao {

    User checkCredentials(String email, String password) throws DaoException;

    boolean registration(RegistrationInfo info) throws DaoException, SQLException;

}
