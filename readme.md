---

# Spring Boot API Demo

簡単な REST API（GET/POST）を Spring Boot 3 で実装したデモです。

## 特長

* Java 17 / Spring Boot 3.x（組み込み Tomcat）
* JSON レスポンス（単一/配列）
* POST で JSON ボディを受け取りメモリに保存
* 最小構成（DBなし）→ 今後 H2 / JPA に拡張予定

## 動作環境

* Java **17 以上**
* Git / PowerShell or ターミナル
* （推奨）VS Code または IntelliJ IDEA

## セットアップ & 起動

### 1) 取得

```bash
git clone https://github.com/reij2z/Springboot_api_demo.git
cd Springboot_api_demo
```

### 2) 起動

* **Windows (PowerShell)**

  ```powershell
  .\mvnw.cmd spring-boot:run
  ```
* **macOS / Linux**

  ```bash
  ./mvnw spring-boot:run
  ```

起動ログに `Tomcat started on port 8080` が出れば OK。
停止は `Ctrl + C`。

> ポート変更したい場合：`src/main/resources/application.properties` に
> `server.port=8081` を追加。

## エンドポイント

### 1) 動作確認

* `GET /`
  レスポンス（例）：`"UP! Try GET /hello or /messages"`

### 2) 単一メッセージ（クエリで名前可変）

* `GET /hello?name=Reiji`
  レスポンス：

  ```json
  { "message": "こんにちは、Reiji！" }
  ```

* `GET /hello`

  ```json
  { "message": "こんにちは、World！" }
  ```

### 3) メッセージ一覧（配列）

* `GET /messages`
  レスポンス（例）：

  ```json
  [
    {"id":"1","message":"こんにちは！"},
    {"id":"2","message":"Spring Boot 勉強中です"},
    {"id":"3","message":"APIから返ってきました！"}
  ]
  ```

### 4) メッセージ追加（POST）

* `POST /messages`
  Header: `Content-Type: application/json`
  Body:

  ```json
  { "message": "Postmanからのテスト" }
  ```

  レスポンス（例）：

  ```json
  { "id":"4","message":"Postmanからのテスト" }
  ```

#### curl 例（Windows PowerShell）

```powershell
curl -s -H "Content-Type: application/json" ^
  -d "{\"message\":\"POSTテスト\"}" ^
  http://localhost:8080/messages
```

#### Postman 例

* Method: `POST`
* URL: `http://localhost:8080/messages`
* Body: `raw` / `JSON`
* JSON:

  ```json
  { "message": "Postmanからのテスト" }
  ```

## プロジェクト構成

```
Springboot_api_demo/
├─ pom.xml
├─ mvnw / mvnw.cmd
└─ src/
   └─ main/
      ├─ java/com/example/demo/
      │  ├─ DemoApplication.java
      │  └─ HelloController.java
      └─ resources/
         └─ application.properties  # 任意（ポート等の設定）
```

## 開発メモ

* 受け取った JSON は メモリ上のリスト に保存しています（サーバ再起動で初期化）
* コンソールログに POST の追加内容を出力（デバッグ用）

## よくあるエラーと対処

* **404 Not Found**: パスの打ち間違い or 再起動忘れ（`/hello`, `/messages` を確認）
* **ポート競合**: `application.properties` に `server.port=8081`

## ビルド（JAR 実行）

```bash
# Windows PowerShell
.\mvnw.cmd clean package
java -jar target\demo-0.0.1-SNAPSHOT.jar
```

## 今後の拡張（Roadmap）

* H2 / Spring Data JPA による 永続化（再起動してもデータ維持）
* PUT /messages/{id}（更新）/ DELETE /messages/{id}（削除）で CRUD 完成
* バリデーション（`spring-boot-starter-validation`）/ 例外ハンドラ整備
* CORS 設定（フロントエンド連携用）

## ライセンス

MIT（任意で変更可）

---

### 作者

* 越智 玲仁（Reiji Ochi）

---