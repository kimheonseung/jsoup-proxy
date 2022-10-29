package com.devh.project.jsoup.service;

import com.devh.project.jsoup.dto.JsoupRequest;
import com.devh.project.jsoup.helper.JsoupHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JsoupProxyServiceImpl implements JsoupProxyService
{

    private final JsoupHelper jsoupHelper;

    @Autowired
    public JsoupProxyServiceImpl(
            JsoupHelper jsoupHelper
    )
    {
        this.jsoupHelper = jsoupHelper;
    }

    @Override
    public String getDocumentHtml(JsoupRequest jsoupRequest)
    {
        return jsoupHelper.getDocument(jsoupRequest).html();
    }
}
