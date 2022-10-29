package com.devh.project.jsoup.exception;

import org.springframework.http.HttpStatus;

public class JsoupException extends RuntimeException
{
    private HttpStatus status;
    public JsoupException(Throwable e)
    {
        super(e);
    }
    public JsoupException(Throwable e, HttpStatus status)
    {
        super(e);
        this.status = status;
    }
    public JsoupException(String message)
    {
        super(message);
    }
}
