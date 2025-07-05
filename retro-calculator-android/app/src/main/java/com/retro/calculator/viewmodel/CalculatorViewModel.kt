package com.retro.calculator.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.retro.calculator.model.CalculationStep
import com.retro.calculator.model.Unit
import com.retro.calculator.utils.SoundManager
import java.text.DecimalFormat

class CalculatorViewModel : ViewModel() {
    
    private val _currentStep = MutableLiveData(CalculationStep.QUANTITY_INPUT)
    val currentStep: LiveData<CalculationStep> = _currentStep
    
    private val _quantity = MutableLiveData("")
    val quantity: LiveData<String> = _quantity
    
    private val _selectedUnit = MutableLiveData(Unit.KG)
    val selectedUnit: LiveData<Unit> = _selectedUnit
    
    private val _rate = MutableLiveData("")
    val rate: LiveData<String> = _rate
    
    private val _result = MutableLiveData("")
    val result: LiveData<String> = _result
    
    private val _robotMessage = MutableLiveData("Welcome! Let's calculate some unit prices together. Start by entering a quantity.")
    val robotMessage: LiveData<String> = _robotMessage
    
    private val _isCalculating = MutableLiveData(false)
    val isCalculating: LiveData<Boolean> = _isCalculating
    
    private val _robotAnimationState = MutableLiveData("idle")
    val robotAnimationState: LiveData<String> = _robotAnimationState
    
    private val decimalFormat = DecimalFormat("#,##0.000")
    private val currencyFormat = DecimalFormat("#,##0.00")
    
    fun updateQuantity(quantity: String) {
        _quantity.value = quantity
        if (quantity.isNotEmpty()) {
            updateRobotMessage("Great! You entered $quantity ${_selectedUnit.value?.displayName}. Now let's set the rate per unit.")
            _robotAnimationState.value = "excited"
        }
    }
    
    fun updateUnit(unit: Unit) {
        _selectedUnit.value = unit
        updateRobotMessage("Perfect! We're calculating for ${unit.displayName}. Enter your quantity and we'll proceed.")
    }
    
    fun updateRate(rate: String) {
        _rate.value = rate
        if (rate.isNotEmpty()) {
            updateRobotMessage("Excellent! Rate set to ₹$rate per ${_selectedUnit.value?.displayName}. Ready to calculate!")
            _robotAnimationState.value = "excited"
        }
    }
    
    fun proceedToRateInput() {
        val quantityValue = _quantity.value?.toDoubleOrNull()
        if (quantityValue != null && quantityValue > 0) {
            _currentStep.value = CalculationStep.RATE_INPUT
            updateRobotMessage("Now enter the rate per ${_selectedUnit.value?.displayName} in rupees.")
            _robotAnimationState.value = "wave"
        } else {
            updateRobotMessage("Please enter a valid quantity greater than 0.")
            _robotAnimationState.value = "idle"
        }
    }
    
    fun calculate() {
        val quantityValue = _quantity.value?.toDoubleOrNull()
        val rateValue = _rate.value?.toDoubleOrNull()
        
        if (quantityValue != null && rateValue != null && quantityValue > 0 && rateValue > 0) {
            _isCalculating.value = true
            _robotAnimationState.value = "confirming"
            updateRobotMessage("Calculating... Processing your request!")
            
            // Simulate calculation time
            android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                val resultValue = quantityValue * rateValue
                val formattedResult = if (_selectedUnit.value?.category == "weight") {
                    decimalFormat.format(resultValue)
                } else {
                    currencyFormat.format(resultValue)
                }
                
                _result.value = "₹$formattedResult"
                _currentStep.value = CalculationStep.RESULT
                _isCalculating.value = false
                _robotAnimationState.value = "excited"
                
                updateRobotMessage("Calculation complete! ${_quantity.value} ${_selectedUnit.value?.displayName} at ₹${_rate.value} per unit = ₹$formattedResult total.")
            }, 1500)
        } else {
            updateRobotMessage("Please enter valid numbers for both quantity and rate.")
            _robotAnimationState.value = "idle"
        }
    }
    
    fun goBack() {
        _currentStep.value = CalculationStep.QUANTITY_INPUT
        updateRobotMessage("Back to quantity input. Modify your values as needed.")
        _robotAnimationState.value = "wave"
    }
    
    fun reset() {
        _currentStep.value = CalculationStep.QUANTITY_INPUT
        _quantity.value = ""
        _rate.value = ""
        _result.value = ""
        _selectedUnit.value = Unit.KG
        _isCalculating.value = false
        _robotAnimationState.value = "idle"
        updateRobotMessage("Reset complete! Let's start fresh with a new calculation.")
    }
    
    private fun updateRobotMessage(message: String) {
        _robotMessage.value = message
    }
}