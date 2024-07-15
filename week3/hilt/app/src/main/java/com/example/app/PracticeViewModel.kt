package com.example.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PracticeViewModel @Inject constructor(
    private val calculator: Calculator
) : ViewModel() {

    private val _testNumber = MutableLiveData(0)
    val testNumber: LiveData<Int> get() = _testNumber

    fun onButtonClicked() {
        setTestText(
            calculator.addNumberTwo(testNumber.value!!)
        )
    }

    private fun setTestText(number: Int) {
        _testNumber.value = number
    }
}