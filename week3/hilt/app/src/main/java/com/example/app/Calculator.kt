package com.example.app

import javax.inject.Inject

class Calculator @Inject constructor() {
    fun addNumberTwo(number: Int): Int {
        return number + 2
    }
}