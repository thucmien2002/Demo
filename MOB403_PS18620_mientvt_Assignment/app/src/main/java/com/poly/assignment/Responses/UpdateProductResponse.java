package com.poly.assignment.Responses;

public class UpdateProductResponse {

    private Boolean result, status;

    public UpdateProductResponse() {
    }

    public UpdateProductResponse(Boolean result, Boolean status) {
        this.result = result;
        this.status = status;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
