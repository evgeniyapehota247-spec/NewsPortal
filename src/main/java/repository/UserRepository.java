package repository;

import bean.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {

    Optional<User> findByEmail(String email);

    boolean create(User user);

    boolean checkPassword(String email, String password);

    void saveRememberToken(int userId, String token);

    String getRememberToken(int userId);

    boolean existsByEmail(String email);

}