package com.scanlibrary.ui.policy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.scanlibrary.R;

public class PolicyActivity extends AppCompatActivity {

    WebView webView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_policy );

        this.webView1 = findViewById(R.id.webView);

        WebSettings webSettings = webView1.getSettings();
        webSettings.setJavaScriptEnabled(true);

        WebViewClientImpl webViewClient = new WebViewClientImpl(this);
        webView1.setWebViewClient(webViewClient);

        webView1.loadUrl("https://elfstudio.yutani.in/scannerpro/privacypolicyscannerpro.html");


        Toolbar toolbar = findViewById( R.id.policy_toolbar );
        toolbar.setNavigationIcon( getResources().getDrawable( R.drawable.back_arrow ) );
        toolbar.setNavigationOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left );

            }
        } );
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView1.canGoBack()) {
            this.webView1.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
