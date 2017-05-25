package com.gisroad.sign.util;
/**
 * Created by stevefat on 17-5-11.
 */

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gisroad.sign.module.WelcomeModel;
import com.gisroad.sign.presenter.WelcomePresenter;
import com.gisroad.sign.ui.activity.WelcomeActivity;

/**
 * Author : stevefat
 * Email :ngh8897@gmail.com
 * Created : 17-5-11 下午4:14
 */
public class PeopleUtils {
    Context context;

    public PeopleUtils(Context context) {
        this.context = context;
    }

    public void getPeople() {
        WebView webView = new WebView(context);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JavaInterface(), "client");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                view.loadUrl("javascript:window.client.showSource('<head>'+"
                        + "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
            }
        });

        webView.loadUrl("http://221.13.129.100:9090/kqcx/Default.aspx");

    }

    class JavaInterface {
        @JavascriptInterface
        public void showSource(String result) {
            Log.e("showSource", result);
            //这里调用一下解析的接口，存储数据到数据库
            new WelcomeModel().saxHtml(result);
        }
    }

}
