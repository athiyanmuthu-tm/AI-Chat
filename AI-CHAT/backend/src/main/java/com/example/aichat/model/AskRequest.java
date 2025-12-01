package com.example.aichat.model;

public class AskRequest {
    private String message;

    public AskRequest() { }

    public AskRequest(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
