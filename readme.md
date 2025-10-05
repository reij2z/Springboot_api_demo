````md
# Spring Boot + PostgreSQL メモ帳アプリ（バックエンド）

簡単な **メモ帳 REST API（CRUD）** を **Spring Boot 3 + PostgreSQL** で実装したデモです。  
Next.js フロントエンドからメモを登録・閲覧・更新・削除できるように構成しています。

---

## 特長

- Java 17+ / Spring Boot 3.x
- Spring Data JPA（PostgreSQL 使用）
- JSON ベースの REST API
- メモを DB に保存（永続化対応）
- CORS 設定済み（フロント連携用）
- Next.js フロントエンドとの連携を想定

---

## 動作環境

| 項目 | 推奨バージョン |
|------|----------------|
| Java | 17 以上 |
| Maven | 3.9+ |
| PostgreSQL | 15〜18 |
| IDE | VS Code / IntelliJ IDEA |
| フロント | Next.js（別リポジトリ） |

---

## セットアップ手順

### 1️ クローン

```bash
git clone https://github.com/reij2z/Springboot_api_demo.git
cd Springboot_api_demo
````

### 2️ PostgreSQL 設定

PostgreSQL に `demo` データベースを作成し、ユーザー情報を設定します。

```sql
CREATE DATABASE demo;
CREATE USER reiji WITH PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE demo TO reiji;
```

`src/main/resources/application.properties` を開いて、以下を確認・設定：

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/demo
spring.datasource.username=reiji
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

---

### 3️ アプリ起動

#### PowerShell（Windows）

```bash
.\mvnw.cmd spring-boot:run
```

#### macOS / Linux

```bash
./mvnw spring-boot:run
```

ブラウザで
[http://localhost:8080/notes](http://localhost:8080/notes)
を開いて JSON が表示されれば成功です。

---

## API エンドポイント

### 1. すべてのメモを取得

```
GET /notes
```

レスポンス例：

```json
[
  { "id": 1, "content": "初めてのメモ" },
  { "id": 2, "content": "Spring BootとNext.js連携テスト" }
]
```

---

### 2. メモを追加

```
POST /notes
```

リクエスト例：

```json
{ "content": "新しいメモです" }
```

レスポンス：

```json
{ "id": 3, "content": "新しいメモです" }
```

---

### 3. メモを更新

```
PUT /notes/{id}
```

リクエスト例：

```json
{ "content": "内容を更新しました！" }
```

レスポンス：

```json
{ "id": 1, "content": "内容を更新しました！" }
```

---

### 4. メモを削除

```
DELETE /notes/{id}
```

レスポンス：

```json
{ "deleted": true }
```

---

### 5. メモを検索（部分一致）

```
GET /notes/search?keyword=Spring
```

レスポンス：

```json
[
  { "id": 2, "content": "Spring BootとNext.js連携テスト" }
]
```

---

## プロジェクト構成

```
Springboot_api_demo/
├─ src/main/java/com/example/demo/
│  ├─ DemoApplication.java         # 起動クラス
│  ├─ Note.java                    # エンティティ
│  ├─ NoteRepository.java          # リポジトリ（JPA）
│  ├─ NoteController.java          # コントローラ（REST API）
│  ├─ NoteCreateRequest.java       # POST用DTO
│  ├─ NoteUpdateRequest.java       # PUT用DTO
│  ├─ WebConfig.java               # CORS設定
│  └─ ...
├─ src/main/resources/
│  └─ application.properties
├─ pom.xml
└─ README.md
```

---

## フロント連携（Next.js）

Next.js で作成したフロントエンド
[https://github.com/reij2z/Springboot_api_frontend](https://github.com/reij2z/Springboot_api_frontend)

### フロント側で実行できる操作

* メモ一覧の取得（`GET /notes`）
* 新規メモ作成（`POST /notes`）
* 更新（`PUT /notes/{id}`）
* 削除（`DELETE /notes/{id}`）
* 検索（`GET /notes/search?keyword=`）

---

## データ永続化の確認

アプリを再起動しても、メモが保持されるか確認：

```bash
"C:\Program Files\PostgreSQL\18\bin\psql.exe" -h localhost -U reiji -d demo
demo=> SELECT * FROM notes;
```

---

## よくあるエラー

| エラー                                           | 原因             | 対処                          |
| --------------------------------------------- | -------------- | --------------------------- |
| `Failed to determine a suitable driver class` | DB接続設定の誤り      | URL・ユーザー名・パスワード確認           |
| `Connection refused (localhost:5432)`         | PostgreSQLが停止中 | サービスを起動                     |
| `Cross-Origin Request Blocked`                | フロント連携時        | `WebConfig.java` の CORS設定確認 |

---

## 作者

**越智 玲仁（Reiji Ochi）**
GitHub: [reij2z](https://github.com/reij2z)

---

## ライセンス

MIT License

```

---