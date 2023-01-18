package com.example.currency.presentationLayer

import android.app.ProgressDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.currency.R
import com.example.currency.dataLayer.model.Currencies
import com.example.currency.dataLayer.model.CurrencyResponse
import com.example.currency.networking.common.NetworkResource
import com.example.currency.viewModel.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_convert_currency.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties

@AndroidEntryPoint
class ConvertCurrencyFragment : Fragment() {
    private val defaultIndex = 0
    private val viewModel by activityViewModels<CurrencyViewModel>()
    private var toCurrency: String = ""
    private var fromCurrency: String = ""
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_convert_currency, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCurrency(requireContext())
        getCurrency()
        convertCurrency()
        fromCurrencySpinner()
        toCurrencySpinner()
        setCurrencyValueEditText()
        swipe()
    }

    private fun progressLoading() {
        progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Loading, please wait")
        progressDialog.show()
    }

    private fun spinnerAdapter(currencyList: ArrayList<String>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.item_spinner, currencyList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fromSpinner?.adapter = adapter
        toSpinner?.adapter = adapter
    }

    private fun setCurrencyValueEditText() {
        fromEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(fromPrice: CharSequence, start: Int, before: Int, count: Int) {
                if (fromPrice.isNotEmpty()) {
//                    viewModel.convertCurrency(requireContext(),fromCurrency, fromPrice.toString().toDouble(), toCurrency)
                }
            }
        })
    }

    private fun fromCurrencySpinner() {
        fromSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (fromCurrency != parent?.selectedItem.toString()) {
//                    viewModel.convertCurrency(requireContext(),parent?.selectedItem.toString(), fromEditText.text.toString().toDouble(), toCurrency)
                }
                fromCurrency = parent?.selectedItem.toString()
            }
        }
    }

    private fun toCurrencySpinner(){
        toSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (toCurrency != parent?.selectedItem.toString()) {
//                    viewModel.convertCurrency(requireContext(),fromCurrency, fromEditText.text.toString().toDouble(), parent?.selectedItem.toString())
                }
                toCurrency = parent?.selectedItem.toString()
            }
        }
    }

    private fun swipe() {
        swipeImageView.setOnClickListener {
            if (toCurrencyValue.text.isNotEmpty()) {
                val fromSpinnerIndex = fromSpinner.selectedItemPosition
                val toSpinnerIndex = toSpinner.selectedItemPosition
                fromSpinner.setSelection(toSpinnerIndex)
                toSpinner.setSelection(fromSpinnerIndex)
                fromCurrency = fromSpinner.selectedItem.toString()
                toCurrency = toSpinner.selectedItem.toString()
                fromEditText.setText(toCurrencyValue.text)
            }
        }
    }

    private fun getCurrency() {
//        lifecycleScope.launchWhenStarted {
//            viewModel.getCurrencyState.collectLatest { response ->
//                when (response) {
//                    is NetworkResource.Success -> {
//                        getCurrencySuccessData(response)
//                        progressDialog.dismiss()
//                    }
//                    is NetworkResource.Error<*> -> {
//                        Toast.makeText(requireContext(),response.message, Toast.LENGTH_SHORT).show()
//                        progressDialog.dismiss()
//                    }
//                    is NetworkResource.Loading -> {
//                        progressLoading()
//                    }
//                    else -> {}
//                }
//            }
//        }
    }

    private fun convertCurrency() {
        lifecycleScope.launchWhenStarted {
            viewModel.convertCurrencyState.collectLatest { response ->
                when (response) {
                    is NetworkResource.Success -> {
                        toCurrencyValue.text = response.data?.result.toString()
                        progressDialog.dismiss()
                    }
                    is NetworkResource.Error<*> -> {
                        Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                        progressDialog.dismiss()
                    }
                    is NetworkResource.Loading -> {
                        progressLoading()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun getCurrencySuccessData(response:NetworkResource<CurrencyResponse?>){
        val countriesList = ArrayList<String>()
        val currencies = response.data?.currencies
        currencies?.asMap()?.keys?.forEach { countriesList.add(it) }
        spinnerAdapter(countriesList)
        fromCurrency = countriesList[defaultIndex]
        toCurrency = countriesList[defaultIndex]
    }

    private fun Currencies.asMap() = this::class.declaredMemberProperties
        .map {
            it as KProperty1<Currencies, Any?>
            it.name to it.get(this).toString()
        }.toMap()
}