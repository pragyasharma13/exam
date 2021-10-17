package com.db.schoolapp.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.db.schoolapp.Constants.AppConstants;
import com.db.schoolapp.Constants.Urls;
import com.db.schoolapp.Helpers.Progress_Dialogue;
import com.db.schoolapp.Models.ContentModel;
import com.db.schoolapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AboutActivity extends AppCompatActivity {
    private WebView Wv;
    ProgressDialog pd;
    String url, value;
    PrintJob printJob;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout coordinatorLayout;
    boolean isConnected;
    private static Progress_Dialogue DialogUtils;
    ImageView ivBack;
    private Dialog myDialog;

    boolean printBtnPressed = false;
    String WebViewString;
    String toolBarIntent;
    private TextView toolbaar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().hide();
        ivBack = findViewById(R.id.ivBack1);
        toolbaar_title = findViewById(R.id.toolbar_title);
        getSupportActionBar().hide();

        //  toolbar.setLogo(R.drawable.logo);
        Intent intent = getIntent();
        toolBarIntent = intent.getStringExtra(AppConstants.VALUE_1);

        contentapicall();
        if (toolBarIntent.equals("1")) {
            toolbaar_title.setText("Privacy Policy");
        } else if (toolBarIntent.equalsIgnoreCase("2")) {
            toolbaar_title.setText("Term and Conditions");
        } else if (toolBarIntent.equalsIgnoreCase("3")) {
            toolbaar_title.setText("About");
        } else {
            toolbaar_title.setText("Refund");
        }

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.btncolor));
        }
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Wv = (WebView) findViewById(R.id.webViewAndroid);
        pd = new ProgressDialog(AboutActivity.this);
        pd.setMessage("Please wait Loading...");
        pd.show();
        WebSettings Ws = Wv.getSettings();
        Ws.setJavaScriptEnabled(true);
        Wv.setWebViewClient(new MainWebViewClient());
        Wv.loadUrl(url);
    }

    private void contentapicall() {
        myDialog = DialogUtils.showProgressDialog(AboutActivity.this, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.URL_CONTENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("contentresponse", response);
                    JSONObject obj = new JSONObject(response);
                    String status = obj.getString("status");
                    if (status.equalsIgnoreCase("1")) {
                        myDialog.dismiss();
                        JSONObject jsonObject = obj.getJSONObject("data_result");
                        ContentModel userdata = new ContentModel();

                        if (toolBarIntent.equals("1")) {
                            WebViewString = userdata.setAbout(jsonObject.getString("privacy_policy"));
                        } else if (toolBarIntent.equalsIgnoreCase("2")) {
                            WebViewString = userdata.setAbout(jsonObject.getString("terms_and_conditions"));
                        } else if (toolBarIntent.equalsIgnoreCase("3")) {
                            WebViewString = userdata.setAbout(jsonObject.getString("about"));
                        } else {
                            WebViewString = userdata.setAbout(jsonObject.getString("refund_policy"));
                        }

                        WebSettings Ws = Wv.getSettings();
                        Ws.setJavaScriptEnabled(true);
                        Wv.setWebViewClient(new MainWebViewClient());
                        Wv.loadDataWithBaseURL(null, WebViewString, "text/html", "utf-8", null);

                    } else {
                        myDialog.dismiss();
                        Toast.makeText(AboutActivity.this, "invalid Credentials", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    myDialog.dismiss();
                    e.printStackTrace();
                    Toast.makeText(AboutActivity.this, getString(R.string.NetworkError), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                myDialog.dismiss();
                Toast.makeText(AboutActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();


                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(AppConstants.PICKNDROPKEY, AppConstants.PICKNDROPKEYVALUE);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
        public void onPageFinished(WebView view, String url) {
            pd.dismiss();
            view.setVisibility(View.VISIBLE);
            super.onPageFinished(view, url);
        }
    }
}