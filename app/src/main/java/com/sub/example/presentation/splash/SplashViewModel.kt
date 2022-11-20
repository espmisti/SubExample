package com.sub.example.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sub.domain.model.CodeModel
import com.sub.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashViewModel(
    private val getFlowUseCase: GetFlowUseCase,
    private val getCodeUseCase: GetCodeUseCase,
    private val getInitUseCase: GetInitUseCase
    ) : ViewModel() {
    // == Init Appsflyer == //
    private val mutableInitAppsflyerSuccessLiveData = MutableLiveData<Boolean>()
    val initAppsflyerSuccessLiveData: LiveData<Boolean> = mutableInitAppsflyerSuccessLiveData
    private val mutableInitAppsflyerFailureLiveData = MutableLiveData<String>()
    val initAppsflyerFailureLiveData: LiveData<String> = mutableInitAppsflyerFailureLiveData
    // == Flow Key == //
    private val mutableFlowKeySuccessLiveData = MutableLiveData<String>()
    val flowKeySuccessLiveData: LiveData<String> = mutableFlowKeySuccessLiveData
    private val mutableFlowKeyFailureLiveData = MutableLiveData<String>()
    val flowKeyFailureLiveData: LiveData<String> = mutableFlowKeyFailureLiveData
    // == Code == //
    private val mutableCodeSuccessLiveData = MutableLiveData<CodeModel>()
    val codeSuccessLiveData: LiveData<CodeModel> = mutableCodeSuccessLiveData
    private val mutableCodeFailureLiveData = MutableLiveData<String>()
    val codeFailureLiveData: LiveData<String> = mutableCodeFailureLiveData

    fun initAppsflyer() = viewModelScope.launch(Dispatchers.IO) {
        val data = getInitUseCase.execute()
        if(data) mutableInitAppsflyerSuccessLiveData.postValue(true)
        else mutableInitAppsflyerFailureLiveData.postValue("Appsflyer initialization error")
    }

    fun getFlow() = viewModelScope.launch(Dispatchers.IO) {
        val data = getFlowUseCase.execute()
        data?.let {
            withContext(Dispatchers.Main) {
                mutableFlowKeySuccessLiveData.postValue(it)
            }
        } ?: run {
            withContext(Dispatchers.Main){
                mutableFlowKeyFailureLiveData.postValue("Error flowkey not found")
            }
        }
    }
    fun getCode(flowkey: String) = viewModelScope.launch(Dispatchers.IO) {
        val data = getCodeUseCase.execute(flowkey = flowkey)
        data?.let {
            withContext(Dispatchers.Main) {
                mutableCodeSuccessLiveData.postValue(it)
            }
        } ?: run {
            withContext(Dispatchers.Main) {
                mutableCodeFailureLiveData.postValue("Error code")
            }
        }
    }
}