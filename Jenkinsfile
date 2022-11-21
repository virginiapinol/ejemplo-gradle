def code
pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
            steps 
            {
                sh '''#!/bin/bash
                echo shell commands here
                chmod +x mvnw
                '''
            }
        }
        stage('Build') {
            steps {
                script {
                    code = load 'maven.groovy'
                }
            }
        }
        stage('Compilación') {
            steps {
                script {
                    code.Compilacion()
                }
            }
        }
    }
}
