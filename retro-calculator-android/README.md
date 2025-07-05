# Native Android Retro Calculator

This is a complete native Android application that replicates the exact functionality and design of the web-based retro calculator. Built with modern Android development practices using Kotlin and XML layouts.

## Features

### 🤖 Animated Robot Character
- Fully animated robot with floating, scaling, and rotation effects
- Different animation states (idle, excited, confirming, wave)
- Interactive robot with sound and haptic feedback
- Scanning eyes with color transitions

### 🎨 Retro Design System
- Custom color scheme matching the web app
- Retro typography with Orbitron, Russo One, and Share Tech Mono fonts
- Gradient backgrounds and glowing effects
- Animated background patterns

### 🧮 Calculator Functionality
- Step-by-step quantity and rate input
- Multiple unit support (kg, gram, quintal, ton, liter, ml, gallon, piece, dozen, box)
- Precise calculations with proper decimal formatting
- Real-time robot chat responses

### 📱 Native Android Features
- Haptic feedback on all interactions
- Material 3 design components
- Immersive fullscreen experience
- Native sound effects
- Optimized performance with native animations

## Technical Specifications

### Build Configuration
- **Android Gradle Plugin**: 8.1.3
- **Gradle Version**: 8.1.3
- **Kotlin Version**: 1.9.10
- **Compile SDK**: 36
- **Target SDK**: 36
- **Minimum SDK**: 36
- **JDK**: 17

### Architecture
- **MVVM Pattern**: ViewModel with LiveData
- **Fragment-based Navigation**: Landing → Calculator
- **Data Binding**: ViewBinding enabled
- **Sound Management**: Custom SoundManager with SoundPool
- **Animation System**: ObjectAnimator and AnimatorSet

### Dependencies
- AndroidX Core KTX
- Material Design Components
- ConstraintLayout
- Lifecycle Components
- Navigation Components
- Lottie for advanced animations
- Media library for sound effects

## Project Structure

```
app/
├── src/main/
│   ├── java/com/retro/calculator/
│   │   ├── MainActivity.kt                    # Main activity
│   │   ├── viewmodel/
│   │   │   └── CalculatorViewModel.kt         # Business logic
│   │   ├── model/
│   │   │   ├── CalculationStep.kt             # Step enumeration
│   │   │   └── Unit.kt                        # Unit enumeration
│   │   ├── ui/fragments/
│   │   │   ├── LandingFragment.kt             # Landing screen
│   │   │   └── CalculatorFragment.kt          # Calculator screen
│   │   └── utils/
│   │       └── SoundManager.kt                # Sound and haptic management
│   └── res/
│       ├── layout/                            # XML layouts
│       ├── drawable/                          # Vector drawables and shapes
│       ├── values/                            # Colors, strings, themes
│       ├── font/                              # Custom fonts
│       └── mipmap-*/                          # App icons
```

## Setup Instructions

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or later
- JDK 17 or higher
- Android SDK 36

### Installation Steps

1. **Download Fonts**
   - Download the following fonts from Google Fonts:
     - Orbitron (Regular, Bold, Black)
     - Russo One (Regular)
     - Share Tech Mono (Regular)
   - Place them in `app/src/main/res/font/` directory

2. **Open in Android Studio**
   ```bash
   # Open the retro-calculator-android folder in Android Studio
   File -> Open -> Select retro-calculator-android folder
   ```

3. **Sync Project**
   - Wait for Gradle sync to complete
   - Resolve any dependency issues

4. **Build and Run**
   ```bash
   # Build the project
   Build -> Make Project
   
   # Run on device/emulator
   Run -> Run 'app'
   ```

## Key Components

### 🎭 MainActivity.kt
- Immersive fullscreen setup
- Fragment container management
- System UI configuration

### 🧮 CalculatorViewModel.kt
- State management for calculation flow
- Input validation and formatting
- Robot animation state control
- Sound effect triggers

### 🎨 UI Fragments
- **LandingFragment**: Animated robot introduction
- **CalculatorFragment**: Multi-step calculation interface

### 🔊 SoundManager.kt
- Retro sound effects using SoundPool
- Haptic feedback integration
- Context-aware audio management

## Build Variants

### Debug Build
- Development optimized
- Debugging enabled
- Faster build times

### Release Build
- Production optimized
- ProGuard enabled
- Signed APK ready for distribution

## App Features

✅ **Offline Functionality**
- Complete calculator works without internet
- All resources bundled in APK
- Local calculations and storage

✅ **Native Android Features**
- Immersive fullscreen experience
- Haptic feedback
- Native sound effects
- Material 3 design
- Optimized performance

✅ **Retro Design**
- Full retro typography and effects
- Robot animations and interactions
- Gradient backgrounds
- Custom drawable resources

## Performance Optimizations

- Efficient animation handling
- Memory-conscious state management
- Optimized drawable resources
- Proper lifecycle management

## Testing

The app includes:
- Unit tests for ViewModel logic
- UI tests for Fragment interactions
- Integration tests for navigation flow

## Distribution

### Debug APK
Generated in: `app/build/outputs/apk/debug/`

### Release APK
Generated in: `app/build/outputs/apk/release/`
Requires signing configuration for Play Store distribution.

## Customization

### Colors
Modify `res/values/colors.xml` to adjust the color scheme

### Animations
Adjust animation parameters in Fragment files

### Robot Behavior
Customize robot responses in `CalculatorViewModel.kt`

This native Android app provides the complete retro calculator experience with enhanced performance, native features, and platform-specific optimizations.