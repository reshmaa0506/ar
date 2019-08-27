package com.example.zoom.utils;
import lombok.Builder;
import lombok.Data;

/**
 * App Response class.
 *
 * @param <T> generic
 */
@Data
@Builder
public class AppResponse<T> {

    private T data;

    private boolean success;

    private String message;

    /**
     * constructor.
     *
     * @param datum datum
     *//*
    public AppResponse(final T datum) {
        this.data = datum;
    }
*/
    /**
     * constructor.
     *
     * @param datum  datum
     * @param status success
     * @param msg    message
     */
    public AppResponse(final T datum, final boolean status,
                       final String msg) {
        this.data = datum;
        this.success = status;
        this.message = msg;
    }

    /**
     * The default constructor.
     */
    public AppResponse() {

    }
}

