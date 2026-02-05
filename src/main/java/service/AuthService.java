package service;

import bean.User;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    User signIn(String email, String password);

    User authenticateByToken(String email, String token);

    String generateRememberToken(User user);

    boolean registration(User user);

}