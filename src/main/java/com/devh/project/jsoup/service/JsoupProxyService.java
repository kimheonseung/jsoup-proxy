package com.devh.project.jsoup.service;

import com.devh.project.jsoup.dto.JsoupRequest;

public interface JsoupProxyService
{
    String getDocumentHtml(JsoupRequest jsoupRequest);
}
