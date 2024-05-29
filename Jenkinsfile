pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Check Requirements') {
            steps {
                sh '''
                    docker version
                    docker info
                    docker-compose version
                '''
            }
        }

        stage('Build') {
            steps {
                sh "./gradlew compile"
            }
        }

        stage('Test') {
            steps {
                sh "./gradlew test"
            }
        }

        stage('Deploy') {
            steps {
                sh "./gradlew dockerComposeUp"
            }
        }
    }

    post {
        success {
            echo 'Success!'
        }

        failure {
            echo 'Failure'
        }
    }
}