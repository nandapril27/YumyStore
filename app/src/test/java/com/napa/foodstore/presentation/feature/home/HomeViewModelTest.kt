package com.napa.foodstore.presentation.feature.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.napa.foodstore.data.local.datastore.UserPreferenceDataSource
import com.napa.foodstore.data.repository.MenuRepository
import com.napa.foodstore.tools.MainCoroutineRule
import com.napa.foodstore.utils.AssetWrapper
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule

class HomeViewModelTest {

    @MockK
    private lateinit var repo: MenuRepository

    @MockK
    private lateinit var userPreferenceDataSource: UserPreferenceDataSource

    @MockK
    private lateinit var assetWrapper: AssetWrapper

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }
}
