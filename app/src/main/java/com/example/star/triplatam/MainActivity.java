package com.example.star.triplatam;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.felipecsl.gifimageview.library.GifImageView;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends Activity {

    private WebView webView;

    private GifImageView animationGifView;
    public static final int DIALOG_LOADING = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://pilotoslatam.com");

        animationGifView = (GifImageView) findViewById(R.id.animationGifView);

        webView.setWebViewClient(new WebViewClient()
        {
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                animationGifView.stopAnimation();
                animationGifView.setVisibility(View.INVISIBLE);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
                animationGifView.stopAnimation();
                animationGifView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
                animationGifView.setVisibility(View.VISIBLE);
                try {
                    InputStream inputStream = getAssets().open("animation.gif");
                    byte[] bytes = IOUtils.toByteArray(inputStream);
                    animationGifView.setBytes(bytes);
                    animationGifView.startAnimation();

                } catch (IOException ex) {

                }
            }
        });
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_LOADING:
                final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                //here we set layout of progress dialog
                dialog.setContentView(R.layout.custom_progress_dialog);
                dialog.setCancelable(true);
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        // TODO Auto-generated method stub
                    }
                });
                return dialog;

            default:
                return null;
        }
    };

}
