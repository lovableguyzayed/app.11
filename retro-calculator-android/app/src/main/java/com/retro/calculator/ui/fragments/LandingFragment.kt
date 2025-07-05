package com.retro.calculator.ui.fragments

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.Fragment
import com.retro.calculator.R
import com.retro.calculator.databinding.FragmentLandingBinding
import com.retro.calculator.utils.SoundManager

class LandingFragment : Fragment() {
    
    private var _binding: FragmentLandingBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var soundManager: SoundManager
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLandingBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        soundManager = SoundManager(requireContext())
        
        setupAnimations()
        setupClickListeners()
    }
    
    private fun setupAnimations() {
        // Robot entrance animation
        binding.robotContainer.alpha = 0f
        binding.robotContainer.translationY = 100f
        
        val robotAnimator = ObjectAnimator.ofFloat(binding.robotContainer, "alpha", 0f, 1f)
        val robotTranslation = ObjectAnimator.ofFloat(binding.robotContainer, "translationY", 100f, 0f)
        
        val robotSet = AnimatorSet()
        robotSet.playTogether(robotAnimator, robotTranslation)
        robotSet.duration = 2000
        robotSet.interpolator = AccelerateDecelerateInterpolator()
        
        // Title glow animation
        val titleGlow = ObjectAnimator.ofFloat(binding.titleText, "alpha", 0.3f, 1f)
        titleGlow.duration = 2000
        titleGlow.repeatMode = ObjectAnimator.REVERSE
        titleGlow.repeatCount = ObjectAnimator.INFINITE
        
        // Button pulse animation
        val buttonPulse = ObjectAnimator.ofFloat(binding.enterButton, "scaleX", 1f, 1.05f)
        val buttonPulseY = ObjectAnimator.ofFloat(binding.enterButton, "scaleY", 1f, 1.05f)
        
        val buttonSet = AnimatorSet()
        buttonSet.playTogether(buttonPulse, buttonPulseY)
        buttonSet.duration = 1500
        buttonSet.repeatMode = ObjectAnimator.REVERSE
        buttonSet.repeatCount = ObjectAnimator.INFINITE
        
        // Start animations
        robotSet.start()
        
        view?.postDelayed({
            titleGlow.start()
            buttonSet.start()
            startRobotFloating()
        }, 2000)
    }
    
    private fun startRobotFloating() {
        val floatUp = ObjectAnimator.ofFloat(binding.robotContainer, "translationY", 0f, -8f)
        val floatDown = ObjectAnimator.ofFloat(binding.robotContainer, "translationY", -8f, 0f)
        
        val floatSet = AnimatorSet()
        floatSet.playSequentially(floatUp, floatDown)
        floatSet.duration = 4000
        floatSet.repeatCount = ObjectAnimator.INFINITE
        floatSet.interpolator = AccelerateDecelerateInterpolator()
        floatSet.start()
    }
    
    private fun setupClickListeners() {
        binding.enterButton.setOnClickListener {
            soundManager.playButtonClick()
            
            // Fade out animation
            val fadeOut = ObjectAnimator.ofFloat(binding.root, "alpha", 1f, 0f)
            fadeOut.duration = 1000
            fadeOut.start()
            
            fadeOut.addListener(object : android.animation.Animator.AnimatorListener {
                override fun onAnimationStart(animation: android.animation.Animator) {}
                override fun onAnimationEnd(animation: android.animation.Animator) {
                    navigateToCalculator()
                }
                override fun onAnimationCancel(animation: android.animation.Animator) {}
                override fun onAnimationRepeat(animation: android.animation.Animator) {}
            })
        }
        
        // Robot click interaction
        binding.robotContainer.setOnClickListener {
            soundManager.playRobotBeep()
            
            val bounce = ObjectAnimator.ofFloat(binding.robotContainer, "scaleX", 1f, 1.1f, 1f)
            val bounceY = ObjectAnimator.ofFloat(binding.robotContainer, "scaleY", 1f, 1.1f, 1f)
            
            val bounceSet = AnimatorSet()
            bounceSet.playTogether(bounce, bounceY)
            bounceSet.duration = 300
            bounceSet.start()
        }
    }
    
    private fun navigateToCalculator() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CalculatorFragment())
            .addToBackStack(null)
            .commit()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        soundManager.release()
        _binding = null
    }
}