package com.poly.assignment.Requests;

public class UploadRequest {

    private String base64;

    public UploadRequest(String base64) {
        this.base64 = base64;
    }

    public UploadRequest() {
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }
}
