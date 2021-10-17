package com.db.schoolapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.appcompat.widget.Toolbar;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.db.schoolapp.Constants.StoreDataPrefernce;
import com.db.schoolapp.Constants.Urls;
import com.db.schoolapp.Helpers.Progress_Dialogue;
import com.db.schoolapp.Model.DataResultitem;
import com.db.schoolapp.OtpActivity2;
import com.db.schoolapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity   {
    DatePickerDialog datePickerDialog;
    Toolbar toolbar1;
    TextView toolbar_title;
    String class_id;
    String[] country = {"Select Class", "Highschool(10th)", "Intermidate(12th)", "Other"};
    TextInputEditText name,fathername, address, dob, school, mobile,email,pass, confirmpass,classSpi;
    String names,fathernames,addresss,dobs,schools,mobiles,emails,passs,confirmpasss,classs;
    String class1;
    Button signup;
    private int flag = 1;
    String profession_id;

    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    ImageView back_t;
    private Dialog myDialog;
    private static Progress_Dialogue DialogUtils;
    HashMap<Object, Object> class_map = new HashMap<Object, Object>();
    private  String class_name,class_id1;
    private String username1,fathername1,address1,dob1,school1,mobileno1,emailid1,userid1;
    ArrayList<String> classlistname = new ArrayList<>();
    ArrayList<String> classlistid = new ArrayList<>();
    ListPopupWindow listPopupWindow;
    ArrayList<DataResultitem> classlist = new ArrayList<>();
    ArrayList<String> Professionlistname = new ArrayList<>();
    ArrayList<String> Professionlistid = new ArrayList<>();
    ArrayList<DataResultitem> Professionlist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar1 = (Toolbar) findViewById(R.id.toolbar);
        getSupportActionBar().hide();
        toolbar_title = (TextView) toolbar1.findViewById(R.id.toolbar_title);
        toolbar_title.setText("Update Profile");
        back_t = (ImageView) toolbar1.findViewById(R.id.bkBtn);
        back_t = (ImageView) toolbar1.findViewById(R.id.bkBtn);
        back_t.setVisibility(View.VISIBLE);
        StoreDataPrefernce storeDataPrefernce=new StoreDataPrefernce(ProfileActivity.this);
//        classlistapi();
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
        classSpi= (TextInputEditText) findViewById(R.id.classspi);

        name = (TextInputEditText) findViewById(R.id.nametv);
        fathername = (TextInputEditText) findViewById(R.id.fathernametv);
        address = (TextInputEditText) findViewById(R.id.addresstv);
        dob = (TextInputEditText) findViewById(R.id.dobtv);
        school = (TextInputEditText) findViewById(R.id.scnametv);
        mobile = (TextInputEditText) findViewById(R.id.mobiletv);
        email = (TextInputEditText) findViewById(R.id.emailtv);
        pass = (TextInputEditText) findViewById(R.id.passtv);
        confirmpass = (TextInputEditText) findViewById(R.id.confirmpasstv);
        signup = findViewById(R.id.signup);
        userid1 = storeDataPrefernce.getUserId();
        username1 = storeDataPrefernce.getUsename();
        fathername1 = storeDataPrefernce.getfathername();
        address1 = storeDataPrefernce.getAddress();
        dob1 = storeDataPrefernce.getdob();
        school1 = storeDataPrefernce.getschool();
        mobileno1 = storeDataPrefernce.getMobiles();
        emailid1 = storeDataPrefernce.getEmail();
        userid1 = storeDataPrefernce.getUserId();
        class1=storeDataPrefernce.getclassname();
        Log.e("pragyaclass",class1);

        name.setText(username1);
        fathername.setText(fathername1);
        address.setText(address1);
        dob.setText(dob1);
        school.setText(school1);
        mobile.setText(mobileno1);
        email.setText(emailid1);
        classSpi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_drop_down_profession();
            }
        });
        classSpi.setText(class1);

        classlistapi();
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(ProfileActivity.this, R.style.customdatepicker,
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
//        class1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
////                String prof_name=   adapterView.getItemAtPosition(i).toString();
////                String prof_id= String.valueOf(prof_map.get(prof_name));
////                Toast.makeText(getApplicationContext(),prof_id,Toast.LENGTH_LONG).show();
//                class_name = storeDataPrefernce.getclassname();
//                class_name= adapterView.getItemAtPosition(i).toString();
//                class_id= String.valueOf(class_map.get(class_name));
////                Toast.makeText(getApplicationContext(),kyc_id,Toast.LENGTH_LONG).show();
//
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                // DO Nothing here
//            }
//        });


        //Creating the ArrayAdapter instance having the country list

    }
