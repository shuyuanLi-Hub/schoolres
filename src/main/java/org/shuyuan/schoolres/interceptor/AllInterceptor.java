package org.shuyuan.schoolres.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.shuyuan.schoolres.service.user.UserService;
import org.shuyuan.schoolres.utils.CookieUtil;
import org.shuyuan.schoolres.utils.UserKey;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AllInterceptor implements HandlerInterceptor
{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        CookieUtil.getSessionId(request, response);
        return true;
    }
}
