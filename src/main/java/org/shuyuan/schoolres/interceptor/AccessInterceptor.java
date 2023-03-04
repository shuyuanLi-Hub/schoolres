package org.shuyuan.schoolres.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.shuyuan.schoolres.domain.User;
import org.shuyuan.schoolres.exceptions.CodeMsg;
import org.shuyuan.schoolres.exceptions.UserException;
import org.shuyuan.schoolres.service.user.UserService;
import org.shuyuan.schoolres.utils.CookieUtil;
import org.shuyuan.schoolres.utils.CustomRedisUtil;
import org.shuyuan.schoolres.utils.UserKey;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

@Component
@Slf4j
public class AccessInterceptor implements HandlerInterceptor
{
    private UserService userService;
    private CustomRedisUtil redisUtil;

    public AccessInterceptor(UserService userService, CustomRedisUtil redisUtil)
    {
        this.userService = userService;
        this.redisUtil = redisUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        String sessionId = CookieUtil.getSessionId(request, response);
//        log.info("uri : " + request.getRequestURI());
//        request.getParameterMap().forEach((k, v) -> System.out.println(k + "->" + v.toString()));

        if (sessionId == null)
        {
            throw new UserException(CodeMsg.SERVER_INNER_ERROR);
        }

        User user = redisUtil.get(UserKey.session, sessionId, User.class);

//        System.out.println(user);

        if (user == null)
        {
            throw new UserException(CodeMsg.USER_NOT_LOGIN);
        }

        request.setAttribute("user", user);

        return true;
    }

}
