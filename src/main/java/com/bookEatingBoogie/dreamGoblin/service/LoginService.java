package com.bookEatingBoogie.dreamGoblin.service;

import com.bookEatingBoogie.dreamGoblin.Repository.UserRepository;
import com.bookEatingBoogie.dreamGoblin.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    public String signup(User user) {
        if (userRepository.findByUserId(user.getUserId()).isPresent()) {
            return "Failed";
        } else {
            userRepository.save(user);
            return "Success";
        }
    }

    public String login(User user) {
        Optional<User> request = userRepository.findByUserId(user.getUserId());
        if (request.get().getPassword().equals(user.getPassword())) {
            return "Success";
        }
        return "Failed";
    }
}
