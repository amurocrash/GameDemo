package com.gamesdk.sdkmarketing.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cngame.gamesdklib.mvp.view.BaseView;
import com.cngame.gamesdklib.mvp.view.animator.AnimatorBottomExit;
import com.cngame.gamesdklib.mvp.view.animator.AnimatorTopEnter;
import com.cngame.gamesdklib.mvp.view.animator.BaseAnimatorSet;
import com.cngame.gamesdklib.mvp.view.sdk_dialog.SDKBaseAnimationDialog;
import com.cngame.gamesdklib.utils.ToastUtils;

/**
 * Created by Amuro on 2016/5/12.
 */
public class SDKMarketingMainDialog extends SDKBaseAnimationDialog
{
    public SDKMarketingMainDialog(Context context)
    {
        super(context);
    }

    @Override
    protected BaseAnimatorSet getShowAnim()
    {
        return new AnimatorTopEnter();
    }

    @Override
    protected BaseAnimatorSet getDismissAnim()
    {
        return new AnimatorBottomExit();
    }

    @Override
    protected BaseView onCreateContentView()
    {
        return new SDKMarketingMainView(context);
    }

    private WebView webView;
    private TextView textViewProgresss;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        webView = (WebView)findViewById(SDKMarketingMainView.id_wv);
        textViewProgresss = (TextView)findViewById(SDKMarketingMainView.id_tv_progress);

        initWebView();
    }

    private void initWebView()
    {
        WebSettings settings = webView.getSettings();
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return true;
            }
        });

        webView.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack())
                    {
                        webView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });

        webView.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public void onProgressChanged(WebView view, int newProgress)
            {
                textViewProgresss.setText((newProgress) + "%");
            }
        });

        webView.loadUrl("http://www.baidu.com");
    }

}
