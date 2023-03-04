package org.shuyuan.schoolres.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.shuyuan.schoolres.domain.Order;
import org.shuyuan.schoolres.domain.User;
import org.shuyuan.schoolres.exceptions.CodeMsg;
import org.shuyuan.schoolres.exceptions.UserException;
import org.shuyuan.schoolres.service.order.OrderService;
import org.shuyuan.schoolres.service.user.UserService;
import org.shuyuan.schoolres.utils.CookieUtil;
import org.shuyuan.schoolres.utils.CustomRedisUtil;
import org.shuyuan.schoolres.utils.Result;
import org.shuyuan.schoolres.utils.UserKey;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController
{
    private UserService userService;
    private MessageSource messageSource;
    private CustomRedisUtil redisUtil;
    private OrderService orderService;

    public UserController(UserService userService, MessageSource messageSource, CustomRedisUtil redisUtil, OrderService orderService)
    {
        this.userService = userService;
        this.messageSource = messageSource;
        this.redisUtil = redisUtil;
        this.orderService = orderService;
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
//        var cookies = request.getCookies();
//
//        if (cookies.length > 0)
//        {
//            for (var cookie : cookies)
//            {
//                String name = cookie.getName();
//
//                if (name != null && !name.equals(""))
//                {
//                    if (userService.findUserByName(name) != null)
//                    {
//                        session.setAttribute("user_name", name);
//                        return "index";
//                    }
//                }
//            }
//        }
        return "index";
    }

//    @PostMapping("/login")
//    public String login(@Validated User user, Errors error, String vercode, Locale locale, String remember, HttpServletResponse response,
//                        Model model, HttpSession session)
//    {
//        if (error.hasErrors())
//        {
//            if (error.hasFieldErrors("name"))
//            {
//                model.addAttribute("error", "name");
//                return "login";
//            }
//            else if (error.hasFieldErrors("passwd"))
//            {
//                model.addAttribute("error", "passwd");
//                return "login";
//            }
//        }
//
//        if (!vercode.equals(session.getAttribute("vercode") + ""))
//        {
//            model.addAttribute("error", "vercode");
//            return "login";
//        }
//
//        User obj = userService.findUserByName(user.getName());
//
//        if (obj == null)
//        {
//            model.addAttribute("error", "name");
//            model.addAttribute("error_mess", messageSource.getMessage("input_name_error", null, locale));
//            return "login";
//        }
//        else if (!obj.getPasswd().equals(user.getPasswd()))
//        {
//            model.addAttribute("error", "passwd");
//            model.addAttribute("error_mess", messageSource.getMessage("input_passwd_error", null, locale));
//            return "login";
//        }
//        else if (remember != null)
//        {
//            Cookie cookie = new Cookie(obj.getName(), URLEncoder.encode(obj.getPasswd(), Charset.forName("UTF-8")));
//            cookie.setMaxAge(3600 * 24);
//            cookie.setHttpOnly(true);
//            response.addCookie(cookie);
//            session.setAttribute("user_name", obj.getName());
//            session.setAttribute("user_id", obj.getId());
//            return "index";
//        }
//        else
//        {
//            session.setAttribute("user_name", obj.getName());
//            session.setAttribute("user_id", obj.getId());
//            return "index";
//        }
//    }

    @SneakyThrows
    @PostMapping("/proLogin")
    @ResponseBody
    public Result<String> proLogin(HttpServletRequest request, @Validated User user, Errors error, String verifyCode)
    {
        if (error.hasErrors())
        {
            if (error.hasFieldErrors("name"))
            {
                return Result.err("name", "用户名非法");
            }
            else if (error.hasFieldErrors("passwd"))
            {
                return Result.err("passwd", "密码非法");
            }
        }

        String sessionId = CookieUtil.getCookieValue(request, UserKey.COOKIE_NAME_TOKEN);

//        log.info("sessionId : " + sessionId);
//        System.out.println("user : " + user);

        if (sessionId != null)
        {
            User res = redisUtil.get(UserKey.session, sessionId, User.class);

            if (res == null)
            {
                if (!userService.checkVerifyCode(sessionId, verifyCode))
                {
//                log.info("check is false");
                    throw new UserException(CodeMsg.VERIFY_ERROR);
                }

                // 根据用户名获取session中的对象
//            User obj = userService.getByName(user.getName());

                User obj = userService.login(user);

                if (obj != null)
                {
                    redisUtil.set(UserKey.session, sessionId, obj);
                    return Result.success("ok");
                }
            }

            return Result.success("ok");
//            if (obj == null)
//            {
//                if (userService.login(user) != null)
//                {
//                    return Result.success("登陆成功");
//                }
//            }
//            else if (!obj.getName().equals(user.getName()))
//            {
//                throw new UserException(CodeMsg.USER_NOT_EXISTS);
//            }
//            else if (!obj.getPasswd().equals(user.getPasswd()))
//            {
//                throw new UserException(CodeMsg.PASSWD_ERROR);
//            }
//            else
//            {
//                return Result.success("登陆成功");
//            }
        }

        throw new UserException(CodeMsg.SERVER_INNER_ERROR);
    }


    @SneakyThrows
    @DeleteMapping("/signOut")
    @ResponseBody
    public void signOut(HttpServletRequest request, HttpServletResponse response)
    {

        String sessionId = CookieUtil.getSessionId(request, response);
//        log.info("signOut" + sessionId);

        if (sessionId == null)
        {
            throw new UserException(CodeMsg.SERVER_INNER_ERROR);
        }

        redisUtil.delete(UserKey.session, sessionId);
    }

    @SneakyThrows
    @GetMapping("/verifyCode")
    @ResponseBody
    public void verifyCodeImg(HttpServletRequest request, HttpServletResponse response)
    {
        String token = CookieUtil.getSessionId(request, response);

        BufferedImage image = userService.createVerifyCode(token);

        OutputStream out = response.getOutputStream();

        ImageIO.write(image, "PNG", out);
        out.flush();
        out.close();
    }

    @GetMapping("/getAddress")
    @ResponseBody
    public ResponseEntity<List<String>> getUserAddress(String name)
    {
        log.info("getAddress");
        return new ResponseEntity<>(userService.findUserAddress(name), HttpStatus.OK);
    }

    @SneakyThrows
    @GetMapping("/initial")
    @ResponseBody
    public ResponseEntity<User> initial(HttpServletRequest request)
    {
        return new ResponseEntity<>((User) request.getAttribute("user"), HttpStatus.OK);
    }

    @SneakyThrows
    @GetMapping("/getOrders")
    @ResponseBody
    public ResponseEntity<List<Order>> getOrders(HttpServletRequest request)
    {
        User user = (User) request.getAttribute("user");

        System.out.println(user);

        var data = orderService.findOrderByUserAndStatusNotIn(user.getName(),
                List.of('2', '3'));

        if (data.isEmpty())
        {
            return null;
        }

        return new ResponseEntity<>(
                data,
                HttpStatus.OK);
    }

    @SneakyThrows
    @GetMapping("/getHistory")
    @ResponseBody
    public ResponseEntity<List<Order>> getHistory(HttpServletRequest request)
    {
        User user = (User) request.getAttribute("user");

        var data = orderService.findOrderByUserAndStatusNotIn(user.getName(), List.of('0', '1'));

        if (data.isEmpty())
        {
            return null;
        }

        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
