def code
pipeline {
    agent any

    parameters {
        /*booleanParam(name: "TEST_BOOLEAN", defaultValue: true, description: "Sample boolean parameter")
        string(name: "TEST_STRING", defaultValue: "ssbostan", trim: true, description: "Sample string parameter")
        text(name: "TEST_TEXT", defaultValue: "Jenkins Pipeline Tutorial", description: "Sample multi-line text parameter")
        password(name: "TEST_PASSWORD", defaultValue: "SECRET", description: "Sample password parameter")
        */
        choice(name: "TEST_CHOICE", choices: ["maven", "gradle",], description: "Sample multi-choice parameter")
    }
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
            when {
                expression { params.TEST_CHOICE == 'maven' }
            }
            steps {
                script {
                    code = load 'maven.groovy'
                }
            }
            when {
                expression { params.TEST_CHOICE == 'gradle' }
            }
            steps {
                script {
                    code = load 'gradle.groovy'
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
