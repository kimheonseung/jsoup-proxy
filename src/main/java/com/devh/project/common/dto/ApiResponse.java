package com.devh.project.common.dto;

import com.devh.project.common.constant.ApiStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Builder
@Getter
public class ApiResponse<T>
{
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String description;
    private List<T> dataArray;
    private Paging paging;

    private ApiResponse() {}

    private ApiResponse(
            LocalDateTime timestamp
            , int status
            , String message
            , String description
            , List<T> dataArray
            , Paging paging
    )
    {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.description = description;
        this.dataArray = dataArray;
        this.paging = paging;
    }

    public static <T> ApiResponse<T> success(ApiStatus.Success status) {
        return new ApiResponse<>(
                LocalDateTime.now()
                , status.getCode()
                , status.getStatus()
                , status.getDescription()
                , Collections.emptyList()
                , null);
    }

    public static <T> ApiResponse<T> success(ApiStatus.Success status, List<T> dataArray) {
        return new ApiResponse<>(
                LocalDateTime.now()
                , status.getCode()
                , status.getStatus()
                , status.getDescription()
                , dataArray
                , Paging.build(1, 10, dataArray.size()));
    }

    public static <T> ApiResponse<T> success(ApiStatus.Success status, List<T> dataArray, Paging paging) {
        return new ApiResponse<>(
                LocalDateTime.now()
                , status.getCode()
                , status.getStatus()
                , status.getStatus()
                , dataArray
                , paging);
    }

    public static <T> ApiResponse<T> success(ApiStatus.Success status, T data) {
        return success(status, Collections.singletonList(data));
    }

    public static <T> ApiResponse<T> authError(ApiStatus.AuthError status) {
        return new ApiResponse<>(
                LocalDateTime.now()
                , status.getCode()
                , status.getStatus()
                , status.getDescription()
                , Collections.emptyList()
                , null);
    }

    public static <T> ApiResponse<T> serverError(ApiStatus.ServerError status, String stacktrace) {
        return new ApiResponse<>(
                LocalDateTime.now()
                , status.getCode()
                , status.getStatus()
                , stacktrace
                , Collections.emptyList()
                , null);
    }

    public static <T> ApiResponse<T> customError(ApiStatus.CustomError status, String stacktrace) {
        return new ApiResponse<>(
                LocalDateTime.now()
                , status.getCode()
                , status.getStatus()
                , stacktrace
                , Collections.emptyList()
                , null);
    }
}
