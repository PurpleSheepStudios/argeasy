pipeline {
    agent {
        docker { image 'maven:3-openjdk-8' }
    }
    stages {
    stage('Build') {
        steps {
            sh 'mvn clean compile test-compile'
        }
    }
    stage('Test') {
        steps {
            sh 'mvn test'
        }
    }
}