def code
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                script {
                    chmod '+x mvnw'
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
