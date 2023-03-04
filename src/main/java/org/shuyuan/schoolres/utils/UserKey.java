package org.shuyuan.schoolres.utils;

public class UserKey extends AbstractKeyPrefix
{
    public static final String COOKIE_NAME_TOKEN = "token";
    public static final int TOKEN_EXPIRE = 1800;

    public UserKey(int expireSeconds, String prefix)
    {
        super(expireSeconds, prefix);
    }

    public static UserKey session = new UserKey(TOKEN_EXPIRE, "session:");
    public static  UserKey verifyCode = new UserKey(TOKEN_EXPIRE, "verifyCode:");
    public static UserKey user = new UserKey(TOKEN_EXPIRE, "user:");
}
