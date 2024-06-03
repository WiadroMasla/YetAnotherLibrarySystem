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
                powershell "gradlew compile"
            }
        }

        stage('Test') {
            steps {
                powershell "gradlew test"
            }
        }

        stage('Deploy') {
            steps {
                powershell "gradlew dockerComposeUp"
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