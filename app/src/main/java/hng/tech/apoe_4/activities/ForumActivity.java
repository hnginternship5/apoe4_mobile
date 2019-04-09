package hng.tech.apoe_4.activities;

import androidx.appcompat.app.AppCompatActivity;
import hng.tech.apoe_4.R;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ForumActivity extends AppCompatActivity {
    private WebView webView;
    private String Url;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        webView = findViewById(R.id.forum_webView);
        progressDialog = new ProgressDialog(this);
        Url = "google.com";
        WebSettings  webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(Url);
        webView.setWebViewClient(new WebViewClient(){
//here we show the progressBar when the page start loading
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressDialog.setTitle("Page Loading");
                progressDialog.setMessage("Here we take you as us...");
                progressDialog.show();
            }
//here we handled a custom error page whenever there is any error
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                if(errorCode <= 100){
                    webView.loadUrl("file:///android_asset/www/error.html");
                }
            }
//here we handled if the page has finished loading
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressDialog.dismiss();
            }
//here we handle clicks to other pages.
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}
