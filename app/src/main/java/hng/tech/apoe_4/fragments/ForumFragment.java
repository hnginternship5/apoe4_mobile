package hng.tech.apoe_4.fragments;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import hng.tech.apoe_4.R;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForumFragment extends Fragment {

    private WebView webView;
    private String Url;
//    private ProgressDialog progressDialog;

    @BindView(R.id.loading)
    MaterialProgressBar progressBar;

    public ForumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_forum, container, false);
        progressBar = getActivity().findViewById(R.id.loading);
        webView = layout.findViewById(R.id.forum_webView);
//        progressDialog = new ProgressDialog(getContext());
        Url = "https://www.alzu.org/forum/list.php?6";
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(Url);
        webView.setWebViewClient(new WebViewClient(){
            //here we show the progressBar when the page start loading
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//                progressDialog.setTitle("Page Loading");
//                progressDialog.setMessage("Here we take you as us...");
                progressBar.setVisibility(View.VISIBLE);
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
                progressBar.setVisibility(View.GONE);
            }
            //here we handle clicks to other pages.
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        return layout;
    }

    public static ForumFragment newInstance() {
        return new ForumFragment();
    }

}
