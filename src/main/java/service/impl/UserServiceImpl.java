package service.impl;

import bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.UserRepository;
import service.UserService;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean register(User user) {
        return userRepository.create(user);
    }

    @Override
    public boolean authenticate(String email, String password) {
        return userRepository.checkPassword(email, password);
    }

    @Override
    public void saveRememberToken(int userId, String token) {
        userRepository.saveRememberToken(userId, token);
    }

    @Override
    public String getRememberToken(int userId) {
        return userRepository.getRememberToken(userId);
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

}