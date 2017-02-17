package com.tldn1.ssporgrs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ZoomControls;

public class MainActivity extends AppCompatActivity {
    WebView webView;
    ZoomControls zoomControls;
    ImageView imageView;
    AppCompatActivity activity;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        imageView = (ImageView) findViewById(R.id.img);
        activity = this;
        webView = (WebView) findViewById(R.id.webView);
        webView.setInitialScale(getScale(Double.parseDouble("10")));
        webView.getSettings().setJavaScriptEnabled(true);

        zoomControls = (ZoomControls) findViewById(R.id.zoomctrl);

        webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setPadding(0, 0, 0, 0);


        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                activity.setTitle("Ucitavanje...");
                activity.setProgress(progress * 100);

                if (progress == 100)
                    activity.setTitle(R.string.app_name);
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                // Handle the error
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                zoomControls.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.INVISIBLE);
                getSupportActionBar().show();

            }
        });

        webView.loadUrl("http://ssp.org.rs");
        zoomControls.setOnZoomInClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.zoomIn();
            }
        });
        zoomControls.setOnZoomOutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.zoomOut();
            }
        });
    }

    private int getScale(double numPages) {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenHeight = dm.heightPixels;

        Double val = (new Double(screenHeight - 40) / (new Double(1024)));
        val = val * 100d;
        return val.intValue();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_trough_site, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.back) {
            if (webView.canGoBack()) {
                webView.goBack();
            }
        } else if (item.getItemId() == R.id.forward) {
            if (webView.canGoForward()) {
                webView.goForward();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}