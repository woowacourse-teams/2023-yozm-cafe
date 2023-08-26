FROM gradle:8.1.1-jdk as build

WORKDIR /work

# 빌드 스테이지에 소스 코드 파일들 추가
COPY src ./src
COPY build.gradle .
COPY settings.gradle .

# 테스트를 제외하고 빌드 진행
RUN gradle build -x test
RUN mv /work/build/libs/*[!-plain].jar ./app.jar


FROM eclipse-temurin:17-jre

COPY --from=build /work/app.jar .

ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh ./wait-for-it.sh
RUN chmod +x ./wait-for-it.sh

CMD ./wait-for-it.sh -t 0 mysql:3306 -- java -jar ./app.jar
