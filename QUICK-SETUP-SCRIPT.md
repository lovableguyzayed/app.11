# 🚀 Quick Setup Script

## **Windows PowerShell Script**

Create a file called `setup-android-project.ps1`:

```powershell
# Create project structure
$projectRoot = "android-native"
New-Item -ItemType Directory -Force -Path $projectRoot
Set-Location $projectRoot

# Create main directories
$dirs = @(
    "app/src/main/java/com/retro/calculator",
    "app/src/main/res/values",
    "app/src/main/res/font",
    "app/src/main/res/mipmap-hdpi",
    "app/src/main/res/mipmap-mdpi", 
    "app/src/main/res/mipmap-xhdpi",
    "app/src/main/res/mipmap-xxhdpi",
    "app/src/main/res/mipmap-xxxhdpi",
    "app/src/main/res/xml",
    "gradle/wrapper"
)

foreach ($dir in $dirs) {
    New-Item -ItemType Directory -Force -Path $dir
}

Write-Host "✅ Project structure created!"
Write-Host "📁 Location: $(Get-Location)"
Write-Host "🔧 Next: Copy the file contents from the setup guide"
Write-Host "🚀 Then open in Android Studio"
```

## **Linux/Mac Bash Script**

Create a file called `setup-android-project.sh`:

```bash
#!/bin/bash

# Create project structure
PROJECT_ROOT="android-native"
mkdir -p "$PROJECT_ROOT"
cd "$PROJECT_ROOT"

# Create main directories
mkdir -p app/src/main/java/com/retro/calculator
mkdir -p app/src/main/res/{values,font,xml}
mkdir -p app/src/main/res/mipmap-{hdpi,mdpi,xhdpi,xxhdpi,xxxhdpi}
mkdir -p gradle/wrapper

echo "✅ Project structure created!"
echo "📁 Location: $(pwd)"
echo "🔧 Next: Copy the file contents from the setup guide"
echo "🚀 Then open in Android Studio"
```

## **Usage**

### **Windows:**
```powershell
# Run PowerShell as Administrator
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
.\setup-android-project.ps1
```

### **Linux/Mac:**
```bash
chmod +x setup-android-project.sh
./setup-android-project.sh
```

## **After Running Script**

1. **Copy file contents** from the setup guide into each file
2. **Download fonts** from Google Fonts
3. **Open in Android Studio**
4. **Build APK**

This creates the complete project structure in seconds! 🎉