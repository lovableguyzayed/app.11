# 📥 Download Links for Required Files

## **🔤 Fonts (Required)**

Download these fonts from Google Fonts and place in `app/src/main/res/font/`:

1. **Orbitron**: https://fonts.google.com/specimen/Orbitron
   - Download: Regular (400), Bold (700), Black (900)
   - Rename to: `orbitron_regular.ttf`, `orbitron_bold.ttf`, `orbitron_black.ttf`

2. **Russo One**: https://fonts.google.com/specimen/Russo+One
   - Download: Regular (400)
   - Rename to: `russo_one_regular.ttf`

3. **Share Tech Mono**: https://fonts.google.com/specimen/Share+Tech+Mono
   - Download: Regular (400)
   - Rename to: `share_tech_mono_regular.ttf`

## **🎨 App Icons (Optional)**

Generate app icons using:
- **Android Studio**: Right-click `res` → New → Image Asset
- **Online Tool**: https://romannurik.github.io/AndroidAssetStudio/icons-launcher.html

## **⚙️ Gradle Wrapper (Auto-downloaded)**

The Gradle wrapper will be automatically downloaded when you:
1. Open the project in Android Studio
2. Or run: `./gradlew wrapper --gradle-version=8.11`

## **📱 Android Studio**

Download the latest version:
- **Official Site**: https://developer.android.com/studio
- **Requirements**: JDK 17+ (included with Android Studio)

## **✅ Verification Checklist**

Before opening in Android Studio, ensure you have:
- [ ] All 5 font files in `res/font/` folder
- [ ] App icons in all `mipmap-*` folders (or use default)
- [ ] All `.gradle` files with correct content
- [ ] `AndroidManifest.xml` with correct content
- [ ] `MainActivity.kt` with correct content

## **🚀 Ready to Build**

Once all files are in place:
1. Open Android Studio
2. File → Open → Select `android-native` folder
3. Wait for Gradle sync
4. Build → Build APK

Your APK will be ready in 5-10 minutes! 🎉