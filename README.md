# user-registration-demo

新規登録
curl -H "Content-Type:application/json" -i -X POST http://localhost:8080/user/register -d {\"email\":\"test4@example.com\",\"password\":\"pass\",\"name\":\"test4\"}
戻り値
{"userId":4,"name":"test4","password":"$2a$10$itKWfcI8GFSHNwJv1MWHHO7sOeg0BXlQLMdNGk6w3E5IHtKFfV54G","email":"test4@example.com","adminFlg":false}

ログイン
curl -i -X POST http://localhost:8080/login -d "email=test4@example.com" -d "password=pass"
ヘッダ情報
Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0NEBleGFtcGxlLmNvbSIsIm5iZiI6MTUzNjA0Nzg5MSwiZXhwIjoxNTM2MDQ4NDkxLCJpYXQiOjE1MzYwNDc4OTF9.5QHklpld0Y5yNWckKgq7UY5js4yYo1rC61cQh-4dF0U

put
curl -H "Content-Type:application/json" -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0NEBleGFtcGxlLmNvbSIsIm5iZiI6MTUzNjA0Nzg5MSwiZXhwIjoxNTM2MDQ4NDkxLCJpYXQiOjE1MzYwNDc4OTF9.5QHklpld0Y5yNWckKgq7UY5js4yYo1rC61cQh-4dF0U" -i -X PUT http://localhost:8080/user -d {\"userId\":\"4\",\"email\":\"test4@example.com\",\"password\":\"pass\",\"name\":\"test5\","adminFlg":false}
