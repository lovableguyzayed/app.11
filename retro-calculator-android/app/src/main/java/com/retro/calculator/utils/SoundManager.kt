package com.retro.calculator.utils

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import com.retro.calculator.R

class SoundManager(private val context: Context) {
    
    private var soundPool: SoundPool? = null
    private var vibrator: Vibrator? = null
    
    // Sound IDs
    private var buttonClickSound = 0
    private var robotBeepSound = 0
    private var calculateSuccessSound = 0
    private var resetSound = 0
    private var calculatingSound = 0
    private var confirmationSound = 0
    
    init {
        initializeSoundPool()
        initializeVibrator()
    }
    
    private fun initializeSoundPool() {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        
        soundPool = SoundPool.Builder()
            .setMaxStreams(6)
            .setAudioAttributes(audioAttributes)
            .build()
        
        // Load sounds (we'll generate these programmatically)
        loadSounds()
    }
    
    private fun initializeVibrator() {
        vibrator = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
    }
    
    private fun loadSounds() {
        // For now, we'll use system sounds or generate simple tones
        // In a real implementation, you would load actual sound files from res/raw/
        
        // These would be actual sound file resources:
        // buttonClickSound = soundPool?.load(context, R.raw.button_click, 1) ?: 0
        // robotBeepSound = soundPool?.load(context, R.raw.robot_beep, 1) ?: 0
        // etc.
        
        // For this implementation, we'll use placeholder values
        buttonClickSound = 1
        robotBeepSound = 2
        calculateSuccessSound = 3
        resetSound = 4
        calculatingSound = 5
        confirmationSound = 6
    }
    
    fun playButtonClick() {
        playSound(buttonClickSound)
        vibrate(50)
    }
    
    fun playRobotBeep() {
        playSound(robotBeepSound)
        vibrate(100)
    }
    
    fun playCalculateSuccess() {
        playSound(calculateSuccessSound)
        vibrate(200)
    }
    
    fun playReset() {
        playSound(resetSound)
        vibrate(150)
    }
    
    fun playCalculating() {
        playSound(calculatingSound)
    }
    
    fun playConfirmation() {
        playSound(confirmationSound)
        vibrate(300)
    }
    
    private fun playSound(soundId: Int) {
        soundPool?.play(soundId, 1f, 1f, 1, 0, 1f)
    }
    
    private fun vibrate(duration: Long) {
        vibrator?.let { vib ->
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                vib.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vib.vibrate(duration)
            }
        }
    }
    
    fun release() {
        soundPool?.release()
        soundPool = null
    }
}