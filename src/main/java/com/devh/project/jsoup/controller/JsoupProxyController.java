package com.devh.project.jsoup.controller;

import com.devh.project.common.constant.ApiStatus;
import com.devh.project.common.dto.ApiResponse;
import com.devh.project.jsoup.dto.JsoupRequest;
import com.devh.project.jsoup.service.JsoupProxyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/jsoup-proxy")
@Slf4j
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

    @PostMapping("/search")
    public ApiResponse<String> getJsoupProxy(
            @RequestBody JsoupRequest jsoupRequest
            , HttpServletRequest request
    )
    {
        log.info("[POST] api/jsoup-proxy - {}, {}", request.getRemoteAddr(), jsoupRequest.toString());
        return ApiResponse.success(ApiStatus.Success.OK, jsoupProxyService.getDocumentHtml(jsoupRequest));
    }
}
