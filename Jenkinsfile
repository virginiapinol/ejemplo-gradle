def code

def COLOR_MAP =[
    'SUCCESS': 'good',
    'FAILURE': 'danger'
]

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
                chmod +x gradlew
                '''
            }
        }
        stage('get_commit_details') {
            steps {
                script {
                    env.GIT_COMMIT_MSG = sh (script: 'git log -1 --pretty=%B ${GIT_COMMIT}', returnStdout: true).trim()
                    env.GIT_AUTHOR = sh (script: 'git log -1 --pretty=%cn ${GIT_COMMIT}', returnStdout: true).trim()
                }
            }
        }
        stage('Carga script') {
            steps {
                script {
                    code = load "./${params.TEST_CHOICE}.groovy"
                }
            }
        }
        /*stage('Compilación') {
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
        }*/
        /*stage('Análisis Sonarqube') {
            environment {
                scannerHome = tool 'SonarScanner'
            }
            steps {
                 withSonarQubeEnv('sonar') {
                    sh './mvnw clean verify sonar:sonar -Dsonar.projectKey=ejemplo_maven -Dsonar.host.url=https://76616267025f.ngrok.io -Dsonar.login=squ_6c8cafa0da0ac1956dc6a6f29d83e7d01856b654 -Dsonar.target=sonar.java.binaries'
                }
            }
            
        }
        stage("Comprobación Quality gate") {
            steps {
                waitForQualityGate abortPipeline: true
            }
        } */
        stage ('Publish Nexus'){
			when {
				branch "main"
			}
            steps{
                script {
                    pom = readMavenPom file: "pom.xml";
                    //filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                    //echo "*** aqui : ${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                    //def file = ${pom.artifactId}-${pom.version}.${pom.packaging}
                    echo "*** aqui : ;"//${pom.artifactId} ${pom.version} ${pom.packaging}"
                    artifactPath = "./build/${pom.artifactId}-${pom.version}.${pom.packaging}";//filesByGlob[0].path;
                    artifactExists = fileExists artifactPath;
                    if(artifactExists) {
                        echo "Artifact exists: ${artifactPath}";
                         nexusPublisher nexusInstanceId: 'Nexus-1', nexusRepositoryId: 'devops-usach-vpino', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: "${artifactPath}"]], mavenCoordinate: [artifactId: 'DevOpsUsach2020', groupId: 'com.devopsusach2020', packaging: 'jar', version: pom.version]]]
                    } else {
                        echo "Artifact does not exist: ${artifactPath}";
                    }
                }
            }
        }
    }
post{
        success{
            setBuildStatus("Build succeeded", "SUCCESS");

            slackSend channel:'#devops-equipo5',
                color:COLOR_MAP[currentBuild.currentResult],
                message: "*${currentBuild.currentResult}:* ${env.GIT_AUTHOR} ${env.JOB_NAME} build ${env.BUILD_NUMBER}  Ejecución exitosa"

        }

        failure {
            setBuildStatus("Build failed", "FAILURE");

            slackSend channel:'#devops-equipo5',
                    color:COLOR_MAP[currentBuild.currentResult],
                    message: "*${currentBuild.currentResult}:* ${env.GIT_AUTHOR} ${env.JOB_NAME} Ejecución fallida en stage: build ${env.BUILD_NUMBER}"

        } 
    }
}

void setBuildStatus(String message, String state) {
    step([
        $class: "GitHubCommitStatusSetter",
        reposSource: [$class: "ManuallyEnteredRepositorySource", url: "https://github.com/virginiapinol/ejemplo-gradle"],
        contextSource: [$class: "ManuallyEnteredCommitContextSource", context: "ci/jenkins/build-status"],
        errorHandlers: [[$class: "ChangingBuildStatusErrorHandler", result: "UNSTABLE"]],
        statusResultSource: [$class: "ConditionalStatusResultSource", results: [[$class: "AnyBuildResult", message: message, state: state]]]
    ]);
}