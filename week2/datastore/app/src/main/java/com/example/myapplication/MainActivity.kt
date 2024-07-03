package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.asLiveData
import com.example.myapplication.databinding.ActivityDataStoreTestBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDataStoreTestBinding
    private lateinit var userManager: UserManager
    private var age = -1
    private var frontName = ""
    private var lastName = ""
    private var gender = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_data_store_test)

        userManager = UserManager(dataStore)

        binding.run {
            buttonSave()
            observeData()
        }
    }

    private fun ActivityDataStoreTestBinding.buttonSave() {
        btnSave.setOnClickListener {
            frontName = etFname.text.toString()
            lastName = etLname.text.toString()
            age = etAge.text.toString().toInt()
            val isMale = switchGender.isChecked

            CoroutineScope(IO).launch {
                userManager.storeUser(age, frontName, lastName, isMale)
            }
        }
    }

    private fun observeData() {
        userManager.userAgeFlow.asLiveData().observe(this) {
            if (it != null) {
                age = it
                binding.tvAge.text = it.toString()
            }
        }

        userManager.userFirstNameFlow.asLiveData().observe(this) {
            if (it != null) {
                frontName = it
                binding.tvFname.text = it
            }
        }

        userManager.userLastNameFlow.asLiveData().observe(this) {
            if (it != null) {
                lastName = it
                binding.tvLname.text = it
            }
        }

        userManager.userGenderFlow.asLiveData().observe(this) {
            if (it != null) {
                gender = if (it) "남성" else "여성"
                binding.tvGender.text = gender
            }
        }
    }

}