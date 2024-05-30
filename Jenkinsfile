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
                powershell "gradle compile"
            }
        }

        stage('Test') {
            steps {
                powershell "gradle test"
            }
        }

        stage('Deploy') {
            steps {
                powershell "gradle dockerComposeUp"
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