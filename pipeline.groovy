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
node('master') {
    unstash 'sources'
    buildDebug()
//    sh 'cp app/build/outputs/apk/app-release.apk wedding_app.apk'
//    step([$class: 'ArtifactArchiver', artifacts: '*.apk'])
}
node('test') {
    unstash 'sources'
    buildRelease()
    sh 'cp app/build/outputs/apk/app-release.apk wedding_app.apk'
    step([$class: 'ArtifactArchiver', artifacts: '*.apk'])
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