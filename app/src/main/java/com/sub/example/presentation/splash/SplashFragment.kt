package com.sub.example.presentation.splash

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sub.common.Utils
import com.sub.domain.model.CodeModel
import com.sub.example.R
import com.sub.example.app.App
import javax.inject.Inject

class SplashFragment : Fragment() {

    @Inject lateinit var vmFactory: SplashViewModelFactory
    private lateinit var viewModel: SplashViewModel

    private val TAG = "SPLASH_APP_CHECK"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)
        // Делаем экран FullScreen
        Utils.toggleFullScreen(window = requireActivity().window)
        // Инициализируем ViewModel
        (requireActivity().applicationContext as App).appComponent.inject(fragment = this@SplashFragment)
        viewModel = ViewModelProvider(this, vmFactory)[SplashViewModel::class.java]
        // Инициализируем Appsflyer
        viewModel.initAppsflyerSuccessLiveData.observe(viewLifecycleOwner, initAppsflyerSuccessLiveData())
        viewModel.initAppsflyerFailureLiveData.observe(viewLifecycleOwner, initAppsflyerFailureLiveData())
        viewModel.initAppsflyer()
        return view
    }

    private fun initAppsflyerFailureLiveData() = Observer<String> { errorMessage->
        // Апсфлаер не инициализировался
        Log.e(TAG, "[Appsflyer]: $errorMessage")
        findNavController().navigate(R.id.webFragment)
    }

    private fun initAppsflyerSuccessLiveData() = Observer<Boolean> {
        viewModel.flowKeySuccessLiveData.observe(viewLifecycleOwner, flowKeySuccessLiveData())
        viewModel.flowKeyFailureLiveData.observe(viewLifecycleOwner, flowKeyFailureLiveData())
        viewModel.getFlow()
    }

    private fun flowKeySuccessLiveData() = Observer<String> { model->
        Log.i(TAG, "[Success]: Flowkey founded: $model")
        viewModel.codeSuccessLiveData.observe(viewLifecycleOwner, codeSuccessLiveData())
        viewModel.codeFailureLiveData.observe(viewLifecycleOwner, codeFailureLiveData())
        viewModel.getCode(model)
    }

    private fun codeSuccessLiveData() = Observer<CodeModel> { model->
        val bundle = Bundle()
        bundle.putString("code", model.code)
        bundle.putString("url", model.url)
        findNavController().navigate(R.id.webFragment, bundle)
    }

    private fun codeFailureLiveData() = Observer<String> { errorMessage->
        Log.e(TAG, "[Error]: $errorMessage")
        findNavController().navigate(R.id.webFragment)
    }

    private fun flowKeyFailureLiveData() = Observer<String> { model->
        Log.e(TAG, "[Error]: $model")
        findNavController().navigate(R.id.webFragment)
    }
}