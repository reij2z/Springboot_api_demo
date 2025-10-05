package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class NoteController {
  private final NoteRepository repo;
  public NoteController(NoteRepository repo){ this.repo=repo; }

  // 一覧（更新日時降順）＋クエリで検索
  @GetMapping("/notes")
public List<Note> list(@RequestParam(required = false) String q) {
  String qq = (q == null) ? "" : q.trim().toLowerCase();
  return repo.findAll().stream()
      // タイトル／本文の部分一致
      .filter(n -> {
        if (qq.isEmpty()) return true;
        String t = n.getTitle() == null ? "" : n.getTitle().toLowerCase();
        String c = n.getContent() == null ? "" : n.getContent().toLowerCase();
        return t.contains(qq) || c.contains(qq);
      })
      // updatedAt 降順
      .sorted((a, b) -> {
        var ua = a.getUpdatedAt();
        var ub = b.getUpdatedAt();
        if (ua == null && ub == null) return 0;
        if (ua == null) return 1;
        if (ub == null) return -1;
        return ub.compareTo(ua);
      })
      .toList();
}

  @GetMapping("/notes/{id}")
  public Note get(@PathVariable Long id){
    return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @PostMapping("/notes")
  public Note create(@RequestBody NoteCreateRequest req){
    if (req.getTitle()==null || req.getTitle().isBlank())
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "title is required");
    return repo.save(new Note(req.getTitle(), req.getContent()));
  }

  @PutMapping("/notes/{id}")
  public Note update(@PathVariable Long id, @RequestBody NoteUpdateRequest req){
    var n = repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    if (req.getTitle()!=null && !req.getTitle().isBlank()) n.setTitle(req.getTitle());
    if (req.getContent()!=null) n.setContent(req.getContent());
    return repo.save(n);
  }

  @DeleteMapping("/notes/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id){
    if (!repo.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    repo.deleteById(id);
  }
}
