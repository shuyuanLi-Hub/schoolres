package org.shuyuan.schoolres.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.shuyuan.schoolres.domain.User;
import org.shuyuan.schoolres.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Slf4j
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

//    @SneakyThrows
//    @PostMapping("/register")
//    @ResponseBody
//    public ResponseEntity<String> register(@Validated User user, BindingResult result, Model model, Errors errors, HttpSession session, HttpServletResponse response)
//    {
//        System.out.println(user);
//        if (errors.hasErrors())
//        {
////            errors.
//            if (errors.hasFieldErrors("name"))
//            {
//                model.addAttribute("error", "name");
//                return new ResponseEntity<>("name", HttpStatus.OK);
////                return "register";
//            }
//            else if (errors.hasFieldErrors("passwd"))
//            {
//                model.addAttribute("error", "passwd");
////                return "register";
//                return new ResponseEntity<>("passwd", HttpStatus.OK);
//            }
//            else if (errors.hasFieldErrors("date"))
//            {
//                log.info("date has errors");
//                model.addAttribute("error", "date");
////                return "register";
//                return new ResponseEntity<>("date", HttpStatus.OK);
//            }
//            else if (errors.hasFieldErrors("email"))
//            {
//                model.addAttribute("error", "email");
////                return "register";
//                return new ResponseEntity<>("email", HttpStatus.OK);
//            }
//            else if (errors.hasFieldErrors("phone"))
//            {
//                model.addAttribute("error", "phone");
////                return "register";
//                return new ResponseEntity<>("phone", HttpStatus.OK);
//            }
//        }
//
//        User res = service.findUserByName(user.getName());
//
//        if (res == null)
//        {
//            session.setAttribute("user_name", user.getName());
////            user.setPhoto("default.png");
//            service.save(user);
//
//            response.sendRedirect("/index");
//            return new ResponseEntity<>(null, HttpStatus.OK);
//
////            return "index";
//
//        }
//        else
//        {
////            return "index";
//            response.sendRedirect("/index");
//            return new ResponseEntity<>(null, HttpStatus.OK);
//        }
//    }


    @SneakyThrows
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Map<String, String>> register(@Validated User user, Errors errors, HttpServletResponse response, HttpSession session)
    {
        System.out.println(user);
        if (errors.getFieldErrorCount() > 0)
        {
//            log.info("有非法字段");
            var obj = errors.getFieldError().getField();
//            log.info(obj);
            return new ResponseEntity<>(Map.of("type", "error", "field", obj), HttpStatus.OK);
        }

        User res = service.findUserByName(user.getName());

        if (res == null)
        {
            session.setAttribute("user_name", user.getName());
            service.save(user);

//            response.sendRedirect("/index");
            return new ResponseEntity<>(Map.of("type", "ok"), HttpStatus.OK);
        }
        else
        {
//            response.sendRedirect("/index");
            return new ResponseEntity<>(Map.of("type", "ok"), HttpStatus.OK);
        }
    }
}
