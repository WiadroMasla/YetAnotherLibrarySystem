pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
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
                container('general-container-name') {
                    withEnv(['PATH+EXTRA=C:\\Program Files\\Docker\\Docker\\resources\\bin']) {
                         sh "./gradlew dockerComposeUp"
                    }

                }
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