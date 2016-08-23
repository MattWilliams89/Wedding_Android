public step() {
    node('test') {
        cleanUnstash('sources')
        buildRelease()
        sh 'cp app/build/outputs/apk/app-release.apk ' + APK_NAME
        stash includes: APK_NAME, name: 'apk'
    }
}