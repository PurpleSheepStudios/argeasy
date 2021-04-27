pipeline {
    agent {
        docker {
            image 'maven:3.6.0-jdk-8-alpine'
            args '-v $HOME/.m2:/root/.m2'
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -DskipTests clean verify'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
    }
}