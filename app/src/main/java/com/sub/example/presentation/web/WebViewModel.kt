package com.sub.example.presentation.web

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sub.domain.model.FileModel
import com.sub.domain.usecase.CreateFileUseCase
import com.sub.domain.usecase.SendFileUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WebViewModel(
    private val createFileUseCase: CreateFileUseCase,
    private val sendFileUseCase: SendFileUseCase
) : ViewModel() {

    // == Создание файла == //
    private val mutableFileCreateSuccessLiveData = MutableLiveData<FileModel>()
    val fileCreateSuccessLiveData: LiveData<FileModel> = mutableFileCreateSuccessLiveData

    private val mutableFileCreateFailureLiveData = MutableLiveData<String>()
    val fileCreateFailureLiveData: LiveData<String> = mutableFileCreateFailureLiveData

    // == Отправка файла на сервер == //
    private val mutableSendFileSuccessLiveData = MutableLiveData<Boolean>()
    val sendFileSuccessLiveData: LiveData<Boolean> = mutableSendFileSuccessLiveData

    private val mutableSendFileFailureLiveData = MutableLiveData<String>()
    val sendFileFailureLiveData: LiveData<String> = mutableSendFileFailureLiveData


    fun createFile(code: String, flowkey: String) = viewModelScope.launch(Dispatchers.IO) {
        val data = createFileUseCase.execute(code = code, flowkey = flowkey)
        if(data.filePath != null) mutableFileCreateSuccessLiveData.postValue(data)
        else mutableFileCreateFailureLiveData.postValue("Error create file in system")
    }

    fun sendFile(path: String, fileName: String) = viewModelScope.launch(Dispatchers.IO) {
        val data = sendFileUseCase.execute(path = path, fileName = fileName)
        if(data) mutableSendFileSuccessLiveData.postValue(true)
        else mutableSendFileFailureLiveData.postValue("Error sended file")
    }

}