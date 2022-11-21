def code
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                chmod '+x mvnw'
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
