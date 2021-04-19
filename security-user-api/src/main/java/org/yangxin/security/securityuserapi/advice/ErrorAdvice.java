package org.yangxin.security.securityuserapi.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangxin
 * 2021/4/17 上午11:17
 */
@RestControllerAdvice
public class ErrorAdvice {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Map<String, Object> handle(Exception e) {
        Map<String, Object> infoMap = new HashMap<>(2);
        infoMap.put("message", e.getMessage());
        infoMap.put("time", System.currentTimeMillis());

        return infoMap;
    }
}
