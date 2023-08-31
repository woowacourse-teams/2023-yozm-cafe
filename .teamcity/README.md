# CI Pipeline Configuration

TeamCity의 IaC 기능을 활용하기 위해 사용하는 디렉토리 입니다.

TeamCity에서 Project를 추가할 시, `.teamcity` 디렉토리를 인식하고 Kotlin DSL을 실행하여 파이프라인 셋업을 진행할 것입니다.

IntelliJ Idea에서 프로젝트 폴더를 열 때, .teamcity 폴더를 열면 됩니다.

## 초기 입력 파라미터
CI Pipeline을 셋업할 때, 몇 가지 초기 파라미터의 입력을 요구합니다.

|파라미터 명|설명|예시|
|-|-|-|
|`repository`|변경 사항을 감시할 대상 Git Repository 입니다|`https://github.com/woowacourse-teams/2023-yozm-cafe`|
|`deploy_target.prod.host`|Prod 환경에서 배포할 대상의 SSH host입니다|`192.168.0.200`|
|`deploy_target.prod.port`|Prod 환경에서 배포할 대상의 SSH port입니다|`22`|
|`deploy_target.prod.username`|Prod 환경에서 배포할 대상의 SSH username입니다|`myuser`|
|`deploy_target.dev.host`|Dev 환경에서 배포할 대상의 SSH host입니다|`192.168.0.201`|
|`deploy_target.dev.port`|Dev 환경에서 배포할 대상의 SSH port입니다|`22`|
|`deploy_target.dev.username`|Dev 환경에서 배포할 대상의 SSH username입니다|`myuser`|

## 셋업 방법
1. Add Project를 클릭하여 이 레포지토리를 추가합니다.
2. 초기에 입력해야 하는 몇 가지 파라미터가 있습니다. 이는 [초기 입력 파라미터](초기-입력-파라미터) 문단을 참고해주세요.
3. 프로젝트 생성 후 배포 대상 호스트의 SSH 키 파일을 추가해주어야 합니다. Edit Project > SSH Keys 로 들어간 뒤 `yozm-cafe.pem` 파일 명으로 SSH 키 파일을 추가해주세요.
