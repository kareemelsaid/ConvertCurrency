package com.example.currency.domanLayer.useCase

import android.content.Context
import com.example.currency.dataLayer.model.ConvertCurrencyResponse
import com.example.currency.dataLayer.repo.CurrencyRepositoryInterface
import com.example.currency.networking.common.CheckInternetConnectionInterface
import com.example.currency.networking.common.NetworkResource
import com.example.currency.utils.CoroutineTestRule
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ConvertCurrencyUseCaseTest : TestCase() {

    private lateinit var sut: ConvertCurrencyUseCase
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()
    @Mock
    private lateinit var currencyRepository: CurrencyRepositoryInterface
    @Mock
    private lateinit var checkInternetConnection: CheckInternetConnectionInterface
    @Mock
    private lateinit var context: Context

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        sut = ConvertCurrencyUseCase(currencyRepository, checkInternetConnection)
    }

    @Test
    fun `if internet connection return true then return success`() =
        kotlinx.coroutines.test.runTest {
            // Given
            val stubbedResponse = NetworkResource.Success(ConvertCurrencyResponse(1.0,true))
            val expectedResponse = flowOf(stubbedResponse)
            val expected = Mockito.lenient().`when`(currencyRepository.convertCurrency("USD", 1.0, "EGY"))
                .thenAnswer { expectedResponse }

            // When
            Mockito.lenient().`when`(checkInternetConnection.isNetworkAvailable(context))
                .thenReturn(true).thenAnswer { expected }

            // Then
            assertEquals(stubbedResponse, expectedResponse.first())
        }

    @Test
    fun `if internet connection return false then return error`() =
        kotlinx.coroutines.test.runTest {
            // Given
            val stubbedResponse = NetworkResource.Error("",null, statusCode = 503)
            val expectedResponse = flowOf(stubbedResponse)
            val expected = Mockito.lenient().`when`(currencyRepository.convertCurrency("USD", 1.0, "EGY"))
                .thenAnswer { expectedResponse }

            // When
            Mockito.lenient().`when`(checkInternetConnection.isNetworkAvailable(context))
                .thenReturn(false).thenAnswer { expected }

            // Then
            assertEquals(stubbedResponse, expectedResponse.first())
        }
}