package com.devh.project.jsoup.helper;

import com.devh.project.jsoup.dto.JsoupRequest;
import com.devh.project.jsoup.exception.JsoupException;
import com.devh.project.jsoup.exception.JsoupMethodException;
import com.devh.project.jsoup.exception.JsoupNotFoundException;
import com.devh.project.jsoup.exception.JsoupUrlException;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JsoupHelper
{
    public Document getDocument(JsoupRequest jsoupRequest)
    {
        try
        {
            return toDocument(jsoupRequest);
        }
        catch (HttpStatusException e)
        {
            if (e.getStatusCode() == 404)
            {
                throw new JsoupNotFoundException(e, HttpStatus.resolve(e.getStatusCode()));
            }
            throw new JsoupException(e, HttpStatus.resolve(e.getStatusCode()));
        }
        catch (JsoupException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new JsoupException(e);
        }
    }

    private Document toDocument(JsoupRequest jsoupRequest) throws IOException
    {
        if (!jsoupRequest.hasUrl())
        {
            throw new JsoupUrlException("url must not be null.");
        }
        if (!jsoupRequest.hasMethod())
        {
            throw new JsoupMethodException("method must not be null.");
        }

        Connection connection = Jsoup.connect(jsoupRequest.getUrl());

        if (jsoupRequest.hasUserAgent())
        {
            connection.userAgent(jsoupRequest.getUserAgent());
        }
        if (jsoupRequest.hasCookies())
        {
            connection.cookies(jsoupRequest.getCookies());
        }
        if (jsoupRequest.hasHeaders())
        {
            connection.headers(jsoupRequest.getHeaders());
        }
        if (jsoupRequest.hasData())
        {
            connection.data(jsoupRequest.getData());
        }

        final String strMethod = jsoupRequest.getMethod().toUpperCase();
        return switch (Connection.Method.valueOf(strMethod))
                {
                    case GET -> connection.get();
                    case POST -> connection.post();
                    default -> throw new JsoupMethodException("Not support method. "+strMethod);
                };
    }
}
