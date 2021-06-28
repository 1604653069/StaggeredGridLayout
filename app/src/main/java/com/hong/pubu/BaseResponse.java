package com.hong.pubu;

public class BaseResponse<T> {
    private int status;
    private T data;

    public BaseResponse() {
    }

    public BaseResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "status=" + status +
                ", data=" + data +
                '}';
    }
}
