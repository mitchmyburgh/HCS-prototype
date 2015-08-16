#Health Case Study Prototype

##How to run

Make sure that JAVA_HOME and ANDROID_HOME is set in your Path

Then run the following commands from the root of the Project folder
```
chmod +x gradlew
```
(Makes gradlew executable)
```
./gradlew assembleDebug
```
builds the android app apk, which can be found in:
```
./app/build/outputs/apk/app-debug.apk
```
This APK can thenn be installed on your device with the following command:
```
adb -d install -r ./app/build/outputs/apk/app-debug.apk
```

#Authors

Mitch Myburgh
Mbongeni Ncube
Aziza Makiwane
