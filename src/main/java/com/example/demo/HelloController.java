package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.*; // List, Map など

@RestController
public class HelloController {

    // ---- 簡易ストア（サーバー起動中だけ保持）----
    private final List<Map<String, String>> store = new ArrayList<>() {{
        add(new HashMap<>(Map.of("id","1","message","こんにちは！")));
        add(new HashMap<>(Map.of("id","2","message","Spring Boot 勉強中です")));
        add(new HashMap<>(Map.of("id","3","message","APIから返ってきました！")));
    }};

    // ---- GET: 単体（JSON）----
    @GetMapping("/hello")
    public Map<String, String> hello(@RequestParam(defaultValue = "World") String name) {
        return Map.of("message", "こんにちは、" + name + "！");
    }

    // ---- GET: 一覧（JSON配列）----
    @GetMapping("/messages")
    public List<Map<String, String>> messages() {
        return store;
    }

// ---- POST: 追加 {"message":"..."} を受け取って保存 ----
record CreateMessageRequest(String message) {}

@PostMapping("/messages")
public Map<String, String> add(@RequestBody CreateMessageRequest req) {
    if (req == null || req.message() == null || req.message().isBlank()) {
        throw new IllegalArgumentException("message は必須です");
    }

    String nextId = String.valueOf(store.size() + 1);

    Map<String, String> newItem = new HashMap<>();
    newItem.put("id", nextId);
    newItem.put("message", req.message());
    store.add(newItem);

    // ★ コンソールに出力（確認用）
    System.out.println("==== 新規メッセージ追加 ====");
    System.out.println("追加内容: " + newItem);
    System.out.println("現在のstore: " + store);

    return newItem; // ← Postmanに返るレスポンス
}


    // ---- ルート確認（任意）----
    @GetMapping("/")
    public String root() {
        return "UP! Try GET /hello, GET /messages, POST /messages";
    }

    // （任意）簡易エラーハンドリング：IllegalArgumentException を 400 に
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBadRequest(IllegalArgumentException ex) {
        return Map.of("error", ex.getMessage());
    }
}
