package com.example.aichat.model;

public class AskResponse {
    private String reply;

    public AskResponse() { }

    public AskResponse(String reply) { this.reply = reply; }

    public String getReply() { return reply; }

    public void setReply(String reply) { this.reply = reply; }
}
