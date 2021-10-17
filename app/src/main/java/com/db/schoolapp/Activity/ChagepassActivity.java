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
import com.db.schoolapp.Constants.StoreDataPrefernce;
import com.db.schoolapp.Constants.Urls;
import com.db.schoolapp.Helpers.Progress_Dialogue;
import com.db.schoolapp.OtpActivity2;
import com.db.schoolapp.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChagepassActivity extends AppCompatActivity {
private TextInputEditText newpass,confirmpass;
private  Button submit;
  private   Toolbar toolbar1;
 private    TextView toolbar_title;
   private ImageView back_t;
String newpass1,confirmpass1;
    private Dialog myDialog;
    private static Progress_Dialogue DialogUtils;
    String lName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chagepass);
        getSupportActionBar().hide();
        newpass=findViewById(R.id.newpasstv);
        confirmpass=findViewById(R.id.confirmpasstv);
        submit=findViewById(R.id.submit);
        toolbar1=(Toolbar)findViewById(R.id.toolbar);
        toolbar_title=(TextView)toolbar1.findViewById(R.id.toolbar_title);
        toolbar_title.setText("Change Password");
        back_t=(ImageView) toolbar1.findViewById(R.id.bkBtn);
        back_t.setVisibility(View.VISIBLE);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.btncolor));
        }
        Bundle extras = getIntent().getExtras();
        Intent intent = getIntent();
         lName = intent.getStringExtra("backid");
        Log.e("pragya",lName);
        back_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intialization();
                validations();
            }
        });

    }

    private void intialization() {
        newpass1=newpass.getText().toString();
        confirmpass1=confirmpass.getText().toString();

    }
    private void validations(){
            if (newpass1.isEmpty()) {
            Toast.makeText(ChagepassActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
            newpass.requestFocus();

        } else if (newpass1.length() < 4) {
            Toast.makeText(ChagepassActivity.this, "Enter Password Should be more then 4 digit", Toast.LENGTH_SHORT).show();
            newpass.requestFocus();

        }
        else if (confirmpass1.isEmpty()) {
            Toast.makeText(ChagepassActivity.this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();
            confirmpass.requestFocus();

        } else if (confirmpass1.length() < 4) {
            Toast.makeText(ChagepassActivity.this, "Enter Confirm Password Should be more then 4 digit", Toast.LENGTH_SHORT).show();
            confirmpass.requestFocus();

        }
        else if (!newpass1.equals(confirmpass1)) {
            Toast.makeText(ChagepassActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();


        } else {
//                Intent intent = new Intent(ChagepassActivity.this, LoginActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//                finish();

                changepassapi();

            }

    }
    private void changepassapi() {
        myDialog = DialogUtils.showProgressDialog(ChagepassActivity.this, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.URL_CHANGEPASS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("changepass",response);
                try {
                    JSONObject obj = new JSONObject(response);
                    String status = obj.getString("status");
                    if (status.equals("1")) {
                        myDialog.dismiss();
                        Toast.makeText(ChagepassActivity.this, "password change Successfully", Toast.LENGTH_SHORT).show();

                    } else {
                        myDialog.dismiss();
                        Toast.makeText(ChagepassActivity.this, "User Does Not Exist", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    myDialog.dismiss();
                    Toast.makeText(ChagepassActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                myDialog.dismiss();
                Toast.makeText(ChagepassActivity.this, getString(R.string.NetworkError), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                StoreDataPrefernce storeDataPrefernce=new StoreDataPrefernce(ChagepassActivity.this);
                String userid=storeDataPrefernce.getUserId();
                Log.d("changepaassuser",userid);
                params.put("user_id", userid);
                params.put("password", confirmpass1);
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

        if(lName.equals("1"))
        {
            Intent intent = new Intent(ChagepassActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);


        }
        else
        {

            super.onBackPressed();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);


        }
    }
}