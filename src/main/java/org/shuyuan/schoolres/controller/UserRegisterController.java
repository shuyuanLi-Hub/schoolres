package org.shuyuan.schoolres.controller;

import jakarta.servlet.http.HttpSession;
import org.shuyuan.schoolres.domain.User;
import org.shuyuan.schoolres.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserRegisterController
{
    private UserService service;

    public UserRegisterController(UserService service)
    {
        this.service = service;
    }

    @GetMapping("/register")
    public String register()
    {
        return "register";
    }

    @PostMapping("/register")
    public String register(@Validated User user, Model model, Errors errors, HttpSession session)
    {
        System.out.println(user);
        if (errors.hasErrors())
        {
            if (errors.hasFieldErrors("name"))
            {
                model.addAttribute("error", "name");
                return "register";
            }
            else if (errors.hasFieldErrors("passwd"))
            {
                model.addAttribute("error", "passwd");
                return "register";
            }
            else if (errors.hasFieldErrors("email"))
            {
                model.addAttribute("error", "email");
                return "register";
            }
            else if (errors.hasFieldErrors("phone"))
            {
                model.addAttribute("error", "phone");
                return "register";
            }
        }

        User res = service.findUserByName(user.getName());

        if (res == null)
        {
            session.setAttribute("user_name", user.getName());
            user.setPhoto("default.png");
            service.save(user);
            return "index";
        }
        else
        {
            return "index";
        }
    }
}
