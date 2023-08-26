<!-- omit from toc -->
# Infrastructure

이 프로젝트에서 사용하는 인프라 코드들을 모아놓은 디렉토리입니다.

인프라에 사용되는 인스턴스의 종류는 현재 3가지 입니다: ci, dev, prod

<!-- omit from toc -->
## Table of Contents
- [ci](#ci)
  - [주요 인스턴스 목록](#주요-인스턴스-목록)
  - [환경변수](#환경변수)
  - [설치 방법](#설치-방법)
- [prod](#prod)
  - [주요 인스턴스 목록](#주요-인스턴스-목록-1)
  - [환경변수](#환경변수-1)
  - [설치 방법](#설치-방법-1)

<br><br>

## ci
CI/CD를 수행하는 인스턴스입니다. ci/ 폴더의 파일들 및 `docker-compose.yml` 에는 CI/CD 환경에 필요한 코드 및 서비스들이 있습니다.

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

<br><br>

## prod
운영 환경에서 사용하는 인스턴스입니다. prod/ 폴더의 파일들 및 `docker-compose.yml` 에는 운영 환경에 필요한 코드 및 서비스들이 있습니다.

### 주요 인스턴스 목록
* **nginx**: 프론트엔드 html, css, js 정적 리소스 서빙 및 백엔드 서버 리버스 프록시 역할을 수행합니다
* **MySQL**: 백엔드에서 사용하는 DB입니다
* **certbot**: HTTPS 인증서 발급을 수행합니다

### 환경변수
|이름|설명|예시|
|-|-|-|
|`DOMAIN`|HTTPS 인증서를 발급받을 도메인 이름입니다|`www.example.com`|
|`EMAIL`|HTTPS 인증서를 발급받을 때 사용할 이메일입니다|`example@gmail.com`|
|`MYSQL_ROOT_PASSWORD`|MySQL DB root 계정의 패스워드입니다|`supersecretpassword`|
|`MYSQL_USER`|MySQL DB 사용자 계정 이름입니다|`myuser`|
|`MYSQL_PASSWORD`|MySQL DB 사용자 계정의 패스워드입니다|`supersecretpassword`|

### 설치 방법
```bash
cd prod
docker compose up certbot
```
먼저, certbot을 사용하여 HTTPS 인증서를 발급받아야 합니다. certbot은 [HTTP-01 challenge](https://letsencrypt.org/docs/challenge-types/#http-01-challenge) 방법을 사용하여 HTTPS 인증서 발급을 시도할 것입니다. 이 방법을 사용하기 위해 certbot은 80 포트를 점유하여 웹 서버를 열기 때문에 nginx와 함께 켜면 안됩니다.

```bash
docker compose up -d web
```
HTTPS 인증서 발급이 완료되면 nginx를 켜주세요.

```bash
docker compose up -d mysql
```
MySQL은 위 과정과 상관없이 별도로 켜주면 됩니다.
