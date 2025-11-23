pipeline {
    agent any

    tools {
        maven 'Maven-3.8.6'
        jdk 'OpenJDK-17'
    }

    environment {
        DOCKER_REGISTRY = credentials('docker-hub')
        APP_VERSION = "${env.BUILD_ID}"
    }

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Unit Tests') {
            steps {
                sh 'mvn test -Dtest=**/*Test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Integration Tests') {
            steps {
                sh 'mvn test -Dtest=**/*IT'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Build Package') {
            when {
                branch 'main'
            }
            steps {
                sh 'mvn clean package -DskipTests'
                archiveArtifacts 'target/*.jar'
            }
        }

        stage('Build Docker Image') {
            when {
                branch 'main'
            }
            steps {
                script {
                    docker.build("my-app:${env.APP_VERSION}")
                }
            }
        }

        stage('Push Docker Image') {
            when {
                branch 'main'
            }
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'docker-hub') {
                        docker.image("my-app:${env.APP_VERSION}").push()
                        docker.image("my-app:${env.APP_VERSION}").push('latest')
                    }
                }
            }
        }

        stage('Deploy to Staging') {
            when {
                branch 'main'
            }
            steps {
                sh 'echo "Deploying to staging environment..."'
                // Команды деплоя
            }
        }

        stage('Deploy to Production') {
            when {
                branch 'main'
            }
            steps {
                input message: 'Deploy to production?', ok: 'Deploy'
                sh 'echo "Deploying to production..."'
                // Команды деплоя
            }
        }
    }

    post {
        always {
            emailext (
                subject: "Build Result: ${currentBuild.currentResult} - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "Check console output at: ${env.BUILD_URL}",
                to: "team@company.com"
            )
        }
        success {
            slackSend channel: '#builds', message: "Build SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
        }
        failure {
            slackSend channel: '#builds', message: "Build FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
        }
    }
}