package com.db.schoolapp.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.db.schoolapp.Constants.StoreDataPrefernce;
import com.db.schoolapp.Constants.Urls;
import com.db.schoolapp.Helpers.Progress_Dialogue;
import com.db.schoolapp.Models.LoginModels;
import com.db.schoolapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    Button login,signup;
    LinearLayout forget_layout;
    Toolbar toolbar1;
    TextView toolbar_title;
    ImageView back_t;
    private Dialog myDialog;
    private static Progress_Dialogue DialogUtils;
    TextInputEditText email,mobile,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        toolbar1=(Toolbar)findViewById(R.id.toolbar);
        toolbar_title=(TextView)toolbar1.findViewById(R.id.toolbar_title);
        toolbar_title.setText("Log In");
        back_t=(ImageView) toolbar1.findViewById(R.id.bkBtn);
        back_t.setVisibility(View.GONE);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.btncolor));
        }
        back_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        toolbar_title.setText("Login");
        mobile=(TextInputEditText) findViewById(R.id.mobiletv);
        password=(TextInputEditText) findViewById(R.id.passtv);
        forget_layout=(LinearLayout) findViewById(R.id.forgetLayout);
        signup=(Button) findViewById(R.id.signup);
        login=(Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   if(mobile.getText().toString().length()==0) {
                    Toast.makeText(LoginActivity.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                    mobile.requestFocus();
                }
                   else if(mobile.getText().toString().length()!=10) {
                       Toast.makeText(LoginActivity.this, "Enter valid Mobile Number", Toast.LENGTH_SHORT).show();
                       mobile.requestFocus();
                   }
               else if(password.getText().toString().length()==0) {
                    Toast.makeText(LoginActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    password.requestFocus();
                }

                else if(password.getText().toString().length()<4) {
                    Toast.makeText(LoginActivity.this, "Enter Password Should be more then 4 digit", Toast.LENGTH_SHORT).show();
                    password.requestFocus();
                }
                else {
//                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                    finish();
//                    overridePendingTransition(R.anim.enter, R.anim.leave);
                       LoginApiCall();

                }
            }
        });
        forget_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,  ForgetPassActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.enter, R.anim.leave);

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,  RegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim.leave);

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

    }
    private void LoginApiCall() {
        myDialog = DialogUtils.showProgressDialog(LoginActivity.this, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.URL_lOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("response",response);
                    JSONObject obj = new JSONObject(response);
                    String status = obj.getString("status");
                    if (status.equalsIgnoreCase("1")) {
                        myDialog.dismiss();
                        JSONObject jsonObject = obj.getJSONObject("data_result");
                        LoginModels userdata = new LoginModels();
                        userdata.setUser_id(jsonObject.getString("user_id"));
                        userdata.setName(jsonObject.getString("name"));
                        userdata.setStatus(jsonObject.getString("status"));
                        userdata.setMobile(jsonObject.getString("mobile"));
                        userdata.setFather_name(jsonObject.getString("father_name"));
                        userdata.setAddress(jsonObject.getString("address"));
                        userdata.setDob(jsonObject.getString("dob"));
                        userdata.setSchool(jsonObject.getString("school"));
                        userdata.setClass_id(jsonObject.getString("class_id"));
                        userdata.setClass_name(jsonObject.getString("class_name"));
                        userdata.setEmail(jsonObject.getString("email"));

                        StoreDataPrefernce storeDataPrefernce=new StoreDataPrefernce(LoginActivity.this);
                        String userId=userdata.getUser_id();
                        String name=userdata.getName();
                        String status1=userdata.getStatus();
                        String mobile=userdata.getMobile();
                        String address=userdata.getAddress();
                        String class1=userdata.getClass_name();
                        String email=userdata.getEmail();
                        String school=userdata.getSchool();
                        String dob=userdata.getDob();
                        String fathername=userdata.getFather_name();
                        String classid=userdata.getClass_id();
                        String class_name=userdata.getClass_name();


                        storeDataPrefernce.setUserId(userdata.getUser_id());
                        Log.d("userId",userId);
                        storeDataPrefernce.setUsename(userdata.getName());
                        Log.d("name",name);
                        storeDataPrefernce.setuser_status(userdata.getStatus());
                        Log.d("status1",status1);
                        storeDataPrefernce.setMobiles(userdata.getMobile());
                        Log.d("mobile",mobile);
                        storeDataPrefernce.setAddress(userdata.getAddress());
                        Log.d("address",address);
                        storeDataPrefernce.setclass1(userdata.getClass_name());
                        Log.d("class1",class1);
                        storeDataPrefernce.setEmail(userdata.getEmail());
                        Log.d("email",email);
                        storeDataPrefernce.setschool(userdata.getSchool());
                        Log.d("school",school);
                        storeDataPrefernce.setdob(userdata.getDob());
                        storeDataPrefernce.setfathername(userdata.getFather_name());
                        storeDataPrefernce.setclassid(userdata.getClass_id());
                        storeDataPrefernce.setclassname(userdata.getClass_name());
                        Log.d("clsass",class_name);

                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));


                    } else {
                        myDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "invalid Credentials", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    myDialog.dismiss();
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, getString(R.string.NetworkError), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                 myDialog.dismiss();
                Toast.makeText(LoginActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mobile",mobile.getText().toString().trim());
                params.put("password", password.getText().toString().trim());

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


}