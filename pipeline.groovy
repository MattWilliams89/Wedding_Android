stage 'TEST'
node {
    runUnitTests()
}

stage 'BUILD'
node{
    buildDebug()
}
node{
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