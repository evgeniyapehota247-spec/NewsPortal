package dao;

import dao.impl.DBNewsDao;
import dao.impl.DBUserDao;
import lombok.Data;
import lombok.Getter;

@Data
public final class DaoProvider {

    @Getter
    private static final DaoProvider instance = new DaoProvider();

    private final NewsDao newsDao = new DBNewsDao();
    private final UserDao userDao = new DBUserDao();

}
