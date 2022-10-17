package com.example.findadoc.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findadoc.MainActivity;
import com.example.findadoc.R;

public class Web_View_Activity extends AppCompatActivity {
    WebView webView;
    ProgressDialog pd;
    SharedPreferences sharedPreferences;
    String key;
    private String url;
    RelativeLayout relatice_back_change_password;
    private String user_id="";
    TextView name;
    private String language="en";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web__view_);
        webView=findViewById(R.id.web_id);
        name=findViewById(R.id.name);
        sharedPreferences= getApplicationContext().getSharedPreferences("LANGUAGE_NAME", Context.MODE_PRIVATE);
        language=sharedPreferences.getString("language_text","en");
        relatice_back_change_password=findViewById(R.id.relatice_back_change_password);
        key=getIntent().getStringExtra("key");
        relatice_back_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(key.equals("1"))
        {
           // url="http://itdevelopmentservices.com/finddoctor/front/Terms&Condition?lang="+language+"";
            url="https://itdevelopmentservices.com/finddoctor/front/terms_conditions?key=MQ==&lang="+language+"";
            name.setText(getResources().getString(R.string.terms_and_conditions));
        }
        else if (key.equals("2"))
        {
           // url="http://itdevelopmentservices.com/finddoctor/front/Privacy?lang="+language+"";
            url="https://itdevelopmentservices.com/finddoctor/front/privacy_policy_web?key=MQ==&lang="+language+"";
            name.setText(getResources().getString(R.string.privacy_policies));
        }
        else if (key.equals("4"))
        {
            url="http://itdevelopmentservices.com/finddoctor/front/legalNotice?lang="+language+"";
            name.setText(getResources().getString(R.string.help_and_legal_notice));
        }

        else
        {
            url="http://itdevelopmentservices.com/finddoctor/front/aboutus?lang="+language+"";
            name.setText(getResources().getString(R.string.about));
        }

        Log.e("DATHS",url);
        pd = new ProgressDialog(Web_View_Activity.this);
        pd.setMessage("Please wait Loading...");
        pd.show();
        webView.setWebViewClient(new myWebClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setDomStorageEnabled(true);
        webView.clearCache(true);
        webView.clearHistory();
        webView.loadUrl(url);
        webView.setHorizontalScrollBarEnabled(false);
    }
    public class myWebClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setSupportZoom(true);
            pd.show();

            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            if (!pd.isShowing()) {
                pd.show();
            }

            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {

            super.onPageFinished(view, url);
            pd.dismiss();
            Log.e("Dataaa",url);
            if(url.contains("success"))
            {
                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Toast.makeText(Web_View_Activity.this, "Appointment Booking Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
//                        intent.putExtra("user_type",user_type);
                        startActivity(intent);


                    }
                }, 3000);


            }


        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}