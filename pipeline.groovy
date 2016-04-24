
node {

    stage 'CHECKOUT'
    checkout scm

    stage 'TEST'
    runUnitTests()

    stage 'BUILD'
    buildDebug()
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