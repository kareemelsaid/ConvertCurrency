package com.example.currency.viewModel

import android.content.Context
import com.example.currency.dataLayer.model.ConvertCurrencyResponse
import com.example.currency.dataLayer.model.Currencies
import com.example.currency.dataLayer.model.CurrencyResponse
import com.example.currency.domanLayer.useCase.ConvertCurrencyUseCaseInterface
import com.example.currency.domanLayer.useCase.CurrencyUseCaseInterface
import com.example.currency.networking.common.ErrorResponseHandlerInterface
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
class CurrencyViewModelTest : TestCase() {

    private lateinit var sut: CurrencyViewModel
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()
    @Mock
    private lateinit var currencyUseCase: CurrencyUseCaseInterface
    @Mock
    private lateinit var convertCurrencyUseCase: ConvertCurrencyUseCaseInterface
    @Mock
    private lateinit var errorResponseHandler: ErrorResponseHandlerInterface
    @Mock
    private lateinit var context: Context

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        sut = CurrencyViewModel(currencyUseCase, convertCurrencyUseCase, errorResponseHandler)
    }

    @Test
    fun `if get currency successfully then emit success state`() = kotlinx.coroutines.test.runTest {
//        // Given
//        val mockResponse = NetworkResource.Success(CurrencyResponse(Currencies(), true))
//        Mockito.lenient().`when`(
//            currencyUseCase.invoke(context)
//        ).thenAnswer { flowOf(mockResponse) }
//        // When
//        sut.getCurrency(context)
//        val actualResponse = sut.getCurrencyState.first()
//        // Then
//        assertEquals(mockResponse, actualResponse)
    }

    @Test
    fun `if get currency failure then emit fail state`() = kotlinx.coroutines.test.runTest {
//        // Given
//        val message = errorResponseHandler.handleDefaultError(500)
//        val mockResponse = NetworkResource.Error(message, null, 500)
//        Mockito.lenient().`when`(
//            currencyUseCase.invoke(context)
//        ).thenAnswer { flowOf(mockResponse) }
//        // When
//        sut.getCurrency(context)
//        val actualResponse = sut.getCurrencyState.first()
//        // Then
//        assertEquals(mockResponse, actualResponse)
    }

    @Test
    fun `if convert currency successfully then emit success state`() =
        kotlinx.coroutines.test.runTest {
            // Given
            val mockResponse = NetworkResource.Success(ConvertCurrencyResponse(0.0, false))
            Mockito.lenient().`when`(
                convertCurrencyUseCase.invoke(
                    context,
                    "",
                    0.0,
                    ""
                )
            ).thenAnswer { flowOf(mockResponse) }
            // When
            sut.convertCurrency(context, "", 0.0, "")
            val actualResponse = sut.convertCurrencyState.first()
            // Then
            assertEquals(mockResponse, actualResponse)
        }

    @Test
    fun `if convert currency failure then emit fail state`() = kotlinx.coroutines.test.runTest {
        // Given
        val message = errorResponseHandler.handleDefaultError(500)
        val mockResponse = NetworkResource.Error(message = message, data = null, statusCode = 500)
        Mockito.lenient().`when`(
            convertCurrencyUseCase.invoke(
                context,
                "",
                0.0,
                ""
            )
        ).thenAnswer {
            flowOf(mockResponse)
        }
        // When
        sut.convertCurrency(context, "", 0.0, "")
        val actualResponse = sut.convertCurrencyState.first()
        // Then
        assertEquals(mockResponse, actualResponse)
    }
}