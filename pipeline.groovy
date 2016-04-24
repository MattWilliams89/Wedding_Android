stage 'Checkout'
node {
    checkout scm
    sh './gradlew clean'
    stash name: 'sources'
}

stage 'Test'
node {
    unstash 'sources'
    runUnitTests()
}

stage 'Build'
node{
    unstash 'sources'
    buildRelease()
}


private void runUnitTests() {
    sh './gradlew testDebug'
}

private void buildDebug() {
    sh './gradlew assembleDebug'
}

private void buildRelease() {
    sh './gradlew assembleRelease'
}