package com.mroxny.ogs;

import lombok.Data;
import lombok.experimental.StandardException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class ResultDTO<T> {
    private HttpStatus code;
    private String message;
    private T content;


    public ResultDTO(HttpStatus code, String message, T content) {
        this.code = code;
        this.message = message;
        this.content = content;
    }


}
