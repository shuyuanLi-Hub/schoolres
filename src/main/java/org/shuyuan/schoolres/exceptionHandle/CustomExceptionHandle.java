package org.shuyuan.schoolres.exceptionHandle;

import org.shuyuan.schoolres.exceptions.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandle
{
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<String> handle(Exception ex)
    {
        return new ResponseEntity<>("same", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<String> handleCustomEx(UserException ex)
    {
        return new ResponseEntity<>(ex.getCodeMsg().getMsg(), HttpStatus.BAD_REQUEST);
    }
}
