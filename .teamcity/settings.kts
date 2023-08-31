import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildFeatures.sshAgent
import jetbrains.buildServer.configs.kotlin.buildSteps.SSHUpload
import jetbrains.buildServer.configs.kotlin.buildSteps.nodeJS
import jetbrains.buildServer.configs.kotlin.buildSteps.sshUpload
import jetbrains.buildServer.configs.kotlin.buildSteps.dockerCompose
import jetbrains.buildServer.configs.kotlin.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.buildSteps.sshExec
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

version = "2023.05"

// TeamCity에 프로젝트를 추가할 때 입력해야 하는 초기 값들입니다
val repository = DslContext.getParameter("repository")

// 배포 대상 서버의 정보(host, port, username)을 입력합니다.
val deployTargetProdHost = DslContext.getParameter("deploy_target.prod.host")
val deployTargetProdPort = DslContext.getParameter("deploy_target.prod.port")
val deployTargetProdUsername = DslContext.getParameter("deploy_target.prod.username")
val deployTargetDevHost = DslContext.getParameter("deploy_target.dev.host")
val deployTargetDevPort = DslContext.getParameter("deploy_target.dev.port")
val deployTargetDevUsername = DslContext.getParameter("deploy_target.dev.username")

// 빌드 알림을 받을 Webhook URL을 설정합니다
val webhookUrl = DslContext.getParameter("webhook.url")

project {
    description = "yozm.cafe 프로젝트의 CI/CD 파이프라인 스크립트입니다"

    val gitHubProd = GitVcsRoot {
        id = RelativeId("ProdVcs")
        name = "2023-yozm-cafe prod"
        url = repository
        branch = "main"
        branchSpec = "+:refs/heads/main"
    }

    val gitHubDev = GitVcsRoot {
        id = RelativeId("DevVcs")
        name = "2023-yozm-cafe dev"
        url = repository
        branch = "dev"
        branchSpec = "+:refs/heads/dev"
    }

    vcsRoot(gitHubProd)
    vcsRoot(gitHubDev)

    // 4개의 빌드 설정을 추가합니다
    buildType(ServerBuildType("prod", gitHubProd, deployTargetProdHost, deployTargetProdPort, deployTargetProdUsername))
    buildType(ServerBuildType("dev", gitHubDev, deployTargetDevHost, deployTargetDevPort, deployTargetDevUsername))
    buildType(ClientBuildType("prod", gitHubProd, deployTargetProdHost, deployTargetProdPort, deployTargetProdUsername))
    buildType(ClientBuildType("dev", gitHubDev, deployTargetDevHost, deployTargetDevPort, deployTargetDevUsername))

    // 웹훅 설정을 추가합니다
    params {
        param("teamcity.internal.webhooks.enable", "true")
        param("teamcity.internal.webhooks.events", "BUILD_STARTED;BUILD_FINISHED;BUILD_INTERRUPTED")
        param("teamcity.internal.webhooks.url", webhookUrl)
    }
}

open class ServerBuildType(
        private val buildMode: String,
        private val vcsRoot: VcsRoot,
        private val deployTargetHost: String,
        private val deployTargetPort: String,
        private val deployTargetUsername: String,
) : BuildType({
    id("YozmCafe_Server_$buildMode")
    name = "Server:$buildMode"
    description = "서버 CI/CD"

    vcs {
        root(vcsRoot)
        cleanCheckout = true
    }

    params {
        param("DEPLOY_TARGET_HOST", deployTargetHost)
        param("DEPLOY_TARGET_PORT", deployTargetPort)
        param("DEPLOY_TARGET_USERNAME", deployTargetUsername)
    }

    steps {
        script {
            name = "개발 서버용 Profile 생성"
            workingDir = "server/src/main/resources"
            scriptContent = """echo "%env.APPLICATION_DEV%" >> application-dev.properties"""
        }
        dockerCompose {
            name = "MySQL 설정"
            file = "server/docker-compose.yml"
        }
        gradle {
            name = "빌드"
            tasks = "clean build"
            buildFile = "server/build.gradle"
            gradleParams = "-Pprofile=$buildMode"
            gradleWrapperPath = "server"
        }
        sshUpload {
            name = "빌드 파일 업로드"
            transportProtocol = SSHUpload.TransportProtocol.SCP
            sourcePath = "server/build/libs/yozm-cafe-0.0.1-SNAPSHOT.jar"
            targetUrl = "%DEPLOY_TARGET_HOST%"
            param("jetbrains.buildServer.sshexec.port", "%DEPLOY_TARGET_PORT%")
            authMethod = sshAgent {
                username = "%DEPLOY_TARGET_USERNAME%"
            }
        }
        sshExec {
            name = "배포"
            commands = """
                if sudo lsof -i :8080; then
                    echo "Port 8080 is already in use. Killing the process..."
                    sudo lsof -i :8080 | awk 'NR!=1 {print ${'$'}2}' | sudo xargs kill -9
                fi

                nohup java -jar yozm-cafe-0.0.1-SNAPSHOT.jar > nohup.out 2> nohup.err < /dev/null &
            """.trimIndent()
            targetUrl = "%DEPLOY_TARGET_HOST%"
            param("jetbrains.buildServer.sshexec.port", "%DEPLOY_TARGET_PORT%")
            authMethod = sshAgent {
                username = "%DEPLOY_TARGET_USERNAME%"
            }
        }
    }

    triggers {
        vcs {
            triggerRules = "+:/server"
        }
    }

    features {
        perfmon {
        }
        sshAgent {
            teamcitySshKey = "yozm-cafe.pem"
        }
    }
})

open class ClientBuildType(
        private val buildMode: String,
        private val vcsRoot: VcsRoot,
        private val deployTargetHost: String,
        private val deployTargetPort: String,
        private val deployTargetUsername: String,
) : BuildType({
    id("YozmCafe_Client_$buildMode")
    name = "Client:$buildMode"
    description = "클라이언트 CI/CD"

    vcs {
        root(vcsRoot)
        cleanCheckout = true
    }

    params {
        param("DEPLOY_TARGET_HOST", deployTargetHost)
        param("DEPLOY_TARGET_PORT", deployTargetPort)
        param("DEPLOY_TARGET_USERNAME", deployTargetUsername)
    }

    steps {
        nodeJS {
            name = "Install dependencies and build"
            workingDir = "client"
            shellScript = """
                yarn install --frozen-lockfile
                yarn build
            """.trimIndent()
        }
        sshUpload {
            name = "Deploy build files"
            transportProtocol = SSHUpload.TransportProtocol.SCP
            sourcePath = "client/dist/**"
            targetUrl = "%DEPLOY_TARGET_HOST%:public"
            param("jetbrains.buildServer.sshexec.port", "%DEPLOY_TARGET_PORT%")
            authMethod = sshAgent {
                username = "%DEPLOY_TARGET_USERNAME%"
            }
        }
    }

    triggers {
        vcs {
            triggerRules = "+:/client"
        }
    }

    features {
        perfmon {
        }
        sshAgent {
            teamcitySshKey = "yozm-cafe.pem"
        }
    }
})
