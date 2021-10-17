package com.db.schoolapp.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import com.db.schoolapp.OtpActivity2;
import com.db.schoolapp.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DatePickerDialog datePickerDialog;
    Toolbar toolbar1;
    TextView toolbar_title;
    String[] country = {"Select Class", "Highschool(10th)", "Intermidate(12th)", "Other"};
    TextInputEditText name,fathername, address, dob, school, mobile,email,pass, confirmpass;
    String names,fathernames,addresss,dobs,schools,mobiles,emails,passs,confirmpasss,classs;
    Spinner class1;
    Button signup;
    private int flag = 1;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    private String class_id,class_name;
    ImageView back_t;
    RelativeLayout rel_lay_signUp,rel_layout_otp;
    private Dialog myDialog;
    private static Progress_Dialogue DialogUtils;
    HashMap<Object, Object> class_map = new HashMap<Object, Object>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        rel_lay_signUp=(RelativeLayout) findViewById(R.id.rel_lay_signup);
        rel_layout_otp=(RelativeLayout) findViewById(R.id.rel_layout_otp);

        toolbar1 = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) toolbar1.findViewById(R.id.toolbar_title);
        toolbar_title.setText("Sign Up");
        back_t=(ImageView) toolbar1.findViewById(R.id.bkBtn);
        back_t=(ImageView) toolbar1.findViewById(R.id.bkBtn);
        back_t.setVisibility(View.VISIBLE);
        classlistapi();
        back_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.btncolor));
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        name=(TextInputEditText) findViewById(R.id.nametv);
        fathername=(TextInputEditText) findViewById(R.id.fathernametv);
        address=(TextInputEditText) findViewById(R.id.addresstv);
        dob=(TextInputEditText) findViewById(R.id.dobtv);
        school=(TextInputEditText) findViewById(R.id.scnametv);
        mobile=(TextInputEditText) findViewById(R.id.mobiletv);
        email=(TextInputEditText) findViewById(R.id.emailtv);
        pass=(TextInputEditText) findViewById(R.id.passtv);
        confirmpass=(TextInputEditText) findViewById(R.id.confirmpasstv);
        class1 = (Spinner) findViewById(R.id.classspi);
        signup =findViewById(R.id.signup);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(RegisterActivity.this,R.style.customdatepicker,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                dob.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intialization();
                validations();
            }
        });
        class1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String prof_name=   adapterView.getItemAtPosition(i).toString();
//                String prof_id= String.valueOf(prof_map.get(prof_name));
//                Toast.makeText(getApplicationContext(),prof_id,Toast.LENGTH_LONG).show();
                class_name= adapterView.getItemAtPosition(i).toString();
                class_id= String.valueOf(class_map.get(class_name));
