stage 'TEST'
node {
    checkout scm
    runUnitTests()
}


stage 'Build'
node {
    checkout scm
    buildDebug()
}
node{
    checkout scm
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