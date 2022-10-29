package com.devh.project.jsoup.controller;

import com.devh.project.common.constant.ApiStatus;
import com.devh.project.common.dto.ApiResponse;
import com.devh.project.jsoup.dto.JsoupRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Collections;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class JsoupProxyControllerTests
{
    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext ctx;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void before() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Nested
    @DisplayName("성공 케이스")
    class 성공
    {
        @Test
        @DisplayName("도큐먼트 반환 (최소 정보)")
        void 도큐먼트_반환_최소_정보() throws Exception
        {
            // given
            final JsoupRequest givenJsoupRequest = JsoupRequest.builder()
                    .method("get")
                    .url("https://naver.com")
                    .build();
            // when
            final String content = objectMapper.writeValueAsString(givenJsoupRequest);
            MvcResult result = mockMvc
                    .perform(
                            MockMvcRequestBuilders
                                    .get("/api/jsoup-proxy")
                                    .content(content)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
            String resultContent = result.getResponse().getContentAsString();
            ApiResponse<String> apiResponse = objectMapper.readValue(resultContent, new TypeReference<>() {});
            int status = apiResponse.getStatus();
            List<String> dataList = apiResponse.getDataArray();
            String documentHtml = dataList.get(0);
            // then
            Assertions.assertThat(status).isEqualTo(ApiStatus.Success.OK.getCode());
            Assertions.assertThat(dataList).isNotEmpty();
            Assertions.assertThat(documentHtml).isNotNull();
        }

        @Test
        @DisplayName("도큐먼트 반환 (최대 정보)")
        void 도큐먼트_반환_최대_정보() throws Exception
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
            final String content = objectMapper.writeValueAsString(givenJsoupRequest);
            MvcResult result = mockMvc
                    .perform(
                            MockMvcRequestBuilders
                                    .get("/api/jsoup-proxy")
                                    .content(content)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
            String resultContent = result.getResponse().getContentAsString();
            ApiResponse<String> apiResponse = objectMapper.readValue(resultContent, new TypeReference<>() {});
            int status = apiResponse.getStatus();
            List<String> dataList = apiResponse.getDataArray();
            String documentHtml = dataList.get(0);
            // then
            Assertions.assertThat(status).isEqualTo(ApiStatus.Success.OK.getCode());
            Assertions.assertThat(dataList).isNotEmpty();
            Assertions.assertThat(documentHtml).isNotNull();
        }
    }

    @Nested
    @DisplayName("실패 케이스")
    class 실패
    {
        @Test
        @DisplayName("도큐먼트 반환 (url 없음)")
        void 도큐먼트_반환_url_없음() throws Exception
        {
            // given
            JsoupRequest givenJsoupRequest = JsoupRequest.builder()
                    .method("post")
                    .build();
            // when
            final String content = objectMapper.writeValueAsString(givenJsoupRequest);
            MvcResult result = mockMvc
                    .perform(
                            MockMvcRequestBuilders
                                    .get("/api/jsoup-proxy")
                                    .content(content)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
            // then
            String resultContent = result.getResponse().getContentAsString();
            ApiResponse<Exception> apiResponse = objectMapper.readValue(resultContent, new TypeReference<>() {});
            int status = apiResponse.getStatus();
            System.out.println(apiResponse.getDescription());
            // then
            Assertions.assertThat(status).isEqualTo(ApiStatus.CustomError.JSOUP_ERROR.getCode());
        }

        @Test
        @DisplayName("도큐먼트 반환 (method 없음)")
        void 도큐먼트_반환_method_없음() throws Exception
        {
            // given
            JsoupRequest givenJsoupRequest = JsoupRequest.builder()
                    .url("https://naver.com")
                    .build();
            // when
            final String content = objectMapper.writeValueAsString(givenJsoupRequest);
            MvcResult result = mockMvc
                    .perform(
                            MockMvcRequestBuilders
                                    .get("/api/jsoup-proxy")
                                    .content(content)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
            // then
            String resultContent = result.getResponse().getContentAsString();
            ApiResponse<Exception> apiResponse = objectMapper.readValue(resultContent, new TypeReference<>() {});
            int status = apiResponse.getStatus();
            System.out.println(apiResponse.getDescription());
            // then
            Assertions.assertThat(status).isEqualTo(ApiStatus.CustomError.JSOUP_ERROR.getCode());
        }

        @Test
        @DisplayName("도큐먼트 반환 (지원하지 않는 method)")
        void 도큐먼트_반환_지원하지_않는_method() throws Exception
        {
            // given
            JsoupRequest givenJsoupRequest = JsoupRequest.builder()
                    .url("https://naver.com")
                    .method("put")
                    .build();
            // when
            final String content = objectMapper.writeValueAsString(givenJsoupRequest);
            MvcResult result = mockMvc
                    .perform(
                            MockMvcRequestBuilders
                                    .get("/api/jsoup-proxy")
                                    .content(content)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
            // then
            String resultContent = result.getResponse().getContentAsString();
            ApiResponse<Exception> apiResponse = objectMapper.readValue(resultContent, new TypeReference<>() {});
            int status = apiResponse.getStatus();
            System.out.println(apiResponse.getDescription());
            // then
            Assertions.assertThat(status).isEqualTo(ApiStatus.CustomError.JSOUP_ERROR.getCode());
        }

        @Test
        @DisplayName("도큐먼트 반환 (잘못된 method)")
        void 도큐먼트_반환_잘못된_method() throws Exception
        {
            // given
            JsoupRequest givenJsoupRequest = JsoupRequest.builder()
                    .url("https://naver.com")
                    .method("put")
                    .build();
            // when
            final String content = objectMapper.writeValueAsString(givenJsoupRequest);
            MvcResult result = mockMvc
                    .perform(
                            MockMvcRequestBuilders
                                    .get("/api/jsoup-proxy")
                                    .content(content)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
            // then
            String resultContent = result.getResponse().getContentAsString();
            ApiResponse<Exception> apiResponse = objectMapper.readValue(resultContent, new TypeReference<>() {});
            int status = apiResponse.getStatus();
            System.out.println(apiResponse.getDescription());
            // then
            Assertions.assertThat(status).isEqualTo(ApiStatus.CustomError.JSOUP_ERROR.getCode());
        }
    }
}
