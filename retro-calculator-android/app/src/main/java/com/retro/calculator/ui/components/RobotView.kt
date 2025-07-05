package com.retro.calculator.ui.components

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.retro.calculator.R
import kotlin.math.*

class RobotView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val gradientPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    
    // Colors
    private val primaryColor = ContextCompat.getColor(context, R.color.primary)
    private val accentColor = ContextCompat.getColor(context, R.color.accent)
    private val darkBlueColor = ContextCompat.getColor(context, R.color.dark_blue)
    private val bgCardColor = ContextCompat.getColor(context, R.color.bg_card)
    private val successColor = ContextCompat.getColor(context, R.color.success)
    private val errorColor = ContextCompat.getColor(context, R.color.error)
    
    // Animation properties
    private var floatOffset = 0f
    private var rotationAngle = 0f
    private var scaleValue = 1f
    private var eyeColorPhase = 0f
    private var antennaPulse = 1f
    
    // Animation state
    private var animationState = "idle"
    
    init {
        startIdleAnimation()
    }
    
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        
        val centerX = width / 2f
        val centerY = height / 2f
        
        canvas.save()
        canvas.translate(0f, floatOffset)
        canvas.scale(scaleValue, scaleValue, centerX, centerY)
        canvas.rotate(rotationAngle, centerX, centerY)
        
        drawRobot(canvas, centerX, centerY)
        
        canvas.restore()
    }
    
    private fun drawRobot(canvas: Canvas, centerX: Float, centerY: Float) {
        val robotScale = 0.8f
        
        // Draw antenna array
        drawAntennaArray(canvas, centerX, centerY - 120 * robotScale)
        
        // Draw robot head
        drawRobotHead(canvas, centerX, centerY - 60 * robotScale)
        
        // Draw robot body
        drawRobotBody(canvas, centerX, centerY + 20 * robotScale)
        
        // Draw robot base
        drawRobotBase(canvas, centerX, centerY + 100 * robotScale)
        
        // Draw robot legs
        drawRobotLegs(canvas, centerX, centerY + 120 * robotScale)
    }
    
    private fun drawAntennaArray(canvas: Canvas, centerX: Float, centerY: Float) {
        val antennaPositions = arrayOf(-30f, 0f, 30f)
        val antennaHeights = arrayOf(32f, 40f, 32f)
        val antennaColors = arrayOf(accentColor, successColor, errorColor)
        
        antennaPositions.forEachIndexed { index, x ->
            // Antenna rod
            paint.color = primaryColor
            canvas.drawRoundRect(
                centerX + x - 2f, centerY - antennaHeights[index],
                centerX + x + 2f, centerY,
                2f, 2f, paint
            )
            
            // Antenna tip with pulse animation
            paint.color = antennaColors[index]
            val pulseRadius = 6f * antennaPulse
            canvas.drawCircle(
                centerX + x, centerY - antennaHeights[index] - 6f,
                pulseRadius, paint
            )
        }
    }
    
    private fun drawRobotHead(canvas: Canvas, centerX: Float, centerY: Float) {
        // Head background with gradient
        val headRect = RectF(centerX - 60f, centerY - 40f, centerX + 60f, centerY + 40f)
        gradientPaint.shader = LinearGradient(
            centerX, centerY - 40f, centerX, centerY + 40f,
            intArrayOf(primaryColor, bgCardColor, darkBlueColor),
            null, Shader.TileMode.CLAMP
        )
        canvas.drawRoundRect(headRect, 24f, 24f, gradientPaint)
        
        // Head border
        paint.color = accentColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 6f
        canvas.drawRoundRect(headRect, 24f, 24f, paint)
        paint.style = Paint.Style.FILL
        
        // Eyes with scanning animation
        val eyeColor = interpolateEyeColor()
        paint.color = eyeColor
        canvas.drawCircle(centerX - 20f, centerY - 8f, 10f, paint)
        canvas.drawCircle(centerX + 20f, centerY - 8f, 10f, paint)
        
        // Eye borders
        paint.color = darkBlueColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 4f
        canvas.drawCircle(centerX - 20f, centerY - 8f, 10f, paint)
        canvas.drawCircle(centerX + 20f, centerY - 8f, 10f, paint)
        paint.style = Paint.Style.FILL
        
        // Mouth display
        paint.color = accentColor
        canvas.drawRoundRect(
            centerX - 16f, centerY + 16f,
            centerX + 16f, centerY + 24f,
            4f, 4f, paint
        )
        
        // Head detail
        paint.color = accentColor
        canvas.drawCircle(centerX, centerY - 24f, 4f, paint)
    }
    
    private fun drawRobotBody(canvas: Canvas, centerX: Float, centerY: Float) {
        // Body background with gradient
        val bodyRect = RectF(centerX - 70f, centerY - 60f, centerX + 70f, centerY + 60f)
        gradientPaint.shader = LinearGradient(
            centerX, centerY - 60f, centerX, centerY + 60f,
            intArrayOf(bgCardColor, primaryColor, darkBlueColor),
            null, Shader.TileMode.CLAMP
        )
        canvas.drawRoundRect(bodyRect, 16f, 16f, gradientPaint)
        
        // Body border
        paint.color = accentColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 6f
        canvas.drawRoundRect(bodyRect, 16f, 16f, paint)
        paint.style = Paint.Style.FILL
        
        // Chest panel
        paint.color = darkBlueColor
        canvas.drawRoundRect(
            centerX - 30f, centerY - 30f,
            centerX + 30f, centerY + 30f,
            12f, 12f, paint
        )
        
        // Calculator icon in chest
        paint.color = accentColor
        drawCalculatorIcon(canvas, centerX, centerY)
        
        // Control panels
        paint.color = accentColor
        canvas.drawRoundRect(
            centerX - 60f, centerY + 30f,
            centerX - 44f, centerY + 46f,
            4f, 4f, paint
        )
        
        paint.color = successColor
        canvas.drawRoundRect(
            centerX + 44f, centerY + 30f,
            centerX + 60f, centerY + 46f,
            4f, 4f, paint
        )
        
        // Arms
        drawRobotArms(canvas, centerX, centerY)
    }
    
    private fun drawRobotArms(canvas: Canvas, centerX: Float, centerY: Float) {
        // Left arm
        gradientPaint.shader = LinearGradient(
            centerX - 70f, centerY - 10f, centerX - 70f, centerY + 50f,
            intArrayOf(primaryColor, darkBlueColor),
            null, Shader.TileMode.CLAMP
        )
        canvas.drawRoundRect(
            centerX - 78f, centerY - 10f,
            centerX - 62f, centerY + 50f,
            8f, 8f, gradientPaint
        )
        
        // Right arm
        canvas.drawRoundRect(
            centerX + 62f, centerY - 10f,
            centerX + 78f, centerY + 50f,
            8f, 8f, gradientPaint
        )
    }
    
    private fun drawRobotBase(canvas: Canvas, centerX: Float, centerY: Float) {
        // Base with gradient
        gradientPaint.shader = LinearGradient(
            centerX, centerY - 12f, centerX, centerY + 12f,
            intArrayOf(primaryColor, darkBlueColor),
            null, Shader.TileMode.CLAMP
        )
        canvas.drawRoundRect(
            centerX - 50f, centerY - 12f,
            centerX + 50f, centerY + 12f,
            12f, 12f, gradientPaint
        )
        
        // Status indicators
        val statusColors = arrayOf(accentColor, successColor, errorColor)
        statusColors.forEachIndexed { index, color ->
            paint.color = color
            canvas.drawCircle(
                centerX - 16f + (index * 16f), centerY,
                3f, paint
            )
        }
    }
    
    private fun drawRobotLegs(canvas: Canvas, centerX: Float, centerY: Float) {
        // Left leg
        gradientPaint.shader = LinearGradient(
            centerX - 24f, centerY, centerX - 24f, centerY + 40f,
            intArrayOf(primaryColor, darkBlueColor),
            null, Shader.TileMode.CLAMP
        )
        canvas.drawRoundRect(
            centerX - 32f, centerY,
            centerX - 16f, centerY + 40f,
            8f, 8f, gradientPaint
        )
        
        // Right leg
        canvas.drawRoundRect(
            centerX + 16f, centerY,
            centerX + 32f, centerY + 40f,
            8f, 8f, gradientPaint
        )
        
        // Feet
        paint.color = darkBlueColor
        canvas.drawRoundRect(
            centerX - 36f, centerY + 40f,
            centerX - 12f, centerY + 52f,
            6f, 6f, paint
        )
        canvas.drawRoundRect(
            centerX + 12f, centerY + 40f,
            centerX + 36f, centerY + 52f,
            6f, 6f, paint
        )
    }
    
    private fun drawCalculatorIcon(canvas: Canvas, centerX: Float, centerY: Float) {
        paint.color = accentColor
        paint.strokeWidth = 2f
        
        // Calculator outline
        paint.style = Paint.Style.STROKE
        canvas.drawRoundRect(
            centerX - 12f, centerY - 16f,
            centerX + 12f, centerY + 16f,
            2f, 2f, paint
        )
        
        // Display
        paint.style = Paint.Style.FILL
        canvas.drawRoundRect(
            centerX - 8f, centerY - 12f,
            centerX + 8f, centerY - 4f,
            1f, 1f, paint
        )
        
        // Buttons
        val buttonSize = 2f
        val buttonSpacing = 4f
        for (row in 0..2) {
            for (col in 0..2) {
                val x = centerX - 4f + (col * buttonSpacing)
                val y = centerY + (row * buttonSpacing)
                canvas.drawCircle(x, y, buttonSize, paint)
            }
        }
    }
    
    private fun interpolateEyeColor(): Int {
        val colors = arrayOf(primaryColor, successColor, errorColor)
        val phase = (eyeColorPhase % 3f)
        val index = phase.toInt()
        val fraction = phase - index
        
        val color1 = colors[index]
        val color2 = colors[(index + 1) % colors.size]
        
        return interpolateColor(color1, color2, fraction)
    }
    
    private fun interpolateColor(color1: Int, color2: Int, fraction: Float): Int {
        val r1 = Color.red(color1)
        val g1 = Color.green(color1)
        val b1 = Color.blue(color1)
        
        val r2 = Color.red(color2)
        val g2 = Color.green(color2)
        val b2 = Color.blue(color2)
        
        val r = (r1 + (r2 - r1) * fraction).toInt()
        val g = (g1 + (g2 - g1) * fraction).toInt()
        val b = (b1 + (b2 - b1) * fraction).toInt()
        
        return Color.rgb(r, g, b)
    }
    
    private fun startIdleAnimation() {
        // Floating animation
        val floatAnimator = ValueAnimator.ofFloat(0f, -8f, 0f)
        floatAnimator.duration = 4000
        floatAnimator.repeatCount = ValueAnimator.INFINITE
        floatAnimator.addUpdateListener { animation ->
            floatOffset = animation.animatedValue as Float
            invalidate()
        }
        
        // Eye scanning animation
        val eyeAnimator = ValueAnimator.ofFloat(0f, 3f)
        eyeAnimator.duration = 3000
        eyeAnimator.repeatCount = ValueAnimator.INFINITE
        eyeAnimator.addUpdateListener { animation ->
            eyeColorPhase = animation.animatedValue as Float
            invalidate()
        }
        
        // Antenna pulse animation
        val antennaAnimator = ValueAnimator.ofFloat(0.8f, 1.3f, 0.8f)
        antennaAnimator.duration = 2000
        antennaAnimator.repeatCount = ValueAnimator.INFINITE
        antennaAnimator.addUpdateListener { animation ->
            antennaPulse = animation.animatedValue as Float
            invalidate()
        }
        
        floatAnimator.start()
        eyeAnimator.start()
        antennaAnimator.start()
    }
    
    fun animateExcited() {
        animationState = "excited"
        
        val scaleAnimator = ValueAnimator.ofFloat(1f, 1.1f, 1f)
        scaleAnimator.duration = 800
        scaleAnimator.repeatCount = 2
        scaleAnimator.addUpdateListener { animation ->
            scaleValue = animation.animatedValue as Float
            invalidate()
        }
        
        val rotateAnimator = ValueAnimator.ofFloat(0f, 5f, -5f, 0f)
        rotateAnimator.duration = 800
        rotateAnimator.repeatCount = 2
        rotateAnimator.addUpdateListener { animation ->
            rotationAngle = animation.animatedValue as Float
            invalidate()
        }
        
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleAnimator, rotateAnimator)
        animatorSet.start()
    }
    
    fun animateConfirming() {
        animationState = "confirming"
        
        val rotateAnimator = ValueAnimator.ofFloat(0f, 360f)
        rotateAnimator.duration = 1500
        rotateAnimator.addUpdateListener { animation ->
            rotationAngle = animation.animatedValue as Float
            invalidate()
        }
        
        val scaleAnimator = ValueAnimator.ofFloat(1f, 1.2f, 1f)
        scaleAnimator.duration = 1500
        scaleAnimator.addUpdateListener { animation ->
            scaleValue = animation.animatedValue as Float
            invalidate()
        }
        
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(rotateAnimator, scaleAnimator)
        animatorSet.start()
    }
    
    fun animateWave() {
        animationState = "wave"
        // Wave animation would be implemented here
        // This would involve animating the arm positions
    }
}