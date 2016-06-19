package com.upgrad.movieon.Api;


import java.io.Serializable;

/**
 * Created by Ajeet Kumar Meena on 18-06-2016.
 */
public class ApiResponse implements Serializable {

    int responsecode;

    String message;

    /**
     * @return Api response code. Only 200 => success
     */
    public int getResponsecode() {
        return responsecode;
    }

    /**
     * @return Api response message. Contains additional message from server about the
     * last request or more info on reason for failure.
     */
    public String getMessage() {
        return message;
    }
}
