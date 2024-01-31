package com.example.webview;
    
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private String Url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         Url = "https://www.trashai.org/uploads/0";

        webView = (WebView) findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(Url);


        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

    }


    public void onClick(View view) {
        webView.loadDataWithBaseURL("file:///android_asset/", "<img src='bottrash.jpg' />", null, "utf-8", Url);

    }
}