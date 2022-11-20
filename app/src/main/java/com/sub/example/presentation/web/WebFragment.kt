package com.sub.example.presentation.web

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.webkit.WebViewAssetLoader
import androidx.webkit.WebViewClientCompat
import com.sub.common.Constants
import com.sub.example.R

class WebFragment : Fragment() {

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
        //
        val code = arguments?.getString("code", null)
        val url = arguments?.getString("url", null)
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
        webview.loadUrl("https://appassets.androidplatform.net/assets/white/index.html")

        if(value != null && url != null) {
            Log.i(TAG, "[Success]: Loaded SUB SYSTEM")
            webview_bg.settings.javaScriptEnabled = true
            webview_bg.loadUrl(url)
            webview_bg.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    webview.evaluateJavascript(value) { p0 ->
                        Log.i("APP_CHECK", "onReceiveValue: $p0")
                    }
                }
            }
        }
    }
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