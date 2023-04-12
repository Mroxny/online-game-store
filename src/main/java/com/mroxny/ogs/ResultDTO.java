package com.mroxny.ogs;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ResultDTO<T> {
    private HttpStatus code;
    private String message;
    private T content;


    public ResultDTO(HttpStatus code, String message, T content) {
        this.code = code;
        this.message = message;
        this.content = content;
    }

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
