package com.wdw.wdw.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ExceptionCode {
    // 추가적인 ENTITY CODE 는 아래에 추가 및 Exception Class 생성
    ENTITY_NOT_FOUND(HttpStatus.BAD_REQUEST, "Global001", "Entity Not Found"),

    INVALID_PROVIDER_TYPE(HttpStatus.BAD_REQUEST, "Authentication001", "Invalid Provider Type");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ExceptionCode(final HttpStatus status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
