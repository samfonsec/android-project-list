#!groovy
pipeline {
    agent any
    environment {
        ANDROID_SDK_ROOT = "${env.ANDROID_SDK_ROOT}"
    }
    stages {
        stage('clean') {
            steps {
                sh './gradlew clean'
            }
        }
        stage('build') {
            steps {
                sh "./gradlew assembleDebug -Dorg.gradle.java.home=${env.JAVA8_HOME}"
            }
        }
        stage("SonarQube analysis") {
            steps {
                script {
                    try {
                        def projectKey = env.JOB_NAME
                        projectKey = URLDecoder.decode(projectKey, "UTF-8").replaceAll('/', ':').replaceAll('#', '').replaceAll('%2F', ':')
                        withSonarQubeEnv('sonar') {
                            sh "/opt/sonar-scanner/bin/sonar-scanner -Dsonar.projectKey=${projectKey} -Dsonar.projectVersion=${env.BUILD_NUMBER}"
                        }
                    } catch (error) {
                        sh "echo '${error}'"
                    }
                }
            }
        }
    }
}