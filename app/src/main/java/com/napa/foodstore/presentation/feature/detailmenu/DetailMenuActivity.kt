package com.napa.foodstore.presentation.feature.detailmenu

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import coil.load
import com.napa.foodstore.databinding.ActivityDetailMenuBinding
import com.napa.foodstore.model.Menu
import com.napa.foodstore.utils.proceedWhen
import com.napa.foodstore.utils.toCurrencyFormat
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailMenuActivity : AppCompatActivity() {

    private val binding: ActivityDetailMenuBinding by lazy {
        ActivityDetailMenuBinding.inflate(layoutInflater)
    }

    private val viewModel: DetailMenuViewModel by viewModel {
        parametersOf(intent.extras ?: bundleOf())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        bindMenu(viewModel.menu)
        observeData()
        setClickListener()
    }

    private fun setClickListener() {
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        binding.ivMinus.setOnClickListener {
            viewModel.minus()
        }
        binding.ivPlus.setOnClickListener {
            viewModel.add()
        }
        binding.tvLocation.setOnClickListener {
            viewModel.onLocationClicked()
        }
        binding.btnAddToCart.setOnClickListener {
            viewModel.addToCart()
        }
    }

    private fun observeData() {
        viewModel.priceLiveData.observe(this) {
            binding.tvCalculatedProductPrice.text = it.toCurrencyFormat()
        }
        viewModel.menuCountLiveData.observe(this) {
            binding.tvProductCount.text = it.toString()
        }
        viewModel.addToCartResult.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(this, "Berhasil Tambahkan Ke Keranjang !", Toast.LENGTH_SHORT).show()
                    finish()
                },
                doOnError = {
                    Toast.makeText(this, it.exception?.message.orEmpty(), Toast.LENGTH_SHORT).show()
                }
            )
        }
        viewModel.navigateToMapsLiveData.observe(this) { location ->
            location?.let {
                navigateToMaps(location)
            }
        }
    }

    private fun bindMenu(menu: Menu?) {
        menu?.let { item ->
            binding.ivMenu.load(item.menuImgUrl) {
                crossfade(true)
            }
            binding.tvMenuName.text = item.name
            binding.tvDesc.text = item.desc
            binding.tvPriceMenu.text = item.price.toCurrencyFormat()
            binding.tvLocation.text = item.location
        }
    }

    private fun navigateToMaps(location: String) {
        val gmmIntentUri = Uri.parse("http://maps.google.com/?q=$location")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
//        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    companion object {
        const val EXTRA_PRODUCT = "EXTRA_PRODUCT"
        fun startActivity(context: Context, product: Menu) {
            val intent = Intent(context, DetailMenuActivity::class.java)
            intent.putExtra(EXTRA_PRODUCT, product)
            context.startActivity(intent)
        }
    }
}
