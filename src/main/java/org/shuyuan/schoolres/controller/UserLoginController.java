package org.shuyuan.schoolres.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.shuyuan.schoolres.domain.User;
import org.shuyuan.schoolres.service.user.UserService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Locale;

@Slf4j
@Controller
public class UserLoginController
{
    private UserService userService;
    private MessageSource messageSource;

    public UserLoginController(UserService userService, MessageSource messageSource)
    {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @GetMapping("/add_user")
    public String addUser()
    {
        return "login";
    }

    @GetMapping("/login")
    public String login()
    {
        return "login";
    }

    @GetMapping("/index")
    public String index(HttpServletRequest request, HttpSession session)
    {
        var cookies = request.getCookies();

        if (cookies.length > 0)
        {
            for (var cookie : cookies)
            {
                String name = cookie.getName();

                if (name != null && !name.equals(""))
                {
                    if (userService.findUserByName(name) != null)
                    {
                        session.setAttribute("user_name", name);
                        return "index";
                    }
                }
            }
        }
        return "index";
    }

    @PostMapping("/login")
    public String login(@Validated User user, Errors error, String vercode, Locale locale, String remember, HttpServletResponse response,
                        Model model, HttpSession session)
    {
        if (error.hasErrors())
        {
            if (error.hasFieldErrors("name"))
            {
                model.addAttribute("error", "name");
                return "login";
            }
            else if (error.hasFieldErrors("passwd"))
            {
                model.addAttribute("error", "passwd");
                return "login";
            }
        }

        if (!vercode.equals(session.getAttribute("vercode") + ""))
        {
            model.addAttribute("error", "vercode");
            return "login";
        }

        User obj = userService.findUserByName(user.getName());

        if (obj == null)
        {
            model.addAttribute("error", "name");
            model.addAttribute("error_mess", messageSource.getMessage("input_name_error", null, locale));
            return "login";
        }
        else if (!obj.getPasswd().equals(user.getPasswd()))
        {
            model.addAttribute("error", "passwd");
            model.addAttribute("error_mess", messageSource.getMessage("input_passwd_error", null, locale));
            return "login";
        }
        else if (remember != null)
        {
            Cookie cookie = new Cookie(obj.getName(), URLEncoder.encode(obj.getPasswd(), Charset.forName("UTF-8")));
            cookie.setMaxAge(3600 * 24);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            session.setAttribute("user_name", obj.getName());
            session.setAttribute("user_id", obj.getId());
            return "index";
        }
        else
        {
            session.setAttribute("user_name", obj.getName());
            session.setAttribute("user_id", obj.getId());
            return "index";
        }
    }

    @DeleteMapping("/signOut")
    @ResponseBody
    public void signOut(HttpSession session)
    {
//        log.info("得到执行");
//        log.info(session.getAttribute("user_name") + "");
        session.removeAttribute("user_name");
        session.removeAttribute("user_id");

//        log.info(session.getAttribute("user_name") + "");
    }
}
