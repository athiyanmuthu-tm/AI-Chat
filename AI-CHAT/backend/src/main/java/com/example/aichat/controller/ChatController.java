package com.example.aichat.controller;

import com.example.aichat.model.AskRequest;
import com.example.aichat.model.AskResponse;
import com.example.aichat.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Allow all origins. For production, restrict this.
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/ask")
    public ResponseEntity<AskResponse> ask(@RequestBody AskRequest request) {
        String reply = chatService.generateReply(request.getMessage());
        AskResponse response = new AskResponse(reply);
        return ResponseEntity.ok(response);
    }
}
