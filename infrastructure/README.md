<!-- omit from toc -->
# Infrastructure

이 프로젝트에서 사용하는 인프라 코드들을 모아놓은 디렉토리입니다.

인프라에 사용되는 인스턴스의 종류는 현재 3가지 입니다: ci, dev, prod

<!-- omit from toc -->
## Table of Contents
- [운영](#운영)
  - [주요 인스턴스 목록](#주요-인스턴스-목록)
  - [환경변수](#환경변수)
  - [설치 방법](#설치-방법)
- [CI](#ci)
  - [주요 인스턴스 목록](#주요-인스턴스-목록-1)
  - [환경변수](#환경변수-1)
  - [설치 방법](#설치-방법-1)

<br><br>

## 운영
`infrastructure` 폴더의 파일들 및 `infrastructure/docker-compose.yml` 에는 운영 환경에 필요한 코드 및 서비스들이 있습니다.

### 주요 인스턴스 목록
* **nginx**: 프론트엔드 html, css, js 정적 리소스 서빙 및 백엔드 서버 리버스 프록시 역할을 수행합니다
* **MySQL**: 백엔드에서 사용하는 DB입니다
* **Spring**: API 요청 및 비즈니스 로직을 수행하는 Spring 서버 인스턴스입니다

### 환경변수
|이름|설명|예시|
|-|-|-|
|`MYSQL_ROOT_PASSWORD`|MySQL DB root 계정의 패스워드입니다|`supersecretpassword`|
|`MYSQL_USER`|MySQL DB 사용자 계정 이름입니다|`myuser`|
|`MYSQL_PASSWORD`|MySQL DB 사용자 계정의 패스워드입니다|`supersecretpassword`|
|`AUTH_KEY`|JWT 발급에 사용될 Secret Key 입니다|`supersecretkey`|
|`AUTH_GOOGLE_CLIENTID`|Google OAuth 에서 제공하는 Client ID 값입니다|`780816631155-gbvyo1o7r2pn95qc4ei9d61io4uh48hl.apps.googleusercontent.com`|
|`AUTH_GOOGLE_CLIENTSECRET`|Google OAuth 에서 제공하는 Client Secret 값입니다|`GOCSPX-XXX`|
|`AUTH_KAKAO_CLIENTID`|Kakao OAuth 에서 제공하는 Client ID 값입니다|`ccd9ea 136632b1fe01c0e918381`|
|`AUTH_KAKAO_CLIENTSECRET`|Kakao OAuth 에서 제공하는 Client Secret 값입니다|`l7BpJ8YxvFotJYEu2WkH4NsvWM9qy47E`|

### 설치 방법
```bash
docker compose up -d
```
nginx와 MySQL, Spring Server를 켜주면 됩니다.

<br><br>

## CI
`infrastructure/ci/` 폴더의 파일들 및 `infrastructure/ci/docker-compose.yml` 에는 CI/CD 환경에 필요한 코드 및 서비스들이 있습니다.

### 주요 인스턴스 목록
* **TeamCity**: CI/CD 시스템입니다
* **TeamCity Agent 1, 2, 3**: 빌드 및 배포를 수행하는 에이전트입니다.
* **Webhook Broker**: TeamCity Webhook 메세지를 Slack Webhook 으로 전달해주는 Webhook 브로커입니다.

### 환경변수
|이름|설명|예시|
|-|-|-|
|`SLACK_WEBHOOK_URL`|빌드 결과 알림을 받을 Slack Webhook URL 입니다|`https://hooks.slack.com/services/XXXXXXXXXXX/XXXXXXXXXXX/XXXXXXXXXXXXXXXXXXXXXXXX`|
|`SLACK_CHANNEL`|빌드 결과 알림 메세지가 전송될 Slack 채널입니다|`C05PNZ142H6S`|

### 설치 방법
```bash
cd ci
docker compose up -d
```
위의 명령으로 TeamCity 서버 및 에이전트, Webhook 브로커를 실행시킬 수 있습니다.
Agent Authorize는 Web UI에서 직접 해야 합니다.
