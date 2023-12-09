package com.napa.foodstore.presentation.feature.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.napa.foodstore.data.repository.UserRepository
import com.napa.foodstore.model.User
import com.napa.foodstore.tools.MainCoroutineRule
import com.napa.foodstore.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ProfileViewModelTest {
    @MockK
    lateinit var userRepo: UserRepository

    private lateinit var viewModel: ProfileViewModel

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(
        UnconfinedTestDispatcher()
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(ProfileViewModel(userRepo))
        val updateResult = flow {
            emit(ResultWrapper.Success(true))
        }
        coEvery { userRepo.getCurrentUser() } returns User("fullName", "url", "email")
        coEvery { userRepo.sendChangePasswordRequestByEmail() } returns true
        coEvery { userRepo.doLogout() } returns true
        coEvery { userRepo.updateProfile(any(), any()) } returns updateResult
    }

    @Test
    fun `get current user`() {
        viewModel.getCurrentUser()
        coVerify { userRepo.getCurrentUser() }
    }

    @Test
    fun `create change password request`() {
        viewModel.createChangePwdRequest()
        coVerify { userRepo.sendChangePasswordRequestByEmail() }
    }

    @Test
    fun `do logout`() {
        viewModel.doLogout()
        coVerify { userRepo.doLogout() }
    }

    @Test
    fun `update profile`() {
        viewModel.updateFullName("full name")
        coVerify { userRepo.updateProfile(any()) }
    }
}
