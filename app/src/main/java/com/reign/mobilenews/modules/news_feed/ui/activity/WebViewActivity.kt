package com.reign.mobilenews.modules.news_feed.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.reign.mobilenews.R
import kotlinx.android.synthetic.main.activity_webvew.*

fun Context.WebViewActivityIntent(storyUrl: String?): Intent {
    return Intent(this, WebViewActivity::class.java).apply {
        putExtra("storyUrl", storyUrl)
    }
}

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webvew)

        val storyUrl = intent.getStringExtra("storyUrl")

        if (storyUrl != null) {
            //webview.settings.javaScriptEnabled = true
            webview.loadUrl(storyUrl)
        }
    }

    fun back(view: View) {
        super.onBackPressed()
    }

}