//                Toast.makeText(getApplicationContext(),kyc_id,Toast.LENGTH_LONG).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });


        //Creating the ArrayAdapter instance having the country list

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View arg1, int i, long id) {
        classs  = adapterView.getItemAtPosition(i).toString();

    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    public  void intialization(){
        names=name.getText().toString().trim();
        fathernames=fathername.getText().toString().trim();
        addresss=address.getText().toString().trim();
        dobs=dob.getText().toString().trim();
        schools=school.getText().toString().trim();
        mobiles=mobile.getText().toString().trim();
        emails=email.getText().toString().trim();
        passs=pass.getText().toString().trim();
        confirmpasss=confirmpass.getText().toString().trim();


    }
    public void validations(){
        if (names.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Enter Name", Toast.LENGTH_SHORT).show();
            name.requestFocus();

        }else if (fathernames.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Enter father name", Toast.LENGTH_SHORT).show();
            fathername.requestFocus();
        }
        else if (addresss.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Enter Permanent Address", Toast.LENGTH_SHORT).show();
            address.requestFocus();

        }
        else if (dobs.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Enter Date of Birth", Toast.LENGTH_SHORT).show();
            dob.requestFocus();

        }
        else if (schools.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Enter school/Institute name", Toast.LENGTH_SHORT).show();
            school.requestFocus();

        }
        else  if (class_name.equals("Select class")) {
            Toast.makeText(RegisterActivity.this, "Please select class", Toast.LENGTH_SHORT).show();
            class1.requestFocus();

        }
        else if (mobiles.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Enter  Mobile Number", Toast.LENGTH_SHORT).show();
            mobile.requestFocus();

        } else if (mobiles.length()!=10) {
            Toast.makeText(RegisterActivity.this, "Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
            mobile.requestFocus();
        }else if (emails.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Enter Email Address", Toast.LENGTH_SHORT).show();
            email.requestFocus();

        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()) {
            Toast.makeText(RegisterActivity.this, "Enter Valid Email Address", Toast.LENGTH_SHORT).show();
            email.requestFocus();
        }
        else if (passs.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
            pass.requestFocus();

        } else if (passs.length() < 4) {
            Toast.makeText(RegisterActivity.this, "Enter Password Should be more then 4 digit", Toast.LENGTH_SHORT).show();
            pass.requestFocus();

        }
        else if (confirmpasss.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
            confirmpass.requestFocus();

        } else if (confirmpasss.length() < 4) {
            Toast.makeText(RegisterActivity.this, "Enter Confirm Password Should be more then 4 digit", Toast.LENGTH_SHORT).show();
            confirmpass.requestFocus();

        }
        else if (!passs.equals(confirmpasss)) {
            Toast.makeText(RegisterActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();


        } else {
//            Intent intent = new Intent(RegisterActivity.this, OtpActivity2.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//            finish();
            ForgetPasswordApi();
            //sendVerificationCode(Mobilenumber);
        }


    }
    private void classlistapi() {
        myDialog = DialogUtils.showProgressDialog(RegisterActivity.this, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.URL_CLASSLIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("classlist",response);
                try{
                    myDialog.dismiss();
                    JSONObject obj = new JSONObject(response);
                    String status = obj.getString("status");
                    ArrayList class_id= new ArrayList<String>();
                    ArrayList class_name = new ArrayList<String>();
                    class_name.add("Select class");
                    class_id.add("-1");

                    if(status.equalsIgnoreCase("1")){
                        JSONArray classlistArray = obj.getJSONArray("data_result");
                        for(int i=0;i< classlistArray.length();i++){
                            JSONObject classlist = classlistArray.getJSONObject(i);

                            class_id.add(classlist.getString("class_id"));
                            class_name.add(classlist.getString("class_name"));


                        }
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_list_item_1,class_name);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    class1.setAdapter(adapter);

                    for (int i=0;i<class_id.size();i++)
                    {
                        class_map.put(class_name.get(i),class_id.get(i));
                    }
                }catch (JSONException e){
                    Toast.makeText(RegisterActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                myDialog.dismiss();
                Toast.makeText(RegisterActivity.this,"failed",Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(AppConstants.PICKNDROPKEY, AppConstants.PICKNDROPKEYVALUE);
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                // params.put("city_id",city_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    private void Registerapi() {
        myDialog = DialogUtils.showProgressDialog(RegisterActivity.this, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.URl_REGISTER, new Response.Listener<String>() {
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

                        StoreDataPrefernce storeDataPrefernce=new StoreDataPrefernce(RegisterActivity.this);
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
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));


                    } else {
                        myDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "invalid Credentials", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    myDialog.dismiss();
                    e.printStackTrace();
                    Toast.makeText(RegisterActivity.this, getString(R.string.NetworkError), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                myDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name",names);
                params.put("father_name",fathernames);
                params.put("address",addresss);
                params.put("dob",dobs );
                params.put("school",schools);
                params.put("class_id",class_id);
                params.put("mobile",mobiles);
                params.put("email",emails);
                params.put("password",confirmpasss );


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
    private void ForgetPasswordApi() {
        myDialog = DialogUtils.showProgressDialog(RegisterActivity.this, "Loading Please Wait...");
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
                        Toast.makeText(RegisterActivity.this, "You are already Registered", Toast.LENGTH_SHORT).show();


                    }
                    else{

                        Registerapi();
                        myDialog.dismiss();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(RegisterActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                    myDialog.dismiss();
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                myDialog.dismiss();
                Toast.makeText(RegisterActivity.this, getString(R.string.NetworkError), Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mobile",mobiles);
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
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

    }


    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }


}
