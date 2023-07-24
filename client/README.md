# yozm-cafe 프론트엔드

## 환경 변수

|환경 변수|설명|기본값|
|-|-|-|
|**NODE_ENV**|개발 환경(`'development'`) 혹은 운영 환경(`'production'`) 여부를 나타내는 환경 변수입니다.|`'development'`|
|**MSW**|MSW(Mock Service Worker) 사용 여부입니다. 만약 값을 설정하지 않았다면 **NODE_ENV** 환경 변수에 따라 설정됩니다. 만약 **NODE_ENV**가 `'development'`라면 **MSW**는 `'true'`로 설정되며 다른 값일 경우 `'false'` 로 설정됩니다.||
|**API_URL**|프론트엔드에서 API 요청에 사용할 주소입니다.|`'http://localhost:8080'`|
