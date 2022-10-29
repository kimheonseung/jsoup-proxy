package com.devh.project.jsoup.helper;

import com.devh.project.jsoup.dto.JsoupRequest;
import com.devh.project.jsoup.exception.JsoupException;
import com.devh.project.jsoup.exception.JsoupMethodException;
import com.devh.project.jsoup.exception.JsoupUrlException;
import org.assertj.core.api.Assertions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class JsoupHelperTests
{
    JsoupHelper jsoupHelper;

    @BeforeEach
    void beforeEach()
    {
        this.jsoupHelper = new JsoupHelper();
    }

    @Test
    void document()
    {
        Document document = jsoupHelper.getDocument(JsoupRequest.builder()
                .method("get")
                .url("https://naver.com")
                .build());


        Jsoup.parse(document.html());
    }

    @Nested
    @DisplayName("성공 케이스")
    class 성공
    {
        @Test
        @DisplayName("도큐먼트 반환 (최소 정보)")
        void 도큐먼트_반환_최소_정보()
        {
            // given
            JsoupRequest givenJsoupRequest = JsoupRequest.builder()
                    .method("get")
                    .url("https://naver.com")
                    .build();
            // when
            Document document = jsoupHelper.getDocument(givenJsoupRequest);
            // then
            Assertions.assertThat(document).isNotNull();
        }

        @Test
        @DisplayName("도큐먼트 반환 (최대 정보)")
        void 도큐먼트_반환_최대_정보()
        {
            // given
            JsoupRequest givenJsoupRequest = JsoupRequest.builder()
                    .method("get")
                    .url("https://naver.com")
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36")
                    .headers(Collections.singletonMap("mockHeaderKey", "mockHeaderValue"))
                    .data(Collections.singletonMap("mockDataKey", "mockDataValue"))
                    .cookies(Collections.singletonMap("mockCookieKey", "mockCookieValue"))
                    .build();
            // when
            Document document = jsoupHelper.getDocument(givenJsoupRequest);
            // then
            Assertions.assertThat(document).isNotNull();
        }
    }

    @Nested
    @DisplayName("실패 케이스")
    class 실패
    {
        @Test
        @DisplayName("도큐먼트 반환 (url 없음)")
        void 도큐먼트_빈환_url_없음()
        {
            // given
            JsoupRequest givenJsoupRequest = JsoupRequest.builder()
                    .method("post")
                    .build();
            // when & then
            Assertions.assertThatThrownBy(
                    () -> jsoupHelper.getDocument(givenJsoupRequest)
            ).isInstanceOf(JsoupUrlException.class);
        }

        @Test
        @DisplayName("도큐먼트 반환 (method 없음)")
        void 도큐먼트_반환_method_없음()
        {
            // given
            JsoupRequest givenJsoupRequest = JsoupRequest.builder()
                    .url("https://naver.com")
                    .build();
            // when & then
            Assertions.assertThatThrownBy(
                    () -> jsoupHelper.getDocument(givenJsoupRequest)
            ).isInstanceOf(JsoupMethodException.class);
        }

        @Test
        @DisplayName("도큐먼트 반환 (지원하지 않는 method)")
        void 도큐먼트_반환_지원하지_않는_method()
        {
            // given
            JsoupRequest givenJsoupRequest = JsoupRequest.builder()
                    .url("https://naver.com")
                    .method("put")
                    .build();
            // when & then
            Assertions.assertThatThrownBy(
                    () -> jsoupHelper.getDocument(givenJsoupRequest)
            ).isInstanceOf(JsoupMethodException.class);
        }

        @Test
        @DisplayName("도큐먼트 반환 (잘못된 method)")
        void 도큐먼트_반환_잘못된_method()
        {
            // given
            JsoupRequest givenJsoupRequest = JsoupRequest.builder()
                    .url("https://naver.com")
                    .method("put")
                    .build();
            // when & then
            Assertions.assertThatThrownBy(
                    () -> jsoupHelper.getDocument(givenJsoupRequest)
            ).isInstanceOf(JsoupException.class);
        }
    }
}
