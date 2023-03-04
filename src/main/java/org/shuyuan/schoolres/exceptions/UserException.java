package org.shuyuan.schoolres.exceptions;

import lombok.Data;

@Data
public class UserException extends Exception
{
    private CodeMsg codeMsg;

    public UserException(CodeMsg codeMsg)
    {
        this.codeMsg = codeMsg;
    }
}
