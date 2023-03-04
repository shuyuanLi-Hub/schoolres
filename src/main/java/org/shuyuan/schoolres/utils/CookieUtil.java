package org.shuyuan.schoolres.utils;

import com.alibaba.fastjson2.util.UUIDUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CookieUtil
{
    public static void addSessionId(HttpServletResponse response, String token)
    {
        Cookie cookie = new Cookie(UserKey.COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(UserKey.TOKEN_EXPIRE);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static String getCookieValue(HttpServletRequest request, String cookieName)
    {
        var cookies = request.getCookies();

        if (cookies == null || cookies.length <= 0)
        {
            return null;
        }

        for (Cookie cookie : cookies)
        {
            if (cookie.getName().equals(cookieName))
            {
                return cookie.getValue();
            }
        }

        return null;
    }

    public static String getSessionId(HttpServletRequest request, HttpServletResponse response)
    {
        String sessionId = getCookieValue(request, UserKey.COOKIE_NAME_TOKEN);

        if (sessionId == null)
        {
            sessionId = String.valueOf(UUID.randomUUID());
            addSessionId(response, sessionId);
        }

        return sessionId;
    }
}
