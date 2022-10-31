package com.devh.project.jsoup.advice;

import com.devh.project.common.constant.ApiStatus;
import com.devh.project.common.dto.ApiResponse;
import com.devh.project.jsoup.exception.JsoupException;
import com.devh.project.jsoup.exception.JsoupNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class JsoupProxyAdvice
{
    @ExceptionHandler({JsoupException.class})
    public ApiResponse<Exception> handleJsoupException(Exception e)
    {
        log.error("jsoup exception: ", e);
        if (e instanceof JsoupNotFoundException)
        {
            return ApiResponse.customError(ApiStatus.CustomError.JSOUP_NOT_FOUND_ERROR, e.getMessage());
        }
        return ApiResponse.customError(ApiStatus.CustomError.JSOUP_ERROR, e.getMessage());
    }
}
