package org.geekhub.studentsregistry.web.controllers;

import org.geekhub.studentsregistry.web.service.interfaces.UserService;
import org.geekhub.studentsregistry.web.users.Role;
import org.geekhub.studentsregistry.web.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "users/userRegistration";
    }

    @PostMapping("/registration")
    public String addUser(Model model, @ModelAttribute ("user") @Valid User user, BindingResult bindingResult) {
        Optional<User> userFromDb = userService.find(user.getUsername());
        if (userFromDb.isPresent()) {
            model.addAttribute("messageName", "User with name '" + user.getUsername() + "' already exists!");
            model.addAttribute("user", user);
            return "users/userRegistration";
        }
        if(!user.getPassword().equals(user.getConfirmPassword())){
            model.addAttribute("messageConfirm", "Password don`t match!");
            model.addAttribute("user", user);
            return "users/userRegistration";
        }
        if (bindingResult.hasErrors()) return "users/userRegistration";
        user.setActive(true);
        user.setRoles(Set.of(Role.values()));
        userService.add(user);
        return "redirect:/login";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users/usersList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/update/{id}")
    public String userEdit(@PathVariable("id") Integer id, Model model) {
        User user = userService.find(id).orElseThrow(IllegalArgumentException::new);
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "users/userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/update/{id}")
    public String userUpdate(@PathVariable("id") Integer id,
                             @ModelAttribute("user") @Valid User newUser,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "users/userEdit";
        userService.update(id, newUser);
        return "redirect:/users";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/delete/{id}")
    public String userDelete(@PathVariable("id") Integer id) {
        userService.delete(id);
        return "redirect:/users";
    }

}
