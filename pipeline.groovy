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
node('test') {
    unstash 'sources'
    buildRelease()
    sh 'cp app/build/outputs/apk/app-release.apk wedding_app.apk'
    step([$class: 'ArtifactArchiver', artifacts: '*.apk'])
}


private void runUnitTests() {
    sh './gradlew testDebug'
}

private void buildRelease() {
    sh 'cp /gradle.properties .'
    sh './gradlew assembleRelease'
}