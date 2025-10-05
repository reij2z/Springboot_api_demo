package com.example.demo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notes")
public class Note {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 120)
  private String title;

  @Lob
  private String content;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @PrePersist
  void onCreate() { createdAt = updatedAt = LocalDateTime.now(); }

  @PreUpdate
  void onUpdate() { updatedAt = LocalDateTime.now(); }

  public Note() {}
  public Note(String title, String content){ this.title=title; this.content=content; }

  public Long getId(){ return id; }
  public String getTitle(){ return title; }
  public void setTitle(String title){ this.title = title; }
  public String getContent(){ return content; }
  public void setContent(String content){ this.content = content; }
  public LocalDateTime getCreatedAt(){ return createdAt; }
  public LocalDateTime getUpdatedAt(){ return updatedAt; }
}
