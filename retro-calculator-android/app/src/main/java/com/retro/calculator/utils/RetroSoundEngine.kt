package com.retro.calculator.utils

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import kotlinx.coroutines.*
import kotlin.math.*

class RetroSoundEngine(private val context: Context) {
    
    private var audioTrack: AudioTrack? = null
    private var vibrator: Vibrator? = null
    private val sampleRate = 44100
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    
    init {
        initializeVibrator()
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
    
    fun playButtonClick() {
        scope.launch {
            generateAndPlayTone(
                frequency = 800f,
                duration = 100,
                waveform = WaveForm.SQUARE,
                envelope = Envelope.QUICK_DECAY
            )
        }
        vibrate(50)
    }
    
    fun playRobotBeep() {
        scope.launch {
            generateAndPlayTone(
                frequency = 440f,
                duration = 200,
                waveform = WaveForm.SAWTOOTH,
                envelope = Envelope.SUSTAIN
            )
        }
        vibrate(100)
    }
    
    fun playCalculateSuccess() {
        scope.launch {
            // Success chord progression
            val frequencies = arrayOf(523.25f, 659.25f, 783.99f) // C5, E5, G5
            frequencies.forEach { freq ->
                generateAndPlayTone(
                    frequency = freq,
                    duration = 150,
                    waveform = WaveForm.SINE,
                    envelope = Envelope.FADE_OUT
                )
                delay(50)
            }
        }
        vibrate(200)
    }
    
    fun playReset() {
        scope.launch {
            generateAndPlayTone(
                frequency = 220f,
                duration = 300,
                waveform = WaveForm.TRIANGLE,
                envelope = Envelope.FADE_OUT
            )
        }
        vibrate(150)
    }
    
    fun playCalculating() {
        scope.launch {
            // Calculating sound - ascending tones
            for (i in 0..5) {
                val freq = 200f + (i * 50f)
                generateAndPlayTone(
                    frequency = freq,
                    duration = 100,
                    waveform = WaveForm.SQUARE,
                    envelope = Envelope.QUICK_DECAY
                )
                delay(100)
            }
        }
    }
    
    fun playConfirmation() {
        scope.launch {
            // Confirmation sound - robot-like beeps
            val pattern = arrayOf(880f, 1108f, 880f, 1108f)
            pattern.forEach { freq ->
                generateAndPlayTone(
                    frequency = freq,
                    duration = 120,
                    waveform = WaveForm.PULSE,
                    envelope = Envelope.SUSTAIN
                )
                delay(80)
            }
        }
        vibrate(300)
    }
    
    fun playError() {
        scope.launch {
            generateAndPlayTone(
                frequency = 150f,
                duration = 500,
                waveform = WaveForm.NOISE,
                envelope = Envelope.FADE_OUT
            )
        }
        vibrate(400)
    }
    
    fun playRobotMovement() {
        scope.launch {
            generateAndPlayTone(
                frequency = 300f,
                duration = 200,
                waveform = WaveForm.SAWTOOTH,
                envelope = Envelope.QUICK_DECAY
            )
        }
    }
    
    fun playNumberInput() {
        scope.launch {
            generateAndPlayTone(
                frequency = 600f,
                duration = 80,
                waveform = WaveForm.SINE,
                envelope = Envelope.QUICK_DECAY
            )
        }
        vibrate(30)
    }
    
    fun playUnitSelection() {
        scope.launch {
            generateAndPlayTone(
                frequency = 750f,
                duration = 120,
                waveform = WaveForm.TRIANGLE,
                envelope = Envelope.SUSTAIN
            )
        }
        vibrate(40)
    }
    
    fun playResultDisplay() {
        scope.launch {
            // Result fanfare
            val melody = arrayOf(523.25f, 659.25f, 783.99f, 1046.5f) // C5, E5, G5, C6
            melody.forEach { freq ->
                generateAndPlayTone(
                    frequency = freq,
                    duration = 200,
                    waveform = WaveForm.SINE,
                    envelope = Envelope.FADE_OUT
                )
                delay(100)
            }
        }
        vibrate(250)
    }
    
    fun playRobotSpeech() {
        scope.launch {
            // Robot speech simulation
            for (i in 0..3) {
                val freq = 200f + (Math.random() * 200f).toFloat()
                generateAndPlayTone(
                    frequency = freq,
                    duration = 80,
                    waveform = WaveForm.PULSE,
                    envelope = Envelope.QUICK_DECAY
                )
                delay(60)
            }
        }
    }
    
    private suspend fun generateAndPlayTone(
        frequency: Float,
        duration: Int,
        waveform: WaveForm,
        envelope: Envelope
    ) = withContext(Dispatchers.IO) {
        val samples = (sampleRate * duration / 1000.0).toInt()
        val buffer = ShortArray(samples)
        
        for (i in 0 until samples) {
            val time = i.toDouble() / sampleRate
            val phase = 2.0 * PI * frequency * time
            
            // Generate waveform
            val sample = when (waveform) {
                WaveForm.SINE -> sin(phase)
                WaveForm.SQUARE -> if (sin(phase) >= 0) 1.0 else -1.0
                WaveForm.TRIANGLE -> (2.0 / PI) * asin(sin(phase))
                WaveForm.SAWTOOTH -> (2.0 / PI) * (phase % (2.0 * PI) - PI)
                WaveForm.PULSE -> if ((phase % (2.0 * PI)) < PI * 0.3) 1.0 else -1.0
                WaveForm.NOISE -> (Math.random() * 2.0 - 1.0)
            }
            
            // Apply envelope
            val envelopeValue = when (envelope) {
                Envelope.SUSTAIN -> 1.0
                Envelope.FADE_OUT -> 1.0 - (i.toDouble() / samples)
                Envelope.QUICK_DECAY -> exp(-5.0 * i / samples)
                Envelope.ATTACK_DECAY -> {
                    val attackTime = samples * 0.1
                    if (i < attackTime) {
                        i / attackTime
                    } else {
                        exp(-3.0 * (i - attackTime) / (samples - attackTime))
                    }
                }
            }
            
            buffer[i] = (sample * envelopeValue * 0.3 * Short.MAX_VALUE).toInt().toShort()
        }
        
        playBuffer(buffer)
    }
    
    private fun playBuffer(buffer: ShortArray) {
        try {
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
            
            val audioFormat = AudioFormat.Builder()
                .setSampleRate(sampleRate)
                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                .build()
            
            audioTrack = AudioTrack(
                audioAttributes,
                audioFormat,
                buffer.size * 2,
                AudioTrack.MODE_STATIC,
                AudioManager.AUDIO_SESSION_ID_GENERATE
            )
            
            audioTrack?.write(buffer, 0, buffer.size)
            audioTrack?.play()
            
            // Clean up after playback
            scope.launch {
                delay(buffer.size * 1000L / sampleRate + 100)
                audioTrack?.stop()
                audioTrack?.release()
                audioTrack = null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
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
        scope.cancel()
        audioTrack?.stop()
        audioTrack?.release()
        audioTrack = null
    }
    
    enum class WaveForm {
        SINE, SQUARE, TRIANGLE, SAWTOOTH, PULSE, NOISE
    }
    
    enum class Envelope {
        SUSTAIN, FADE_OUT, QUICK_DECAY, ATTACK_DECAY
    }
}