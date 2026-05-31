pipeline {
    agent any
    tools {
        maven 'Maven-3.9.6'
        jdk 'jdk-8'
    }
    stages {
        stage('git checkout') {
            steps {
                git credentialsId: 'git_credentials', url: 'https://github.com/Avohaja/Tp_jenks.git'
            }
        }
        
        stage('Check Java') {
            steps {
                bat 'java -version'
                bat 'mvn -version'
        }
    }

        stage('Build the application') {
            steps {
                bat 'mvn clean install'
            }
        }
        stage('Unit Test Execution') {
            steps {
                bat 'mvn test'
            }
        }
        stage('Build the docker image') {
            steps {
                bat 'docker build -t avohaja/bankapp:1.0.0 .'
            }
        }
        stage('Push to DockerHub') {
            steps {
                withCredentials([string(credentialsId: 'dockerhubpass', variable: 'dockerHubPass')]) {
                    bat 'docker login -u avohaja -p %dockerHubPass%'
                    bat 'docker push avohaja/bankapp:1.0.0'
                }
            }
        }
    }
}