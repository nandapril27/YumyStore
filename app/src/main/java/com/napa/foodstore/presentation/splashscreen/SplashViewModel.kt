package com.napa.foodstore.presentation.splashscreen

import androidx.lifecycle.ViewModel
import com.napa.foodstore.data.repository.UserRepository

class SplashViewModel(private val repository: UserRepository) : ViewModel() {

    fun isUserLoggedIn() = repository.isLoggedIn()
}
