package com.ronsoftstudios.governmentjobs;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class JobDetailsActivity extends AppCompatActivity {

    private WebView mWebView;
    private String jobUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        jobUrl = getIntent().getStringExtra("JOB_URL");

        String url = jobUrl;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);

//        mWebView = (WebView)findViewById(R.id.jobDescriptionWebView);
//        mWebView.setWebViewClient(new myWebClient());
//        mWebView.getSettings().setJavaScriptEnabled(true);
//       // mWebView.loadUrl(jobUrl);
//
//        mWebView.setWebViewClient(new WebViewClient() {
//            public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
//                try {
//                    webView.stopLoading();
//                } catch (Exception e) {
//                }
//
//                if (webView.canGoBack()) {
//                    webView.goBack();
//                }
//
//                webView.loadUrl("about:blank");
//                AlertDialog alertDialog = new AlertDialog.Builder(JobDetailsActivity.this).create();
//                alertDialog.setTitle("Error");
//                alertDialog.setMessage("Check your internet connection and try again.");
//                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Try Again", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
//                        startActivity(getIntent());
//                    }
//                });
//
//                alertDialog.show();
//                super.onReceivedError(webView, errorCode, description, failingUrl);
//            }
//        });
    }

    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            return true;

        }
    }




    @Override
    // This method is used to detect back button
    public void onBackPressed() {
        finish();
    }
}
