package com.devh.project.jsoup.dto;

import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Map;

@Builder
@Getter
public class JsoupRequest
{
    private String url;
    private String userAgent;
    private Map<String, String> cookies;
    private Map<String, String> headers;
    private String method;
    private Map<String, String> data;

    private JsoupRequest() {}

    private JsoupRequest(
            String url
            , String userAgent
            , Map<String, String> cookies
            , Map<String, String> headers
            , String method
            , Map<String, String> data
    )
    {
        this.url = url;
        this.userAgent = userAgent;
        this.cookies = cookies;
        this.headers = headers;
        this.method = method;
        this.data = data;
    }

    public boolean hasUrl()
    {
        return StringUtils.isNotEmpty(this.url);
    }

    public boolean hasUserAgent()
    {
        return StringUtils.isNotEmpty(this.userAgent);
    }

    public boolean hasCookies()
    {
        return !CollectionUtils.isEmpty(this.cookies);
    }

    public boolean hasHeaders()
    {
        return !CollectionUtils.isEmpty(this.headers);
    }

    public boolean hasData()
    {
        return !CollectionUtils.isEmpty(this.data);
    }

    public boolean hasMethod()
    {
        return !StringUtils.isEmpty(this.method);
    }
}
