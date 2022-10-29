package com.devh.project.jsoup.exception;

import org.springframework.http.HttpStatus;

public class JsoupMethodException extends JsoupException
{
    public JsoupMethodException(Throwable e) {
        super(e);
    }
    public JsoupMethodException(Throwable e, HttpStatus status) {
        super(e, status);
    }
    public JsoupMethodException(String message) {
        super(message);
    }
}
