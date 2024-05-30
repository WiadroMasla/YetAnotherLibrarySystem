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
                sh "gradle compile"
            }
        }

        stage('Test') {
            steps {
                sh "gradle test"
            }
        }

        stage('Deploy') {
            steps {
                sh "gradle dockerComposeUp"
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