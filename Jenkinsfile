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
                bash "gradle compile"
            }
        }

        stage('Test') {
            steps {
                bash "gradle test"
            }
        }

        stage('Deploy') {
            steps {
                bash "gradle dockerComposeUp"
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