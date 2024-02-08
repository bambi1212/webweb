package com.example.webview;
//
import androidx.appcompat.app.AppCompatActivity;
//
import android.os.Bundle;
//
//import android.view.View;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//
//
//import java.io.DataOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//
//public class MainActivity extends AppCompatActivity {
//    private WebView webView;
//    private String Url;
//    private String targetUrl;
//    private String imagePath;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Url = "https://www.trashai.org/uploads/0";
//
//        webView = (WebView) findViewById(R.id.webview);
//        webView.setWebViewClient(new WebViewClient());
//        webView.loadUrl(Url);
//
//
//        WebSettings settings = webView.getSettings();
//        settings.setJavaScriptEnabled(true);
//
//
//        targetUrl = "https://trashai.org/uploads/0";
//        imagePath = "file:///android_asset/bottrash.jpg";
//        // CHAT
//
//
//    }
//
//
//    public void onClick(View view) throws IOException {
//        File file = new File(imagePath);
//        URL url = new URL(targetUrl);
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//        connection.setDoOutput(true);
//        connection.setRequestMethod("POST");
//
//        try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
//             FileInputStream fileInputStream = new FileInputStream(file)) {
//
//            byte[] buffer = new byte[4096];
//            int bytesRead;
//
//            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
//                outputStream.write(buffer, 0, bytesRead);
//            }
//
//            int responseCode = connection.getResponseCode();
//            System.out.println("Server response code: " + responseCode);
//        } finally {
//            connection.disconnect();
//        }
////        webView.loadDataWithBaseURL("file:///android_asset/", "<img src='bottrash.jpg' />", null, "utf-8", Url);
//
//    }
//}


//public class ImageUploader {
//
//    public static void main(String[] args) {
//
//    }
//
//    private static void uploadImage(String targetUrl, String imagePath) throws IOException {
//
//    }
//}

import android.annotation.SuppressLint;
        import android.app.Activity;
        import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.webkit.ValueCallback;
        import android.webkit.WebChromeClient;
        import android.webkit.WebResourceRequest;
        import android.webkit.WebSettings;
        import android.webkit.WebView;
        import android.webkit.WebViewClient;

public class MainActivity extends Activity {

    private WebView webView;
    private ValueCallback<Uri[]> mUploadMessage;
    private final static int FILECHOOSER_RESULTCODE = 1;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                if (mUploadMessage != null) {
                    mUploadMessage.onReceiveValue(null);
                }
                mUploadMessage = filePathCallback;

                Intent intent = fileChooserParams.createIntent();
                startActivityForResult(intent, FILECHOOSER_RESULTCODE);

                return true;
            }
        });

        // Load the target URL
        webView.loadUrl("https://trashai.org/uploads/0");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (result != null) {
                mUploadMessage.onReceiveValue(new Uri[]{result});
            } else {
                mUploadMessage.onReceiveValue(null);
            }
            mUploadMessage = null;
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}