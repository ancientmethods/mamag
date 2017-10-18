package com.mamags.mamag.model;

/**
 * Created by Samer on 18/10/2017.
 */

public class FDSresponse {

    private int ResponseCode;
    private String ErrorMessage ="";

    public int getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(int responseCode) {
        this.ResponseCode = responseCode;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.ErrorMessage = errorMessage;
    }
}
