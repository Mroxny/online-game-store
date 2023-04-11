package com.mroxny.ogs;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ResponseDTO {
    private HttpStatus code;
    private String message;
    private List<Game> content;


    public ResponseDTO(HttpStatus code, String message, List<Game> content) {
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

    public List<Game> getContent() {
        return content;
    }

    public void setContent(List<Game> content) {
        this.content = content;
    }
}
