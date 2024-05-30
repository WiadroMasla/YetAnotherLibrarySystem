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
                gradle compile
            }
        }

        stage('Test') {
            steps {
                gradle test
            }
        }

        stage('Deploy') {
            steps {
                gradle dockerComposeUp
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