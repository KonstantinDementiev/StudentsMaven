package org.geekhub.studentsregistry.web.service;

import org.geekhub.studentsregistry.web.logger.WebLogger;
import org.geekhub.studentsregistry.web.repository.UserRepo;
import org.geekhub.studentsregistry.web.service.interfaces.UserService;
import org.geekhub.studentsregistry.web.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final WebLogger logger = new WebLogger(UserServiceImpl.class.getName());
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> find(Integer id) {
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()) {
            logger.info("User with ID= " + id + " successfully loaded!");
        } else {
            logger.info("User with ID= " + id + " was not found!");
        }
        return user;
    }

    @Override
    public Optional<User> find(String name) {
        Optional<User> user = Optional.ofNullable(userRepo.findByUsername(name));
        if (user.isPresent()) {
            logger.info("User with name '" + name + "' successfully loaded!");
        } else {
            logger.info("User with name '" + name + "' was not found!");
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public void add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        logger.info("User with name '" + user.getUsername() + "' has been successfully created!");
    }

    @Override
    public void update(int id, User newUser) {
        if (userRepo.existsById(id)) {
            User existUser = userRepo.findById(id).orElseThrow(IllegalArgumentException::new);
            existUser.setUsername(newUser.getUsername());
            existUser.setRoles(newUser.getRoles());
            userRepo.save(existUser);
            logger.info("User with ID= " + id + " successfully updated!");
        } else {
            logger.info("User with ID= " + id + " was not found!");
        }
    }

    @Override
    public void delete(int id) {
        if (userRepo.existsById(id)) {
            userRepo.deleteById(id);
            logger.info("User with ID= " + id + " successfully removed!");
        } else {
            logger.info("User with ID= " + id + " was not found!");
        }
    }
}