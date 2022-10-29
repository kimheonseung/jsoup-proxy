package com.devh.project.jsoup.exception;

import org.springframework.http.HttpStatus;

public class JsoupUrlException extends JsoupException
{
    public JsoupUrlException(Throwable e) {
        super(e);
    }
    public JsoupUrlException(Throwable e, HttpStatus status) {
        super(e, status);
    }
    public JsoupUrlException(String message) {
        super(message);
    }
}
