package com.devh.project.jsoup.advice;

import com.devh.project.common.constant.ApiStatus;
import com.devh.project.common.dto.ApiResponse;
import com.devh.project.jsoup.exception.JsoupException;
import com.devh.project.jsoup.exception.JsoupNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class JsoupProxyAdvice
{
    @ExceptionHandler({JsoupException.class})
    public ApiResponse<Exception> handleJsoupException(Exception e)
    {
        if (e instanceof JsoupNotFoundException)
        {
            return ApiResponse.customError(ApiStatus.CustomError.JSOUP_NOT_FOUND_ERROR, e.getMessage());
        }
        return ApiResponse.customError(ApiStatus.CustomError.JSOUP_ERROR, e.getMessage());
    }
}
