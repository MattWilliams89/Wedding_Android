-dontwarn com.squareup.okhttp.**
-dontwarn okio.**

-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-dontwarn android.app.Notification

-keep class org.maw.wedding.model.** { *; }