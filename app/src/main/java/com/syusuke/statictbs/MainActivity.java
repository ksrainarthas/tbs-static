package com.syusuke.statictbs;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.syusuke.tbs.WebViewUtils;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.X5CoreSdk;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = findViewById(R.id.webview);
        WebViewUtils.getInstance(this).initWebSettings(webView);
        webView.loadUrl("https://www.baidu.com");

        Log.d("rain", X5CoreSdk.getTbsSDKVersion() + "");
        Log.d("rain", X5CoreSdk.getTbsCoreVersion(this) + "");
    }
}