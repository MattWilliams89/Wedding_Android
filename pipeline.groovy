stage 'Checkout'
node('master') {
    checkout scm
    sh './gradlew clean'
    stash name: 'sources'
}

stage 'Test'
node('master') {
    unstash 'sources'
    runUnitTests()
}


stage 'Build'
def branches = [:]
branches["devBuild"] = {
    node('master') {
        unstash 'sources'
        buildDebug()
    }
}
branches["releaseBuild"] = {
    node('test') {
        unstash 'sources'
        buildRelease()
        sh 'cp app/build/outputs/apk/app-release.apk wedding_app_' + "${env.BUILD_NUMBER}" + '.apk'
        step([$class: 'ArtifactArchiver', artifacts: '*.apk'])
    }
}

parallel branches

stage 'Release'
node('master') {
    unstash 'sources'
    uploadToHockey()
}

private void runUnitTests() {
    sh './gradlew testDebug'
}

private void buildDebug() {
    sh './gradlew assembleDebug'
}

private void buildRelease() {
    sh 'cp /gradle.properties .'
    sh './gradlew assembleRelease'
}

private void uploadToHockey() {
    sh 'curl \\' +
            '  -F "status=2" \\' +
            '  -F "notify=1" \\' +
            '  -F "ipa=@wedding_app"'+"${env.BUILD_NUMBER}"+'".apk" \\' +
            '  -H "X-HockeyAppToken: "' + HOCKEY_API_KEY + ' \\' +
            '  https://rink.hockeyapp.net/api/2/apps/6a61f0dc39884d0c969068bac05a1af9/app_versions/upload'
}