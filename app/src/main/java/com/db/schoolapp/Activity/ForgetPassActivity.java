package com.db.schoolapp.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import com.db.schoolapp.OtpActivity2;
import com.db.schoolapp.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgetPassActivity extends AppCompatActivity {
    Toolbar toolbar1;
    TextView toolbar_title;
    TextInputEditText mobile;
    Button submit;
    String mobile1;
    ImageView back_t;
    String backid="1";
    private Dialog myDialog;
    private static Progress_Dialogue DialogUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        getSupportActionBar().hide();
        toolbar1=(Toolbar)findViewById(R.id.toolbar);
        toolbar_title=(TextView)toolbar1.findViewById(R.id.toolbar_title);
        back_t=(ImageView) toolbar1.findViewById(R.id.bkBtn);
        toolbar_title.setText("Reset Password");
        mobile=findViewById(R.id.mobiletv);
        submit=findViewById(R.id.submit);
        back_t.setVisibility(View.VISIBLE);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.btncolor));
        }
        back_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intilization();
                validations();
            }
        });


    }

    private void intilization() {
        mobile1=mobile.getText().toString();
    }
    private void validations() {
             if (mobile1.isEmpty()) {
            Toast.makeText(ForgetPassActivity.this, "Enter  Mobile Number", Toast.LENGTH_SHORT).show();
            mobile.requestFocus();

        } else if (mobile1.length()!=10) {
            Toast.makeText(ForgetPassActivity.this, "Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
            mobile.requestFocus();

        } else {
//            Intent intent = new Intent(ForgetPassActivity.this, OtpActivity2.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//            finish();
//            overridePendingTransition(R.anim.enter, R.anim.leave);
                 ForgetPasswordApi();



             }
    }
    private void ForgetPasswordApi() {
        myDialog = DialogUtils.showProgressDialog(ForgetPassActivity.this, "Loading Please Wait...");
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.URL_RESET, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("resetresponse",response);
                    JSONObject obj=new JSONObject(response);
                    String status=obj.getString("status");
                    if(status.equals("1"))
                    {
                        myDialog.dismiss();
                        JSONObject object=obj.getJSONObject("data_result");
                        startActivity(new Intent(ForgetPassActivity.this,OtpActivity2.class)
                                .putExtra("Mobile",mobile1));
                    }
                    else{
                        myDialog.dismiss();
                        Toast.makeText(ForgetPassActivity.this, "User Does Not Exist", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    myDialog.dismiss();
                    Toast.makeText(ForgetPassActivity.this, getString(R.string.SomethingWentWrong), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                myDialog.dismiss();
                Toast.makeText(ForgetPassActivity.this, getString(R.string.NetworkError), Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mobile",mobile1);
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
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(ForgetPassActivity.this,LoginActivity.class);
        startActivity(i);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

    }
}