//
//    @Override
//    public void onItemSelected(AdapterView<?> adapterView, View arg1, int i, long id) {
//        classs  = adapterView.getItemAtPosition(i).toString();
//
//    }
//    @Override
//    public void onNothingSelected(AdapterView<?> arg0) {
//        // TODO Auto-generated method stub
//    }
private void show_drop_down_profession() {
    if (Professionlistname != null && Professionlistname.size() > 0) {
        Professionlistname.clear ( );
        Professionlistid.clear ( );
    }
    listPopupWindow = new ListPopupWindow(ProfileActivity.this);
    for (DataResultitem beans : Professionlist) {
        if (!Professionlistname.contains(beans.getClass_name())) {
            Professionlistname.add(beans.getClass_name());
            Professionlistid.add(beans.getClass_id());
        }
    }
    listPopupWindow.setAdapter(new ArrayAdapter(ProfileActivity.this, android.R.layout.simple_spinner_dropdown_item, Professionlistname));
    listPopupWindow.setAnchorView(classSpi);
    listPopupWindow.setWidth(ListPopupWindow.WRAP_CONTENT);
    listPopupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
    listPopupWindow.setModal(true);
    listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            classSpi.setText(Professionlistname.get(i));
            profession_id = Professionlistid.get(i);
            Log.d("professionId",profession_id);
            listPopupWindow.dismiss();
        }
    });
    listPopupWindow.show();
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
            Toast.makeText(ProfileActivity.this, "Enter Name", Toast.LENGTH_SHORT).show();
            name.requestFocus();

        }else if (fathernames.isEmpty()) {
            Toast.makeText(ProfileActivity.this, "Enter father name", Toast.LENGTH_SHORT).show();
            fathername.requestFocus();
        }
        else if (addresss.isEmpty()) {
            Toast.makeText(ProfileActivity.this, "Enter Permanent Address", Toast.LENGTH_SHORT).show();
            address.requestFocus();

        }
        else if (dobs.isEmpty()) {
            Toast.makeText(ProfileActivity.this, "Enter Date of Birth", Toast.LENGTH_SHORT).show();
            dob.requestFocus();

        }
        else if (schools.isEmpty()) {
            Toast.makeText(ProfileActivity.this, "Enter school/Institute name", Toast.LENGTH_SHORT).show();
            school.requestFocus();

        }
        else  if (classSpi.getText().toString().length()==0) {
            Toast.makeText(ProfileActivity.this, "Please select class", Toast.LENGTH_SHORT).show();
            classSpi.requestFocus();

        }
        else if (mobiles.isEmpty()) {
            Toast.makeText(ProfileActivity.this, "Enter  Mobile Number", Toast.LENGTH_SHORT).show();
            mobile.requestFocus();

        } else if (mobiles.length()!=10) {
            Toast.makeText(ProfileActivity.this, "Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
            mobile.requestFocus();
        }else if (emails.isEmpty()) {
            Toast.makeText(ProfileActivity.this, "Enter Email Address", Toast.LENGTH_SHORT).show();
            email.requestFocus();

        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()) {
            Toast.makeText(ProfileActivity.this, "Enter Valid Email Address", Toast.LENGTH_SHORT).show();
            email.requestFocus();
        }


        else{
            CallUpDateApi();        }
        }
    private void classlistapi() {
        myDialog = DialogUtils.showProgressDialog(ProfileActivity.this, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.URL_CLASSLIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("classlist",response);
                try{
                    myDialog.dismiss();
                    JSONObject obj = new JSONObject(response);
                    String status = obj.getString("status");
                    if(status.equalsIgnoreCase("1")){
                        JSONArray classlistArray = obj.getJSONArray("data_result");
                        for(int i=0;i< classlistArray.length();i++){
                            JSONObject classlist = classlistArray.getJSONObject(i);
                            DataResultitem professiondata = new DataResultitem();
                            professiondata.setClass_id(classlist.getString("class_id"));
                            professiondata.setClass_name(classlist.getString("class_name"));
                            Professionlist.add(professiondata);
                            Professionlist.add(professiondata);



                        }
                    }

//                    ArrayAdapter<String> adapter = new ArrayAdapter<>(ProfileActivity.this, android.R.layout.simple_list_item_1,class_name);
//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    class1.setAdapter(adapter);
//
//                    for (int i=0;i<class_id.size();i++)
//                    {
//                        class_map.put(class_name.get(i),class_id.get(i));
//                    }
                }catch (JSONException e){
                    Toast.makeText(ProfileActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                myDialog.dismiss();
                Toast.makeText(ProfileActivity.this,"failed",Toast.LENGTH_SHORT).show();

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


    private void CallUpDateApi() {
        myDialog = DialogUtils.showProgressDialog(ProfileActivity.this, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.URL_UPDATEPPROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.v("response",response);
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            if (status.equalsIgnoreCase("1")) {
                                myDialog.dismiss();
                                JSONObject jsonObject = obj.getJSONObject("data_result");
//                                DataResult userdata = new DataResult();
//                                userdata.setProfilePic(jsonObject.getString("profile_pic"));
//                                userdata.setUserId(jsonObject.getString("user_id"));
//                                userdata.setUserStatus(jsonObject.getString("user_status"));
//                                userdata.setUsername(jsonObject.getString("username"));
//                                userdata.setMobile(jsonObject.getString("mobile"));
//                                userdata.setAlternateMobile(jsonObject.getString("alternate_mobile"));
//                                userdata.setGender(jsonObject.getString("gender"));
//                                userdata.setEmailid(jsonObject.getString("email"));
//                                userdata.setProfession(jsonObject.getString("profession"));
//                                userdata.setProfessionId(jsonObject.getString("profession_id"));
//                                userdata.setAddress(jsonObject.getString("address"));
//                                userdata.setNearByLocation(jsonObject.getString("near_by_location"));
//                                userdata.setPincodeId(jsonObject.getString("pincode_id"));
//                                userdata.setPincodeNumber(jsonObject.getString("pincode_number"));
//                                userdata.setCityId(jsonObject.getString("city_id"));
//                                userdata.setCityName(jsonObject.getString("city_name"));
//                                userdata.setStateId(jsonObject.getString("state_id"));
//                                userdata.setStateName(jsonObject.getString("state_name"));
//                                userdata.setCountryId(jsonObject.getString("country_id"));
//                                userdata.setCountryName(jsonObject.getString("country_name"));
//                                userdata.setKycTypeId(jsonObject.getString("kyc_type_id"));
//                                userdata.setKycTypeName(jsonObject.getString("kyc_type_name"));
//                                userdata.setKycNumber(jsonObject.getString("kyc_number"));
//                                userdata.setKycstatus(jsonObject.getString("kyc_status"));
//                                userdata.setKycPic(jsonObject.getString("kyc_pic"));
//                                userdata.setRegistrationDate(jsonObject.getString("registration_date"));
//
                                Toast.makeText(ProfileActivity.this, "Update sucessfully ", Toast.LENGTH_SHORT).show();
                                StoreDataPrefernce storeDataPrefernce=new StoreDataPrefernce(ProfileActivity.this);
//                                String userId=userdata.getUserId();
//                                Log.d("userId",userId);
//                                storeDataPrefernce.setUserId(userdata.getUserId());
//                                storeDataPrefernce.setuser_status(userdata.getUserStatus());
//                                storeDataPrefernce.setUsename(userdata.getUsername());
//                                storeDataPrefernce.setMobiles(userdata.getMobile());
//                                String email=userdata.getEmailid();
//                                storeDataPrefernce.setEmail(email);
//                                String gend=userdata.getGender();
//                                storeDataPrefernce.setgender(gend);
//                                Log.d("gen1",gend);
//                                storeDataPrefernce.setcoun(userdata.getCountryName());
//                                String countryname=storeDataPrefernce.getcoun();
//                                Log.d("contryname",countryname);
//                                storeDataPrefernce.setStateName(userdata.getStateName());
//                                storeDataPrefernce.setCityName(userdata.getCityName());
//                                storeDataPrefernce.setPincode(userdata.getPincodeNumber());
//                                storeDataPrefernce.setKycName(userdata.getKycTypeName());
//                                Log.d("email",email);
//                                String kycstatus=userdata.getKycstatus();
//                                String user_img=userdata.getProfilePic();
//                                storeDataPrefernce.setprofile_pic(user_img);
//                                storeDataPrefernce.setkycstatus(kycstatus);
//                                storeDataPrefernce.setProf(userdata.getProfession());
//                                String address=userdata.getAddress();
//                                storeDataPrefernce.setAddress(address);
//                                Log.d("address",address);
//                                String altermobno=userdata.getAlternateMobile();
//                                storeDataPrefernce.setAlternateno(altermobno);
//                                Log.d("alternate",altermobno);
//                                String docno=userdata.getKycNumber();
//                                storeDataPrefernce.setdoc(docno);
//                                Log.d("docno",docno);
//                                storeDataPrefernce.setAlternateno(altermobno);
//                                Log.d("alternate",altermobno);
//                                storeDataPrefernce.setprofession_id(userdata.getProfessionId());
//                                storeDataPrefernce.setnear_by_location(userdata.getNearByLocation());
//                                storeDataPrefernce.setpincode_id(userdata.getPincodeId());
//                                storeDataPrefernce.setcity_id(userdata.getCityId());
//                                storeDataPrefernce.setstate_id(userdata.getStateId());
//                                storeDataPrefernce.setcountry_id(userdata.getCountryId());
//                                storeDataPrefernce.setkyc_type_id_name(userdata.getKycTypeId());
                            } else {
                                myDialog.dismiss();
                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();
                            e.printStackTrace();
                            Toast.makeText(ProfileActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                myDialog.dismiss();
                Toast.makeText(ProfileActivity.this, getString(R.string.NetworkError), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(AppConstants.PICKNDROPKEY, AppConstants.PICKNDROPKEYVALUE);
                Log.v("params",new Gson().toJson(params));

                return params;
            }
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                StoreDataPrefernce storeDataPrefernce=new StoreDataPrefernce(UserProfileActivity.this);
//                String userid=storeDataPrefernce.getUserId();

                params.put("user_id", userid1);
//                params.put("user_id", "70");
                params.put("name",names);
                params.put("father_name", fathernames);
                params.put("address",addresss);
                params.put("dob",dobs);
                params.put("school", schools);
//                Log.v("params",new Gson().toJson(params));
                params.put("class_id", class_id);
                params.put("mobile", mobiles);
                params.put("email", emails);
                Log.v("params",new Gson().toJson(params));
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
