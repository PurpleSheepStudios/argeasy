pipeline {
    agent {
        docker { image 'maven:3-openjdk-8' }
    }
    stages {
    stage('Build') {
        steps {
            sh 'mvn -B -DskipTests clean verify'
        }
    }
    stage('Test') {
        steps {
            sh 'mvn test'
        }
    }
}