#app/proguard-rules.pro

# Keep model classes used by Firestore
-keep class com.example.domain.model.CartItem { *; }
-keepclassmembers class com.example.domain.model.CartItem {
    public <init>();
}

# Existing Firebase rules
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.firebase.**
-dontwarn com.google.android.gms.**