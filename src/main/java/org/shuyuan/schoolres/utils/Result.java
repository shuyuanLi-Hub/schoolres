package org.shuyuan.schoolres.utils;

import lombok.Data;

@Data
public class Result<T>
{
    private String state;
    private String field;
    private T msg;

    private Result(String state, T msg)
    {
        this.state = state;
        this.msg = msg;
    }

    public Result(String state, String field, T msg)
    {
        this.state = state;
        this.field = field;
        this.msg = msg;
    }

    public static <T> Result<T> err(T msg)
    {
        return new Result<>("err", msg);
    }

    public static <T> Result<T> success(T msg)
    {
         return new Result<>("ok", msg);
    }
    public static <T> Result<T> err(String field, T msg)
    {
        return new Result<>("err", field, msg);
    }

    public static <T> Result<T> success(String field, T msg)
    {
         return new Result<>("ok", field, msg);
    }
}
