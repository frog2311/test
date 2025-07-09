def version = "v0.${BUILD_NUMBER}"

pipeline {
    agent any

    stages
    {
        stage('Check Source')
        {
            steps
            {
                echo 'First Stage'
                git url: 'https://github.com/frog2311/test.git', branch: 'master', credentialsId: 'github-private-token'
            }
        }

        stage('Build Project') 
        {
            steps {
                echo '🚧 Building Java backend & React frontend...'
                bat '''
                    REM 🧱 Build backend Spring Boot
                    mvn clean install

                    REM 🌐 Build frontend React
                    cd src\\main\\resources\\templates
                    npm install
                    npm run build

                    echo "✅ Build complete!"
                '''
            }
        }



        

        /* stage('Test')
        {
            steps
            {
                echo 'Third Stage'
                sh 'echo "Testing version ${version}"'
            }
        } */
    }
    /* post {
        always {
            echo 'This will always run'
        }
        success {
            echo 'This will run only if the pipeline succeeds'
        }
        failure {
            echo 'This will run only if the pipeline fails'
        }
        unstable {
            echo 'This will run if the pipeline is unstable'
        }
    } */

}

// SonarQube docs
