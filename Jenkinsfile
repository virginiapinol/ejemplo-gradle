def code
pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Agregando permisos'){
            steps {
                sh '''#!/bin/bash
                echo shell commands here
                chmod +x mvnw
                '''
            }
        }
        stage('Carga script') {
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
        stage('Test') {
            steps {
                script {
                    code.Test()
                }
            }
        }
    }
}
