package com.devh.project.jsoup.exception;

import org.springframework.http.HttpStatus;

public class JsoupNotFoundException extends RuntimeException
{
    private HttpStatus status;
    public JsoupNotFoundException(Throwable e)
    {
        super(e);
    }
    public JsoupNotFoundException(Throwable e, HttpStatus status)
    {
        super(e);
        this.status = status;
    }
    public JsoupNotFoundException(String message)
    {
        super(message);
    }
}
