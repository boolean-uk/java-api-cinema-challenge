package com.booleanuk.api.cinema.responses;

public class Response<T> {
    protected String status;
    protected T data;

    public Response(String status, T data) {
        this.status = status;
        this.data = data;
    }

    public Response(T data) {
        this("success", data);
    }

    public Response() {
    }

    public void set(T data) {
        this.status = "success";
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
