package com.napa.foodstore.presentation.feature.cart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.napa.foodstore.data.repository.CartRepository
import com.napa.foodstore.tools.MainCoroutineRule
import com.napa.foodstore.tools.getOrAwaitValue
import com.napa.foodstore.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CartViewModelTest {

    @MockK
    private lateinit var repo: CartRepository

    private lateinit var viewModel: CartViewModel

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        coEvery { repo.getUserCartData() } returns flow {
            emit(
                ResultWrapper.Success(
                    Pair(
                        listOf(
                            mockk(relaxed = true),
                            mockk(relaxed = true)
                        ),
                        250000
                    )
                )
            )
        }
        viewModel = spyk(CartViewModel(repo))
        val updateResultMock = flow {
            emit(ResultWrapper.Success(true))
        }
        coEvery { repo.decreaseCart(any()) } returns updateResultMock
        coEvery { repo.increaseCart(any()) } returns updateResultMock
        coEvery { repo.deleteCart(any()) } returns updateResultMock
        coEvery { repo.setCartNotes(any()) } returns updateResultMock
    }

    @Test
    fun `test cart list`() {
        val result = viewModel.cartList.getOrAwaitValue()
        assertEquals(result.payload?.first?.size, 2)
        assertEquals(result.payload?.second, 250000)
    }

    @Test
    fun `test decrease cart`() {
        viewModel.decreaseCart(mockk())
        coVerify { repo.decreaseCart(any()) }
    }

    @Test
    fun `test increase cart`() {
        viewModel.increaseCart(mockk())
        coVerify { repo.increaseCart(any()) }
    }

    @Test
    fun `test remove cart`() {
        viewModel.removeCart(mockk())
        coVerify { repo.deleteCart(any()) }
    }

    @Test
    fun `test set cart notes`() {
        viewModel.setCartNotes(mockk())
        coVerify { repo.setCartNotes(any()) }
    }

    @Test
    fun `test cart not empty`() {
        val result = viewModel.cartNotEmpty()
        coVerify { viewModel.cartNotEmpty() }
        assertFalse(result)
    }
}
