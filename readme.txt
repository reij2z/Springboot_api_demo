Spring Boot が起動
→ 内部で Tomcat（Webサーバー） が立ち上がり、http://localhost:8080 で待ち受け開始。
（ログに Tomcat started on port 8080）

ブラウザからアクセス

http://localhost:8080/hello

http://localhost:8080/messages
→ リクエストがサーバーに飛ぶ

DispatcherServlet が処理
Spring Boot の「受付係」がリクエストを見て、どのメソッドに渡すかを判断。

HelloController が実行

/hello → メソッドが呼ばれて {"message":"こんにちは、Reiji！"} みたいなJSONを返す

/messages → 複数のMapをリストに入れて返す → 自動でJSON配列に変換される

Spring Boot が JSON に変換

Javaの Map や List を Jackson というライブラリで JSON形式 にシリアライズして返却。

ブラウザには [{"id":"1","message":"こんにちは！"}, …] のように見える。