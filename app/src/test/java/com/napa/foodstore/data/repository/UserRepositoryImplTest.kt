package com.napa.foodstore.data.repository

import app.cash.turbine.test
import com.google.firebase.auth.FirebaseUser
import com.napa.foodstore.data.network.firebase.auth.FirebaseAuthDataSource
import com.napa.foodstore.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {

    @MockK
    lateinit var dataSource: FirebaseAuthDataSource

    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        userRepository = UserRepositoryImpl(dataSource)
    }

    @Test
    fun `doLogin, result loading`() {
        coEvery { dataSource.doLogin(any(), any()) } returns true
        runTest {
            userRepository.doLogin("email", "pass").map {
                delay(100)
                it
            }.test {
                delay(120)
                val data = expectMostRecentItem()
                println(data)
                TestCase.assertTrue(data is ResultWrapper.Loading)
                coVerify { dataSource.doLogin(any(), any()) }
            }
        }
    }

    @Test
    fun `doLogin, result success`() {
        coEvery { dataSource.doLogin(any(), any()) } returns true
        runTest {
            userRepository.doLogin("email", "pass").map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                println(data)
                TestCase.assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.doLogin(any(), any()) }
            }
        }
    }

    @Test
    fun `doLogin, result error`() {
        coEvery { dataSource.doLogin(any(), any()) } throws IllegalStateException()
        runTest {
            userRepository.doLogin("email", "pass").map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                println(data)
                TestCase.assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.doLogin(any(), any()) }
            }
        }
    }

    @Test
    fun `doRegister, result loading`() {
        coEvery { dataSource.doRegister(any(), any(), any()) } returns true
        runTest {
            userRepository.doRegister("full name", "email", "pass").map {
                delay(100)
                it
            }.test {
                delay(120)
                val data = expectMostRecentItem()
                println(data)
                TestCase.assertTrue(data is ResultWrapper.Loading)
                coVerify { dataSource.doRegister(any(), any(), any()) }
            }
        }
    }

    @Test
    fun `doRegister, result success`() {
        coEvery { dataSource.doRegister(any(), any(), any()) } returns true
        runTest {
            userRepository.doRegister("full name", "email", "pass").map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                println(data)
                TestCase.assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.doRegister(any(), any(), any()) }
            }
        }
    }

    @Test
    fun `doRegister, result error`() {
        coEvery { dataSource.doRegister(any(), any(), any()) } throws IllegalStateException()
        runTest {
            userRepository.doRegister("full name", "email", "pass").map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                println(data)
                TestCase.assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.doRegister(any(), any(), any()) }
            }
        }
    }

    @Test
    fun `update profile, result loading`() {
        coEvery { dataSource.updateProfile(any(), any()) } returns true
        runTest {
            userRepository.updateProfile("email", null).map {
                delay(100)
                it
            }.test {
                delay(120)
                val data = expectMostRecentItem()
                println(data)
                TestCase.assertTrue(data is ResultWrapper.Loading)
                coVerify { dataSource.updateProfile(any(), any()) }
            }
        }
    }

    @Test
    fun `update profile, result success`() {
        coEvery { dataSource.updateProfile(any(), any()) } returns true
        runTest {
            userRepository.updateProfile("email", null).map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                println(data)
                TestCase.assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.updateProfile(any(), any()) }
            }
        }
    }

    @Test
    fun `update profile, result error`() {
        coEvery { dataSource.updateProfile(any(), any()) } throws IllegalStateException()
        runTest {
            userRepository.updateProfile("email", null).map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                println(data)
                TestCase.assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.updateProfile(any(), any()) }
            }
        }
    }

    @Test
    fun doLogout() {
        coEvery { dataSource.doLogout() } returns true
        runTest {
            val result = userRepository.doLogout()
            assertEquals(result, true)
            coVerify { dataSource.doLogout() }
        }
    }

    @Test
    fun isLoggedIn() {
        coEvery { dataSource.isLoggedIn() } returns true
        runTest {
            val result = userRepository.isLoggedIn()
            assertEquals(result, true)
            coVerify { dataSource.isLoggedIn() }
        }
    }

    @Test
    fun getCurrentUser() {
        val mockUser = mockk<FirebaseUser>()
        coEvery { dataSource.getCurrentUser() } returns mockUser
        coEvery { dataSource.getCurrentUser()?.displayName } returns "name"
        coEvery { dataSource.getCurrentUser()?.email } returns "email"
        coEvery { dataSource.getCurrentUser()?.photoUrl } returns mockk(relaxed = true)
        runTest {
            val name = userRepository.getCurrentUser()?.fullName
            val email = userRepository.getCurrentUser()?.email
            assertEquals(name, "name")
            assertEquals(email, "email")
            coVerify { dataSource.getCurrentUser() }
        }
    }

    @Test
    fun `update password, result loading`() {
        coEvery { dataSource.updatePassword(any()) } returns true
        runTest {
            userRepository.updatePassword("new pass").map {
                delay(100)
                it
            }.test {
                delay(120)
                val data = expectMostRecentItem()
                println(data)
                TestCase.assertTrue(data is ResultWrapper.Loading)
                coVerify { dataSource.updatePassword(any()) }
            }
        }
    }

    @Test
    fun `update password, result success`() {
        coEvery { dataSource.updatePassword(any()) } returns true
        runTest {
            userRepository.updatePassword("new pass").map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                println(data)
                TestCase.assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.updatePassword(any()) }
            }
        }
    }

    @Test
    fun `update password, result error`() {
        coEvery { dataSource.updatePassword(any()) } throws IllegalStateException()
        runTest {
            userRepository.updatePassword("new pass").map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                println(data)
                TestCase.assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.updatePassword(any()) }
            }
        }
    }

    @Test
    fun `update email, result loading`() {
        coEvery { dataSource.updateEmail(any()) } returns true
        runTest {
            userRepository.updateEmail("new email").map {
                delay(100)
                it
            }.test {
                delay(120)
                val data = expectMostRecentItem()
                println(data)
                TestCase.assertTrue(data is ResultWrapper.Loading)
                coVerify { dataSource.updateEmail(any()) }
            }
        }
    }

    @Test
    fun `update email, result success`() {
        coEvery { dataSource.updateEmail(any()) } returns true
        runTest {
            userRepository.updateEmail("new email").map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                println(data)
                TestCase.assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.updateEmail(any()) }
            }
        }
    }

    @Test
    fun `update email, result error`() {
        coEvery { dataSource.updateEmail(any()) } throws IllegalStateException()
        runTest {
            userRepository.updateEmail("new email").map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                println(data)
                TestCase.assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.updateEmail(any()) }
            }
        }
    }

    @Test
    fun sendChangePasswordRequestByEmail() {
        coEvery { dataSource.sendChangePasswordRequestByEmail() } returns true
        runTest {
            val result = userRepository.sendChangePasswordRequestByEmail()
            assertEquals(result, true)
            coVerify { dataSource.sendChangePasswordRequestByEmail() }
        }
    }
}
