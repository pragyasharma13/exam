package com.db.schoolapp.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.db.schoolapp.Helpers.Progress_Dialogue;
import com.db.schoolapp.R;
import com.google.android.material.snackbar.Snackbar;

public class TermsActivity extends AppCompatActivity  {
    private WebView Wv;
    ProgressDialog pd;
    String url,value;
    PrintJob printJob;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout coordinatorLayout;
    boolean isConnected;
    private static Progress_Dialogue DialogUtils;
    private static final int MY_PERMISSION_REQUEST_CODE = 123;
    // object of print job
    ImageView ivBack;

    // a boolean to check the status of printing
    boolean printBtnPressed = false;
    private  String doctorid,userbookappoinmentid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms2);
        getSupportActionBar().hide();
        ivBack=findViewById(R.id.ivBack1);
        getSupportActionBar().hide();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.btncolor));
        }
        //  toolbar.setLogo(R.drawable.logo);

        Button savePdfBtn = (Button) findViewById(R.id.savePdfBtn);

        String url=("https://www.google.com/");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        savePdfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Wv != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Calling createWebPrintJob()
                        PrintTheWebPage(Wv);
                    } else {
                        // Showing Toast message to user
//                        Toast.makeText(MainActivity.this, "Not available for device below Android LOLLIPOP", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Showing Toast message to user
//                    Toast.makeText(MainActivity.this, "WebPage not fully loaded", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Wv = (WebView) findViewById(R.id.webViewAndroid);
        pd = new ProgressDialog(TermsActivity.this);
        pd.setMessage("Please wait Loading...");
        pd.show();
        WebSettings Ws = Wv.getSettings();
        Ws.setJavaScriptEnabled(true);
        Wv.setWebViewClient(new MainWebViewClient());
        Wv.loadUrl(url);




    }






    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        finish();
        return super.onSupportNavigateUp();

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        finish();

    }


    private class MainWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
           /* Dialog myDialog;
            Progress_Dialogue DialogUtils = null;
            myDialog = DialogUtils.showProgressD0.0ialog(PaymentWebView.this, "Loading Please Wait...");
            myDialog.hide();*/

            //  Log.d("urltest",url);
            //  myDialog = DialogUtils.showProgressDialog(TermsAndPrivacyPolicy.this, "Loading Please Wait...");
            //  myDialog = new ProgressDialog(TermsAndPrivacyPolicy.this);
            view.setVisibility(View.GONE);
            //  pd.setTitle(getString(R.string.loading));
            // pd.show();
            //  pd.setMessage(getString(R.string.pleasewait)/* + url*/);
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            pd.dismiss();
            //   myDialog = DialogUtils.showProgressDialog(TermsAndPrivacyPolicy.this, "Loading Please Wait...");
            //if (myDialog != null) {
            //    myDialog.dismiss();
            //  animate(view);
            //  Log.d("urltest",url);
            view.setVisibility(View.VISIBLE);
            // }

            /*if (myDialog != null) {
                myDialog.dismiss();
                //  animate(view);
                view.setVisibility(View.VISIBLE);
            }*/
            super.onPageFinished(view, url);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN){
            switch(keyCode)
            {
                case KeyEvent.KEYCODE_BACK:

                    if ((keyCode == KeyEvent.KEYCODE_BACK) && Wv.canGoBack()) {
                        Wv.goBack();
                        return true;
                    }

            }

        }
        return super.onKeyDown(keyCode, event);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void PrintTheWebPage(WebView webView) {

        // set printBtnPressed true
        printBtnPressed = true;

        // Creating  PrintManager instance
        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        // setting the name of job
        String jobName = getString(R.string.app_name) + " webpage" + webView.getUrl();

        // Creating  PrintDocumentAdapter instance
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(jobName);
        // Create a print job with name and adapter instance
        assert printManager != null;
        printJob = printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());

    }
}
