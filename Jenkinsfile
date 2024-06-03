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
                powershell ".\\gradlew.bat compile"
            }
        }

        stage('Test') {
            steps {
                powershell ".\\gradlew.bat test"
            }
        }

        stage('Deploy') {
            steps {
                powershell ".\\gradlew.bat dockerComposeUp"
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