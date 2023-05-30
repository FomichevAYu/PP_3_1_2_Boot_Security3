package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.transaction.Transactional;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;

    private RoleRepository roleRepository;
    @Autowired
    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.showAllUsers());
        return "admin";
    }
    @GetMapping("/{id}")
    public String showUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.showUser(id));
        return "user";
    }
    @GetMapping("/{id}/edit")
    @Transactional
    public String showEditUserPage(@PathVariable("id") long id, Model model) {
        List<Role> roles =  roleRepository.findAll();
        model.addAttribute("allRoles", roles);
        model.addAttribute("user", userService.showUser(id));
        User user = userService.showUser(id);
        System.out.println(user);
        System.out.println(roles);
        return "edit";
    }
    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") long id) {
        userService.updateUser(user, id);
        return "redirect:/admin";
    }
    @PostMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}