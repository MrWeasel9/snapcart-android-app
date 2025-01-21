# domain/proguard-rules.pro

-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }
-keep class com.google.protobuf.** { *; }
-keep class androidx.lifecycle.** { *; }
-dontwarn com.google.firebase.**
-dontwarn com.google.android.gms.**