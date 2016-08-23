stage 'Checkout'
node('master') {
    deleteDir()
    checkout scm
    sh './gradlew clean'
    stash name: 'sources'

    APK_NAME = 'wedding_app_'+ "${env.BUILD_NUMBER}" + '.apk'
}

stage 'Test'
node('master') {
    cleanUnstash('sources')
    runUnitTests()
}

stage 'Build'
parallel (
        "devBuild" : {
            node('master') {
                cleanUnstash('sources')
                buildDebug()
            }
        },
        "releaseBuild": {
            node('master') {
                def externalMethod = load("Wedding/steps.groovy")
                externalMethod.step()
            }
        }
)

stage 'Release'
node('master') {
    uploadToHockey()
}

private void cleanUnstash(String source) {
    deleteDir()
    unstash source
}

private void runUnitTests() {
    sh './gradlew test'
}

private void buildDebug() {
    sh './gradlew assembleDebug'
}

private void buildRelease() {
    sh 'cp /gradle.properties .'
    sh './gradlew assembleRelease'
}

private void uploadToHockey() {

    unstash 'apk'

    sh 'curl \\' +
            '  -F "status=2" \\' +
            '  -F "notify=1" \\' +
            '  -F "ipa=@"' + APK_NAME + ' \\' +
            '  -H "X-HockeyAppToken: "' + HOCKEY_API_KEY + ' \\' +
            '  https://rink.hockeyapp.net/api/2/apps/6a61f0dc39884d0c969068bac05a1af9/app_versions/upload'
}