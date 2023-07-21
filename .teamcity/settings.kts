package _Self.buildTypes

import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.v2018_2.buildFeatures.sshAgent
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.SSHUpload
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.nodeJS
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.sshUpload
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.dockerCompose
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.sshExec
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.sshUpload
import jetbrains.buildServer.configs.kotlin.v2018_2.triggers.vcs

object Server : BuildType({
    name = "Server"

    params {
        param("env.APPLICATION_DEV", """
        """.trimIndent())
    }

    vcs {
        root(HttpsGithubComWoowacourseTeams2023yozmCafeRefsHeadsMain)
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
            gradleParams = "-Pprofile=dev"
            gradleWrapperPath = "server"
        }
        sshUpload {
            name = "빌드 파일 업로드"
            transportProtocol = SSHUpload.TransportProtocol.SCP
            sourcePath = "server/build/libs/yozm-cafe-0.0.1-SNAPSHOT.jar"
            targetUrl = "ec2-43-201-57-25.ap-northeast-2.compute.amazonaws.com"
            authMethod = sshAgent {
                username = "ubuntu"
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
            targetUrl = "ec2-43-201-57-25.ap-northeast-2.compute.amazonaws.com"
            authMethod = sshAgent {
                username = "ubuntu"
            }
        }
    }

    triggers {
        vcs {
            triggerRules = "+:/server"
            branchFilter = "+:main"
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

object Client : BuildType({
    name = "Client"

    vcs {
        root(HttpsGithubComWoowacourseTeams2023yozmCafeRefsHeadsMain1)
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
            targetUrl = "ec2-43-201-57-25.ap-northeast-2.compute.amazonaws.com:public"
            authMethod = sshAgent {
                username = "ubuntu"
            }
        }
    }

    triggers {
        vcs {
            triggerRules = "+:/client"
            branchFilter = "+:main"
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
