package com.devh.project.jsoup.controller;

import com.devh.project.common.constant.ApiStatus;
import com.devh.project.common.dto.ApiResponse;
import com.devh.project.jsoup.dto.JsoupRequest;
import com.devh.project.jsoup.service.JsoupProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/jsoup-proxy")
public class JsoupProxyController
{
    private final JsoupProxyService jsoupProxyService;

    @Autowired
    public JsoupProxyController(
            JsoupProxyService jsoupProxyService
    )
    {
        this.jsoupProxyService = jsoupProxyService;
    }

    @GetMapping
    public ApiResponse<String> getJsoupProxy(@RequestBody JsoupRequest jsoupRequest)
    {
        return ApiResponse.success(ApiStatus.Success.OK, jsoupProxyService.getDocumentHtml(jsoupRequest));
    }
}
