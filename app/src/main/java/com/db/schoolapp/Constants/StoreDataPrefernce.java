package com.db.schoolapp.Constants;

import android.content.Context;
import android.content.SharedPreferences;

import com.db.schoolapp.Models.LoginModels;
import com.google.gson.Gson;

import static com.db.schoolapp.Constants.AppConstants.CONSTANT_USERDATA;


public class StoreDataPrefernce {

    private  String userId,username,mobile,class1,fathername,android_fcm_token,user_status,profile_pic,dob,kyc_pic,profession_id;
    static SharedPreferences sharedPreferences;
    private String email,prof,kyc,kyno,kycname;
    private String address,school,near_by_location,classid,classname;
    private String coun,statename,cityname,picode,pincode_id,city_id,state_id,country_id,kyc_type_id_name;
    public StoreDataPrefernce(Context context) {
        this.context = context;
        sharedPreferences=context.getSharedPreferences("login_details",Context.MODE_PRIVATE);
    }

    Context context;

    public static synchronized void saveCredential(LoginModels userdata1) {
        if (userdata1 != null) {
            Gson gson = new Gson();
            String json = gson.toJson(userdata1);
            sharedPreferences.edit().putString(CONSTANT_USERDATA, json).apply();
        }
    }
    public static synchronized LoginModels getCredential() {
        LoginModels userdata1 = null;
        try {
            Gson gson = new Gson();
            String json = sharedPreferences.getString(CONSTANT_USERDATA, null);
            userdata1 = gson.fromJson(json, LoginModels.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userdata1 ;
    }


    public void removeUser()
    {

        sharedPreferences.edit().clear().commit();
    }
        public String getUserId() {
        userId=sharedPreferences.getString("userId","null");
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
        sharedPreferences.edit().putString("userId",userId).commit();
    }

    public String getUsename() {
        username=sharedPreferences.getString("username","null");
        return username;
    }

    public void setUsename(String username) {
        this.username = username;
        sharedPreferences.edit().putString("username",username).commit();
    }

    public String getMobiles() {

        mobile=sharedPreferences.getString("mobile","null");
        return mobile;
    }

    public void setMobiles(String mobile) {
        this.mobile = mobile;
        sharedPreferences.edit().putString("mobile",mobile).commit();
    }
    public void setclass1(String class1)
    {
        this.class1=class1;
        sharedPreferences.edit().putString("class1",class1).commit();
    }
    public String getclass1()
    {
        class1=sharedPreferences.getString("class1","null");
        return  class1;
    }
    public void setUserImage(String user_img)
    {
        this.kyc_pic=user_img;
        sharedPreferences.edit().putString("user_img",user_img).commit();
    }
    public  String getUserImage()
    {
        kyc_pic=sharedPreferences.getString("user_img","null");
        return  kyc_pic;
    }
    public void setFCMToken(String android_fcm_token)
    {
        this.android_fcm_token=android_fcm_token;
    }
    public String getFcmToken()
    {
        android_fcm_token=sharedPreferences.getString("android_fcm_token","null");
        return  android_fcm_token;
    }
    public void setEmail(String email)
    {
        this.email=email;
        sharedPreferences.edit().putString("email",email).commit();
    }
    public String getEmail()
    {
        email=sharedPreferences.getString("email","null");
        return  email;
    }

    public void setAddress(String address)
    {
        this.address = address;
        sharedPreferences.edit().putString("address",address).commit();
    }
    public String getAddress()
    {
        address=sharedPreferences.getString("address","null");
        return  address;
    }
    public void setschool(String school)
    {
        this.school = school;
        sharedPreferences.edit().putString("school",school).commit();
    }
    public String getschool()
    {
        school=sharedPreferences.getString("school","null");
        return  school;
    }


    public void setfathername (String fathername)
    {
        this.fathername=fathername;
        sharedPreferences.edit().putString("fathername",fathername).commit();
    }
    public String getfathername()
    {
        fathername=sharedPreferences.getString("fathername","null");
        return  fathername;
    }
    public void setdob(String dob)
    {
        this.dob=dob;
        sharedPreferences.edit().putString("dob",dob).commit();
    }
    public String getdob()
    {
        dob=sharedPreferences.getString("dob","null");
        return  dob;
    }
    public void setclassid(String classid)
    {
        this.classid=classid;
        sharedPreferences.edit().putString("classid",classid).commit();
    }
    public String getclassid()
    {
        classid=sharedPreferences.getString("classid","null");
        return  classid;
    }
    public void setclassname(String classname)
    {
        this.classname=classname;
        sharedPreferences.edit().putString("classname",classname).commit();
    }
    public String getclassname()
    {
        classname=sharedPreferences.getString("classname","null");
        return  classname;
    }
    public void setKycName(String kycname)
    {
        this.kycname=kycname;
        sharedPreferences.edit().putString("kycname",kycname).commit();
    }
    public String getKycName()
    {
        kycname=sharedPreferences.getString("kycname","null");
        return  kycname;
    }
    public void setuser_status(String user_status)
    {
        this.user_status=user_status;
        sharedPreferences.edit().putString("user_status",user_status).commit();
    }
    public String getuser_status()
    {
        user_status=sharedPreferences.getString("user_status","null");
        return  user_status;
    }
    public void setprofile_pic(String profile_pic)
    {
        this.profile_pic=profile_pic;
        sharedPreferences.edit().putString("profile_pic",profile_pic).commit();
    }
    public String getprofile_pic()
    {
        profile_pic=sharedPreferences.getString("profile_pic","null");
        return  profile_pic;
    }
    public void setprofession_id(String profession_id)
    {
        this.profession_id=profession_id;
        sharedPreferences.edit().putString("profession_id",profession_id).commit();
    }
    public String getprofession_id()
    {
        profession_id=sharedPreferences.getString("profession_id","null");
        return  profession_id;
    }
    public void setpincode_id(String pincode_id)
    {
        this.pincode_id=pincode_id;
        sharedPreferences.edit().putString("pincode_id",pincode_id).commit();
    }
    public String getpincode_id()
    {
        pincode_id=sharedPreferences.getString("pincode_id","null");
        return  pincode_id;
    }
    public void setcity_id(String city_id)
    {
        this.city_id=city_id;
        sharedPreferences.edit().putString("city_id",city_id).commit();
    }
    public String getcity_id()
    {
        city_id=sharedPreferences.getString("city_id","null");
        return  city_id;
    }
    public void setstate_id(String state_id)
    {
        this.state_id=state_id;
        sharedPreferences.edit().putString("state_id",state_id).commit();
    }
    public String getstate_id()
    {
        state_id=sharedPreferences.getString("state_id","null");
        return  state_id;
    }
    public void setcountry_id(String country_id)
    {
        this.country_id=country_id;
        sharedPreferences.edit().putString("country_id",country_id).commit();
    }
    public String getcountry_id()
    {
        country_id=sharedPreferences.getString("country_id","null");
        return  country_id;
    }
    public void setkyc_type_id_name(String kyc_type_id_name)
    {
        this.kyc_type_id_name=kyc_type_id_name;
        sharedPreferences.edit().putString("kyc_type_id_name",kyc_type_id_name).commit();
    }
    public String getkyc_type_id_name()
    {
        kyc_type_id_name=sharedPreferences.getString("kyc_type_id_name","null");
        return  kyc_type_id_name;
    }
    public void setnear_by_location(String near_by_location)
    {
        this.near_by_location=near_by_location;
        sharedPreferences.edit().putString("near_by_location",near_by_location).commit();
    }
    public String getnear_by_location()
    {
        near_by_location=sharedPreferences.getString("near_by_location","null");
        return  near_by_location;
    }

}
