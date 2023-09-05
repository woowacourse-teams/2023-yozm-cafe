read -p "파일이름을 입력하세요: " description
timestamp=$(date +%Y%m%d%H%M%S)
filename="V${timestamp}__${description}.sql"
touch "./src/main/resources/db/migration/$filename"
echo "파일이 생성되었습니다: $filename"
