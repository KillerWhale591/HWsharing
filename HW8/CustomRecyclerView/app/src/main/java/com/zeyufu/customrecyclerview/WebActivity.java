package com.zeyufu.customrecyclerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends AppCompatActivity {

    public static final String KEY_EP_URL = "ep_url";
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        webView = findViewById(R.id.webView);

        Intent i = getIntent();
        String ep = i.getStringExtra(KEY_EP_URL);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(ep);
    }
}
