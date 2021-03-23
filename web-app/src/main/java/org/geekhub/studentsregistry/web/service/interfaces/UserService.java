package org.geekhub.studentsregistry.web.service.interfaces;

import org.geekhub.studentsregistry.web.users.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public Optional<User> find(Integer id);

    public Optional<User> find(String name);

    public List<User> findAll();

    public void add(User user);

    public void update(int id, User newUser);

    public void delete(int id);

}
