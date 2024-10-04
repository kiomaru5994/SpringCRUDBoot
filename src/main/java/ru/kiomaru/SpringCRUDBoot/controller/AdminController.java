package ru.kiomaru.SpringCRUDBoot.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kiomaru.SpringCRUDBoot.model.User;
import ru.kiomaru.SpringCRUDBoot.service.UserServiceImp;



@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServiceImp userServiceImp;

    @Autowired
    public AdminController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @GetMapping(value = {"", "/"})
    public String index() {
        return "/admin_panel/index";
    }

    @GetMapping(value = {"/create", "/create/"})
    public String create(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", userServiceImp.getAllRoles());
        model.addAttribute("citizenship", userServiceImp.getAllCountries());
        return "/admin_panel/create_user";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        if (userServiceImp.existByUserName(user.getUsername())) {
            bindingResult.rejectValue("username", "exist.user.username", "Такой логин существует");
        } else if (userServiceImp.existByEmail(user.getEmail())) {
            bindingResult.rejectValue("email", "exist.user.email", "Такой email существует");
        } else if (userServiceImp.existByTelegramAccount(user.getTelegramAccount())) {
            bindingResult.rejectValue("telegramAccount", "exist.user.telegramAccount", "Такой ТГ аккаунт существует");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", userServiceImp.getAllRoles());
            model.addAttribute("citizenship", userServiceImp.getAllCountries());
            return "/admin_panel/create_user";
        }
        userServiceImp.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping(value = {"/users", "/users/"})
    public String showUsers(Model model) {
        model.addAttribute("users", userServiceImp.getAllUsers());
        return "/admin_panel/users_list";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam String username) {
        userServiceImp.delete(username);
        return "redirect:/admin/users";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam String username, Model model) {
        model.addAttribute("roles", userServiceImp.getAllRoles());
        model.addAttribute("citizenship", userServiceImp.getAllCountries());
        model.addAttribute("user", userServiceImp.getUser(username));
        return "/admin_panel/edit_user";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model, @RequestParam String hiddenUsername) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", userServiceImp.getAllRoles());
            model.addAttribute("citizenship", userServiceImp.getAllCountries());
            return "/admin_panel/edit_user";
        }
        User originalUser = userServiceImp.getUser(hiddenUsername);
        if (userServiceImp.existByUserName(user.getUsername()) && !originalUser.getUsername().equals(user.getUsername())) {
            bindingResult.rejectValue("username", "exist.user.username", "Такой логин существует");
        }
        if (userServiceImp.existByEmail(user.getEmail()) && !originalUser.getEmail().equals(user.getEmail())) {
            bindingResult.rejectValue("email", "exist.user.email", "Такой email существует");
        }
        if (userServiceImp.existByTelegramAccount(user.getTelegramAccount()) && !originalUser.getTelegramAccount().equals(user.getTelegramAccount())) {
            bindingResult.rejectValue("telegramAccount", "exist.user.telegramAccount", "Такой ТГ аккаунт существует");
        }

        userServiceImp.saveEditedUser(hiddenUsername, user);
        return "redirect:/admin/users";
    }
}
