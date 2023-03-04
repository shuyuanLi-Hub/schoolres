package org.shuyuan.schoolres.exceptions;

public enum CodeMsg
{
    SERVER_INNER_ERROR("服务器内部错误!"),
    VERIFY_ERROR("验证码错误"),
    PASSWD_ERROR("密码错误"),
    USER_NOT_EXISTS("用户不存在"),
    USER_NOT_LOGIN("用户未登录");

    private String msg;

    CodeMsg(String msg)
    {
        this.msg = msg;
    }

    public String getMsg()
    {
        return this.msg;
    }
}
