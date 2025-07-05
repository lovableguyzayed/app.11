package com.retro.calculator.ui.fragments

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.retro.calculator.R
import com.retro.calculator.databinding.FragmentCalculatorBinding
import com.retro.calculator.model.CalculationStep
import com.retro.calculator.model.Unit
import com.retro.calculator.utils.SoundManager
import com.retro.calculator.viewmodel.CalculatorViewModel

class CalculatorFragment : Fragment() {
    
    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var viewModel: CalculatorViewModel
    private lateinit var soundManager: SoundManager
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel = ViewModelProvider(requireActivity())[CalculatorViewModel::class.java]
        soundManager = SoundManager(requireContext())
        
        setupUI()
        observeViewModel()
        setupClickListeners()
        startRobotAnimations()
    }
    
    private fun setupUI() {
        // Setup unit spinner
        val units = Unit.getAllUnits().map { it.displayName }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, units)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.unitSpinner.adapter = adapter
        
        binding.unitSpinner.setOnItemSelectedListener(object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedUnit = Unit.getAllUnits()[position]
                viewModel.updateUnit(selectedUnit)
                soundManager.playButtonClick()
            }
            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {}
        })
    }
    
    private fun observeViewModel() {
        viewModel.currentStep.observe(viewLifecycleOwner) { step ->
            updateUIForStep(step)
        }
        
        viewModel.quantity.observe(viewLifecycleOwner) { quantity ->
            if (binding.quantityInput.text.toString() != quantity) {
                binding.quantityInput.setText(quantity)
            }
        }
        
        viewModel.rate.observe(viewLifecycleOwner) { rate ->
            if (binding.rateInput.text.toString() != rate) {
                binding.rateInput.setText(rate)
            }
        }
        
        viewModel.result.observe(viewLifecycleOwner) { result ->
            binding.resultText.text = result
        }
        
        viewModel.robotMessage.observe(viewLifecycleOwner) { message ->
            binding.robotMessage.text = message
        }
        
        viewModel.isCalculating.observe(viewLifecycleOwner) { isCalculating ->
            binding.calculateButton.isEnabled = !isCalculating
            if (isCalculating) {
                binding.progressBar.visibility = View.VISIBLE
                soundManager.playCalculating()
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
        
        viewModel.robotAnimationState.observe(viewLifecycleOwner) { state ->
            animateRobot(state)
        }
        
        viewModel.selectedUnit.observe(viewLifecycleOwner) { unit ->
            val position = Unit.getAllUnits().indexOf(unit)
            if (binding.unitSpinner.selectedItemPosition != position) {
                binding.unitSpinner.setSelection(position)
            }
        }
    }
    
    private fun updateUIForStep(step: CalculationStep) {
        when (step) {
            CalculationStep.QUANTITY_INPUT -> {
                binding.quantityInputLayout.visibility = View.VISIBLE
                binding.rateInputLayout.visibility = View.GONE
                binding.resultLayout.visibility = View.GONE
                binding.nextButton.visibility = View.VISIBLE
                binding.calculateButton.visibility = View.GONE
                binding.backButton.visibility = View.GONE
                binding.newCalculationButton.visibility = View.GONE
                binding.stepIndicator.text = "STEP 1: ENTER QUANTITY"
            }
            CalculationStep.RATE_INPUT -> {
                binding.quantityInputLayout.visibility = View.GONE
                binding.rateInputLayout.visibility = View.VISIBLE
                binding.resultLayout.visibility = View.GONE
                binding.nextButton.visibility = View.GONE
                binding.calculateButton.visibility = View.VISIBLE
                binding.backButton.visibility = View.VISIBLE
                binding.newCalculationButton.visibility = View.GONE
                binding.stepIndicator.text = "STEP 2: ENTER RATE"
            }
            CalculationStep.RESULT -> {
                binding.quantityInputLayout.visibility = View.GONE
                binding.rateInputLayout.visibility = View.GONE
                binding.resultLayout.visibility = View.VISIBLE
                binding.nextButton.visibility = View.GONE
                binding.calculateButton.visibility = View.GONE
                binding.backButton.visibility = View.GONE
                binding.newCalculationButton.visibility = View.VISIBLE
                binding.stepIndicator.text = "CALCULATION COMPLETE"
                
                // Show calculation breakdown
                val quantity = viewModel.quantity.value ?: ""
                val rate = viewModel.rate.value ?: ""
                val unit = viewModel.selectedUnit.value?.displayName ?: ""
                val result = viewModel.result.value ?: ""
                binding.calculationBreakdown.text = "$quantity $unit × ₹$rate = $result"
            }
        }
    }
    
    private fun setupClickListeners() {
        binding.nextButton.setOnClickListener {
            val quantity = binding.quantityInput.text.toString()
            viewModel.updateQuantity(quantity)
            viewModel.proceedToRateInput()
            soundManager.playButtonClick()
        }
        
        binding.calculateButton.setOnClickListener {
            val rate = binding.rateInput.text.toString()
            viewModel.updateRate(rate)
            viewModel.calculate()
            soundManager.playCalculateSuccess()
        }
        
        binding.backButton.setOnClickListener {
            viewModel.goBack()
            soundManager.playButtonClick()
        }
        
        binding.resetButton.setOnClickListener {
            viewModel.reset()
            soundManager.playReset()
        }
        
        binding.newCalculationButton.setOnClickListener {
            viewModel.reset()
            soundManager.playButtonClick()
        }
        
        // Robot interaction
        binding.robotContainer.setOnClickListener {
            soundManager.playRobotBeep()
            animateRobot("excited")
        }
    }
    
    private fun startRobotAnimations() {
        // Continuous floating animation
        val floatUp = ObjectAnimator.ofFloat(binding.robotContainer, "translationY", 0f, -5f)
        val floatDown = ObjectAnimator.ofFloat(binding.robotContainer, "translationY", -5f, 0f)
        
        val floatSet = AnimatorSet()
        floatSet.playSequentially(floatUp, floatDown)
        floatSet.duration = 3000
        floatSet.repeatCount = ObjectAnimator.INFINITE
        floatSet.start()
        
        // Eye blinking animation
        val eyeBlink = ObjectAnimator.ofFloat(binding.robotEyes, "scaleY", 1f, 0.1f, 1f)
        eyeBlink.duration = 150
        eyeBlink.repeatCount = ObjectAnimator.INFINITE
        eyeBlink.repeatMode = ObjectAnimator.RESTART
        eyeBlink.startDelay = 3000
        
        view?.postDelayed({
            eyeBlink.start()
        }, 3000)
    }
    
    private fun animateRobot(state: String) {
        when (state) {
            "excited" -> {
                val scaleX = ObjectAnimator.ofFloat(binding.robotContainer, "scaleX", 1f, 1.1f, 1f)
                val scaleY = ObjectAnimator.ofFloat(binding.robotContainer, "scaleY", 1f, 1.1f, 1f)
                val rotation = ObjectAnimator.ofFloat(binding.robotContainer, "rotation", 0f, 5f, -5f, 0f)
                
                val excitedSet = AnimatorSet()
                excitedSet.playTogether(scaleX, scaleY, rotation)
                excitedSet.duration = 800
                excitedSet.start()
            }
            "confirming" -> {
                val rotation = ObjectAnimator.ofFloat(binding.robotContainer, "rotation", 0f, 360f)
                rotation.duration = 1500
                rotation.start()
                
                soundManager.playConfirmation()
            }
            "wave" -> {
                val wave = ObjectAnimator.ofFloat(binding.robotArms, "rotation", 0f, -20f, 15f, -10f, 0f)
                wave.duration = 1200
                wave.repeatCount = 3
                wave.start()
            }
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        soundManager.release()
        _binding = null
    }
}