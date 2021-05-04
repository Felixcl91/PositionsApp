package com.test.positionsapp.ui.web

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.test.positionsapp.R
import com.test.positionsapp.databinding.FragmentWebBinding
import com.test.positionsapp.utils.autoCleared


class WebFragment : Fragment() {

    companion object {
        fun newInstance() = WebFragment()
        private val TAG = "WEB"
    }
    private var binding: FragmentWebBinding by autoCleared()
    private var webView: WebView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWebBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arg = arguments?.getString("pos_url")
        Log.e(TAG, "URL----$arg")
        webView = view.findViewById(R.id.webView) as WebView
        webView!!.setWebViewClient(WebViewClient())
        webView!!.getSettings().setJavaScriptEnabled(true)
        if (arg != null) {
            webView!!.loadUrl(arg)
        }

    }

}