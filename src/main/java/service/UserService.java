package service;

import bean.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    Optional<User> findByEmail(String email);

    boolean register(User user);

    boolean authenticate(String email, String password);

    void saveRememberToken(int userId, String token);

    String getRememberToken(int userId);

    boolean emailExists(String email);

}