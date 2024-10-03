package ru.kiomaru.SpringCRUDBoot.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kiomaru.SpringCRUDBoot.model.User;
import ru.kiomaru.SpringCRUDBoot.service.UserServiceImp;



@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServiceImp userServiceImp;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserServiceImp userServiceImp, PasswordEncoder passwordEncoder) {
        this.userServiceImp = userServiceImp;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    public String index() {
        return "/admin_panel/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", userServiceImp.getAllRoles());
        model.addAttribute("citizenship", userServiceImp.getAllCountries());
        return "/admin_panel/create_user";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        if (userServiceImp.existByUserName(user.getUsername())) {
            bindingResult.rejectValue("userName", "exist.user.username", "Такой логин существует");
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
        return "redirect:/admin";
    }
}
