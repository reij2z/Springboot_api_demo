# Spring Boot API Demo

簡単な **REST API（GET/POST）** を Spring Boot 3 + PostgreSQL で実装したデモです。  
最小構成から DB 永続化まで対応しています。

---

## 特長

- Java 17+ / Spring Boot 3.x
- PostgreSQL 18 (JPA / Hibernate 使用)
- JSON レスポンス（単一 / 配列）
- POST で JSON を受け取り DB に保存
- CRUD 拡張のベースに利用可能

---

## 動作環境

- Java **17 以上**
- PostgreSQL **18** （port=5432, DB名=demo, ユーザ=reiji）
- Git / PowerShell or ターミナル
- （推奨）VS Code または IntelliJ IDEA

---

## セットアップ & 起動

### 1) ソース取得

```bash
git clone https://github.com/reij2z/Springboot_api_demo.git
cd Springboot_api_demo
````

### 2) DB準備

PostgreSQL に demo DB を作成:

```sql
CREATE DATABASE demo;
\c demo;
```

起動時に `messages` テーブルが自動生成されます。

### 3) アプリ起動

#### Windows (PowerShell)

```powershell
.\mvnw.cmd spring-boot:run
```

#### macOS / Linux

```bash
./mvnw spring-boot:run
```

起動ログに `Tomcat started on port 8080` が出れば成功。
停止は `Ctrl + C`。

---

## エンドポイント

### 1. 動作確認

* `GET /`
  レスポンス例: `"UP! Try GET /hello or /messages"`

### 2. 単一メッセージ

* `GET /hello?name=Reiji`

  ```json
  { "message": "こんにちは、Reiji！" }
  ```

* `GET /hello`

  ```json
  { "message": "こんにちは、World！" }
  ```

### 3. メッセージ一覧（DBから取得）

* `GET /messages`

  ```json
  [
    {"id":1,"message":"こんにちは！"},
    {"id":2,"message":"Spring Boot 勉強中です"}
  ]
  ```

### 4. メッセージ追加（DBに保存）

* `POST /messages`
  Body:

  ```json
  { "message": "Postmanからのテスト" }
  ```

  レスポンス例:

  ```json
  { "id":3,"message":"Postmanからのテスト" }
  ```

---

## プロジェクト構成

```
Springboot_api_demo/
├─ pom.xml
├─ mvnw / mvnw.cmd
└─ src/
   └─ main/
      ├─ java/com/example/demo/
      │  ├─ DemoApplication.java
      │  ├─ HelloController.java
      │  ├─ Message.java              # Entity
      │  ├─ MessageRepository.java    # Repository
      │  └─ MessageController.java    # REST Controller
      └─ resources/
         └─ application.properties    # DB接続設定
```

---

## よくあるエラーと対処

* **Failed to determine a suitable driver class**
  → `spring.datasource.url`, `username`, `password` の設定忘れ

* **relation "messages" does not exist**
  → 初回起動で Hibernate が自動生成するため、DB 準備を確認

* **404 Not Found**
  → パスの打ち間違い。`/hello`, `/messages` を確認

---
## 今後の拡張（Roadmap）

### Step 1: メッセージ機能の完成

* `PUT /messages/{id}`（更新）/ `DELETE /messages/{id}`（削除）で CRUD を完成
* Next.js フロントエンドと連携

  * 入力フォームからメッセージを `POST` → API に送信
  * DB（PostgreSQL）に保存
  * 一覧をフロントで表示

---

### Step 2: メモ帳アプリ（Note API）

* エンティティ: `Note(id, title, content, createdAt, updatedAt)`
* API: `GET /notes`, `POST /notes`, `PUT /notes/{id}`, `DELETE /notes/{id}`

  * ページング対応で一覧取得
  * タイトルは任意、本文は必須
* Next.js フロントからフォーム投稿 → 一覧表示・編集・削除が可能
* DB に永続化（再起動してもデータが残る）

---

## ライセンス

MIT License

---

### 作者

* 越智 玲仁（Reiji Ochi）