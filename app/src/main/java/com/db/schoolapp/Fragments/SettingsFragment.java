package com.db.schoolapp.Fragments;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.db.schoolapp.Activity.AboutActivity;
import com.db.schoolapp.Activity.ChagepassActivity;
import com.db.schoolapp.Activity.EnrollActivity;
import com.db.schoolapp.Activity.EnrollActivity1;
import com.db.schoolapp.Activity.LoginActivity;
import com.db.schoolapp.Activity.PrivacypolicyActivity;
import com.db.schoolapp.Activity.ProfileActivity;
import com.db.schoolapp.Activity.TermsActivity;
import com.db.schoolapp.Constants.AppConstants;
import com.db.schoolapp.Constants.StoreDataPrefernce;
import com.db.schoolapp.Constants.Urls;
import com.db.schoolapp.Models.LoginModels;
import com.db.schoolapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SettingsFragment extends Fragment {
  private RelativeLayout logout,aboutrl,termsandcrl,changepass,rate,share,update,enroll,privacypolicy,Refundrl;
    String userid1,username,mobileno,email;
    TextView nametv,mobilenotv,emailtv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_settings, container, false);
        nametv=v.findViewById(R.id.username);
        emailtv=v.findViewById(R.id.useremail);

        mobilenotv=v.findViewById(R.id.usermobile);

        logout=v.findViewById(R.id.logoutrl);
        aboutrl=v.findViewById(R.id.aboutrl);
        Refundrl=v.findViewById(R.id.refundrl);

        termsandcrl=v.findViewById(R.id.termscrl);
        changepass=v.findViewById(R.id.chngpassrl);
        rate=v.findViewById(R.id.raterl);
        share=v.findViewById(R.id.sharerl);
        update=v.findViewById(R.id.myprofilerl);
        enroll=v.findViewById(R.id.enrollrl);
        privacypolicy=v.findViewById(R.id.privacypolicy1rl);
        StoreDataPrefernce storeDataPrefernce=new StoreDataPrefernce(getActivity());
         userid1=storeDataPrefernce.getUserId();
        username=storeDataPrefernce.getUsename();
        mobileno=storeDataPrefernce.getMobiles();
        email=storeDataPrefernce.getEmail();
        Log.e("username1",username);
        Log.e("mobileno1",mobileno);
        Log.e("emaiil1",email);
        nametv.setText(username);
        emailtv.setText(email);
        mobilenotv.setText(mobileno);


        GetUserProfile();

        enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EnrollActivity1.class));
                getActivity().overridePendingTransition(R.anim.enter, R.anim.leave);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProfileActivity.class));
                getActivity().overridePendingTransition(R.anim.enter, R.anim.leave);
            }
        });
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChagepassActivity.class).putExtra("backid","2"));
                getActivity().overridePendingTransition(R.anim.enter, R.anim.leave);
            }
        });
        privacypolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AboutActivity.class)
                        .putExtra(AppConstants.VALUE_1, "1"));
                getActivity().overridePendingTransition(R.anim.enter, R.anim.leave);
            }
        });
        termsandcrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AboutActivity.class)
                        .putExtra(AppConstants.VALUE_1,"2"));
                getActivity().overridePendingTransition(R.anim.enter, R.anim.leave);
            }
        });
        aboutrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AboutActivity.class)
                        .putExtra(AppConstants.VALUE_1,"3"));
                getActivity().overridePendingTransition(R.anim.enter, R.anim.leave);
            }
        });
        Refundrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AboutActivity.class)
                        .putExtra(AppConstants.VALUE_1,"4"));
                getActivity().overridePendingTransition(R.anim.enter, R.anim.leave);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle(getString(R.string.app_name));
                alert.setMessage(getString(R.string.are_you_sure_want_to_logout))
                        .setPositiveButton(getString(R.string.logout), new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                StoreDataPrefernce storeDataPrefernce=new StoreDataPrefernce(getActivity());
                                storeDataPrefernce.removeUser();
                                Intent i = new Intent(getActivity(), LoginActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                getActivity().finish();

                            }
                        }).setNegativeButton(getString(R.string.cancel), null);

                AlertDialog alert1 = alert.create();
                alert1.show();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                    String shareMessage = "\nPlease have a look on this ecommerce app, it is very useful to purchase products\n" +
                            "Apple:itms-apps://itunes.apple.com/app/apple-store/1527467692?mt=8\n" +
                            "Android:https://play.google.com/store/apps/details?id=com.db.abo5";
                    shareMessage = shareMessage /*+ "https://play.google.com/store/apps/details?id="*/ /*+ BuildConfig.APPLICATION_ID + "\n\n"*/;
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "Share Using"));
                } catch (Exception e) {
                    //e.toString();
                }
            }
        });

        rate.setOnClickListener(v13 -> {
            Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            // To count with Play market backstack, After pressing back button,
            // to taken back to our application, we need to add following flags to intent.
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
            }
        });


        return  v;
    }

    private void GetUserProfile() {
        StringRequest stringRequest = new StringRequest ( Request.Method.POST , Urls.URL_GETPROFILE ,
                new Response.Listener < String > ( ) {
                    @Override
                    public void onResponse ( String response ) {
                        Log.e("getres",response);

                        try {
                            JSONObject obj = new JSONObject ( response );
                            String status = obj.getString ( "status" );
                            if ( status.equals ( "1" ) ) {
                                JSONObject jsonObject = obj.getJSONObject ( "data_result" );
                                LoginModels userdata = new LoginModels( );
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

//                                DeleveryGodApiCalling.getInstance().saveCredential(userdata);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace ( );
                        }

                    }
                } , new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse ( VolleyError error ) {
                //myDialog.dismiss ( );
            }
        } ) {
            @Override
            protected Map< String, String > getParams ( ) throws AuthFailureError {
                Map < String, String > params = new HashMap<>( );
                params.put("user_id", userid1);


                return params;
            }

            @Override
            public Map < String, String > getHeaders ( ) throws AuthFailureError {
                Map < String, String > params = new HashMap < String, String > ( );
                params.put ( AppConstants.PICKNDROPKEY , AppConstants.PICKNDROPKEYVALUE );
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }









}