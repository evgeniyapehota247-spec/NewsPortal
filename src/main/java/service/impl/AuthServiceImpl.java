package service.impl;

import bean.User;
import bean.UserDetails;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.AuthService;
import service.UserService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserService userService;

    @Override
    public User signIn(String email, String password) {

        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isEmpty()) {
            return null;
        }

        User user = userOptional.get();

        if (!userService.authenticate(email, password)) {
            return null;
        }

        return user;
    }

    @Override
    public User authenticateByToken(String email, String token) {
        Optional<User> userOptional = userService.findByEmail(email);

        if (userOptional.isEmpty()) {
            return null;
        }

        User user = userOptional.get();
        String storedToken = userService.getRememberToken(user.getId());

        if (storedToken == null || !storedToken.equals(token)) {
            return null;
        }

        return user;
    }

    @Override
    public String generateRememberToken(User user) {
        String token = UUID.randomUUID().toString();
        userService.saveRememberToken(user.getId(), token);
        return token;
    }

    @Override
    public boolean registration(User user) {

        if (userService.emailExists(user.getEmail())) {
            return false;
        }

        if (user.getUserStatusId() == null) {
            user.setUserStatusId(1); // активный
        }

        if (user.getRoleId() == null) {
            user.setRoleId(1); // обычный пользователь
        }

        if (user.getCreatedAt() == null) {
            user.setCreatedAt(LocalDateTime.now());
        }

        if (user.getUpdatedAt() == null) {
            user.setUpdatedAt(LocalDateTime.now());
        }

        if (user.getUserDetails() == null) {
            UserDetails details = new UserDetails();
            details.setUserId(user.getId());
            details.setFirstName("");
            details.setLastName("");
            user.setUserDetails(details);
        }

        return userService.register(user);
    }
}