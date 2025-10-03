package com.example.demo;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class MessageController {

    private final MessageRepository repo;

    public MessageController(MessageRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/messages")
    public List<Message> getMessages() {
        return repo.findAll();
    }

    // リクエスト用のシンプルなDTO。クラスにしておく（recordでも可）
    public static class CreateMessageRequest {
        private String message;
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }

    @PostMapping("/messages")
    public Message addMessage(@RequestBody CreateMessageRequest req) {
        if (req == null || req.getMessage() == null || req.getMessage().isBlank()) {
            throw new IllegalArgumentException("message は必須です");
        }
        return repo.save(new Message(req.getMessage()));
    }
}
