package com.napa.foodstore.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.firebase.auth.FirebaseAuth
import com.napa.foodstore.data.local.database.AppDatabase
import com.napa.foodstore.data.local.database.datasource.CartDataSource
import com.napa.foodstore.data.local.database.datasource.CartDatabaseDataSource
import com.napa.foodstore.data.local.datastore.UserPreferenceDataSource
import com.napa.foodstore.data.local.datastore.UserPreferenceDataSourceImpl
import com.napa.foodstore.data.local.datastore.appDataStore
import com.napa.foodstore.data.network.api.datasource.FoodStoreApiDataSource
import com.napa.foodstore.data.network.api.datasource.FoodStoreDataSource
import com.napa.foodstore.data.network.api.service.FoodStoreApiService
import com.napa.foodstore.data.network.firebase.auth.FirebaseAuthDataSource
import com.napa.foodstore.data.network.firebase.auth.FirebaseAuthDataSourceImpl
import com.napa.foodstore.data.repository.CartRepository
import com.napa.foodstore.data.repository.CartRepositoryImpl
import com.napa.foodstore.data.repository.MenuRepository
import com.napa.foodstore.data.repository.MenuRepositoryImpl
import com.napa.foodstore.data.repository.UserRepository
import com.napa.foodstore.data.repository.UserRepositoryImpl
import com.napa.foodstore.presentation.feature.cart.CartViewModel
import com.napa.foodstore.presentation.feature.checkout.CheckoutViewModel
import com.napa.foodstore.presentation.feature.detailmenu.DetailMenuViewModel
import com.napa.foodstore.presentation.feature.home.HomeViewModel
import com.napa.foodstore.presentation.feature.login.LoginViewModel
import com.napa.foodstore.presentation.feature.profile.ProfileViewModel
import com.napa.foodstore.presentation.feature.register.RegisterViewModel
import com.napa.foodstore.presentation.splashscreen.SplashViewModel
import com.napa.foodstore.utils.AssetWrapper
import com.napa.foodstore.utils.PreferenceDataStoreHelper
import com.napa.foodstore.utils.PreferenceDataStoreHelperImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {

    private val localModule = module {
        single { AppDatabase.getInstance(androidContext()) }
        single { get<AppDatabase>().cartDao() }
        single { androidContext().appDataStore }
        single<PreferenceDataStoreHelper> { PreferenceDataStoreHelperImpl(get()) }
    }

    private val networkModule = module {
        single { ChuckerInterceptor(androidContext()) }
        single { FoodStoreApiService.invoke(get()) }
        single { FirebaseAuth.getInstance() }
    }

    private val dataSourceModule = module {
        single<CartDataSource> { CartDatabaseDataSource(get()) }
        single<UserPreferenceDataSource> { UserPreferenceDataSourceImpl(get()) }
        single<FoodStoreDataSource> { FoodStoreApiDataSource(get()) }
        single<FirebaseAuthDataSource> { FirebaseAuthDataSourceImpl(get()) }
    }

    private val repositoryModule = module {
        single<CartRepository> { CartRepositoryImpl(get(), get()) }
        single<MenuRepository> { MenuRepositoryImpl(get()) }
        single<UserRepository> { UserRepositoryImpl(get()) }
    }

    private val viewModelModule = module {
        viewModelOf(::HomeViewModel)
        viewModelOf(::CartViewModel)
        viewModel { params -> DetailMenuViewModel(params.get(), get()) }
        viewModelOf(::CheckoutViewModel)
        viewModelOf(::ProfileViewModel)
        viewModelOf(::RegisterViewModel)
        viewModelOf(::LoginViewModel)
        viewModelOf(::SplashViewModel)
    }

    private val utilsModule = module {
        single { AssetWrapper(androidContext()) }
    }

    val modules: List<Module> = listOf(
        localModule,
        networkModule,
        dataSourceModule,
        repositoryModule,
        viewModelModule,
        utilsModule
    )
}
