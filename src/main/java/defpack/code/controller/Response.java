package defpack.code.controller;

import org.springframework.http.HttpStatus;

public class Response {

    private final HttpStatus status;
    private final Integer code;

    public Response(HttpStatus status, Integer code) {
        this.status = status;
        this.code = code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Integer getCode() {
        return code;
    }
}