package com.napa.foodstore.presentation.feature.detailmenu

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.napa.foodstore.data.repository.CartRepository
import com.napa.foodstore.model.Menu
import com.napa.foodstore.utils.ResultWrapper
import kotlinx.coroutines.launch

class DetailMenuViewModel(
    private val extras: Bundle?,
    private val cartRepository: CartRepository
) : ViewModel() {

    val menu = extras?.getParcelable<Menu>(DetailMenuActivity.EXTRA_PRODUCT)

    val priceLiveData = MutableLiveData<Int>().apply {
        postValue(0)
    }
    val menuCountLiveData = MutableLiveData<Int>().apply {
        postValue(0)
    }
    private val _addToCartResult = MutableLiveData<ResultWrapper<Boolean>>()
    val addToCartResult: LiveData<ResultWrapper<Boolean>>
        get() = _addToCartResult

    val navigateToMapsLiveData = MutableLiveData<String?>()

    fun onLocationClicked() {
        val location = menu?.location
        navigateToMapsLiveData.value = location
    }

    fun add() {
        val count = (menuCountLiveData.value ?: 0) + 1
        menuCountLiveData.postValue(count)
        priceLiveData.postValue(menu?.price?.times(count) ?: 0)
    }

    fun minus() {
        if ((menuCountLiveData.value ?: 0) > 0) {
            val count = (menuCountLiveData.value ?: 0) - 1
            menuCountLiveData.postValue(count)
            priceLiveData.postValue(menu?.price?.times(count) ?: 0)
        }
    }

    fun addToCart() {
        viewModelScope.launch {
            val menuQuantity =
                if ((menuCountLiveData.value ?: 0) <= 0) 1 else menuCountLiveData.value ?: 0
            menu?.let {
                cartRepository.createCart(it, menuQuantity).collect { result ->
                    _addToCartResult.postValue(result)
                }
            }
        }
    }
}
