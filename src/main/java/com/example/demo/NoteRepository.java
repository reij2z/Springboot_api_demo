package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
  // いったん追加メソッドなし（findAll, findById 等を使う）
}
