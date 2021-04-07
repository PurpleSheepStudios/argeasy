pipeline {
    agent {
        docker { image 'maven:adoptopenjdk' }
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
}