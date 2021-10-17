package com.db.schoolapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
import com.db.schoolapp.Constants.StoreDataPrefernce;
import com.db.schoolapp.Constants.Urls;
import com.db.schoolapp.Fragments.HomeFragment;
import com.db.schoolapp.Helpers.Progress_Dialogue;
import com.db.schoolapp.Model.Homelist;
import com.db.schoolapp.Models.DataResultModel;
import com.db.schoolapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnrollActivity1 extends AppCompatActivity {
    Button login,signup;
    LinearLayout forget_layout;
    Toolbar toolbar1;
    TextView toolbar_title;
    ImageView back_t;
    RecyclerView recyclerView;
    String userid1;
    List<DataResultModel> dataResultModels=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll1);
        getSupportActionBar().hide();
        toolbar1 = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) toolbar1.findViewById(R.id.toolbar_title);
        back_t = (ImageView) toolbar1.findViewById(R.id.bkBtn);
        recyclerView = (RecyclerView) findViewById(R.id.homelistrv);
        StoreDataPrefernce storeDataPrefernce=new StoreDataPrefernce(EnrollActivity1.this);
        userid1=storeDataPrefernce.getUserId();
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
        toolbar_title.setText("Enroll");
        ExamEnrollApi();
    }
    private void ExamEnrollApi() {
        Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(EnrollActivity1.this, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.URL_MYEXAM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("ENROLL",response);

                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                myDialog.dismiss();
                                JSONArray jsonObject = obj.optJSONArray("data_result");
                                if (dataResultModels != null) {
                                    dataResultModels.clear();
                                }
                                for (int i = 0; i < jsonObject.length(); i++) {
                                    JSONObject qstnArray = jsonObject.getJSONObject(i);
                                    DataResultModel dataResultModel = new DataResultModel();
                                    dataResultModel.setUser_exam_id(qstnArray.getString("user_exam_id"));
                                    dataResultModel.setExam_name(qstnArray.getString("exam_name"));
                                    dataResultModel.setExam_id(qstnArray.getString("exam_id"));
                                    dataResultModel.setExam_price(qstnArray.getString("price"));
                                    dataResultModel.setClass_name(qstnArray.getString("class_name"));
                                    dataResultModel.setRegistered_on(qstnArray.getString("registered_on"));
                                    dataResultModel.setExam_desc(qstnArray.getString("exam_desc"));




                                    dataResultModels.add(dataResultModel);
                                }
                                MyListAdapter myListAdapter = new MyListAdapter(EnrollActivity1.this,dataResultModels);
                                recyclerView.setLayoutManager(new LinearLayoutManager(EnrollActivity1.this));
                                recyclerView.setAdapter(myListAdapter);
                            } else {
                                myDialog.dismiss();
                                if (dataResultModels != null) {
                                    dataResultModels.clear();
                                    Toast.makeText(EnrollActivity1.this, "No Data found", Toast.LENGTH_SHORT).show();

                                }
                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();
                            Toast.makeText(EnrollActivity1.this ,getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();
                        Toast.makeText(EnrollActivity1.this, getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id","1");

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


    public class MyListAdapter extends RecyclerView.Adapter<EnrollActivity1.MyListAdapter.ViewHolder>{
        Context context;
        public List<DataResultModel> listdata;

        public MyListAdapter(Context context, List<DataResultModel> listdata) {
            this.context = context;
            this.listdata = listdata;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.row_enroll, parent, false);
       ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {


            Picasso.with(EnrollActivity1.this).load(Urls.ImageBaseUrl + listdata.get(position).getImg()).placeholder(R.drawable.logo_examica).into(holder.img_user, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {

                }
            });
            String data=listdata.get(position).getExam_desc();
            holder.examnametv1.setText(listdata.get(position).getExam_name());

            String price =listdata.get(position).getExam_price();
            Log.d("price",price);
            holder. prize.setText(price  + " " +getString(R.string.rupee));
            String classname=listdata.get(position).getClass_name();
            Log.e("classname",classname);
            holder. classnametv1.setText(classname);
            holder.registredon.setText(listdata.get(position).getRegistered_on());
            holder.examdesc.setText(data);

            if(data.length()>4)
            {

                holder.seemore.setVisibility(View.VISIBLE);
                holder.seemore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialog = new Dialog(EnrollActivity1.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(true);
                        dialog.setContentView(R.layout.eam_desc);
                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                        lp.copyFrom(dialog.getWindow().getAttributes());
                        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        lp.gravity = Gravity.CENTER;

                        dialog.getWindow().setAttributes(lp);
                        ImageView itemmage = (ImageView) dialog.findViewById(R.id.menuimage);
                        TextView examnametv = (TextView) dialog.findViewById(R.id.examnametv);
                        TextView examdesctv = (TextView) dialog.findViewById(R.id.examdesctv);
                        examdesctv.setText(listdata.get(position).getExam_desc());
                        examnametv.setText(listdata.get(position).getExam_name());

                        TextView clostv = (TextView) dialog.findViewById(R.id.clostv);



                        clostv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }
                });
            }
            else
            {

                holder.seemore.setVisibility(View.GONE);


            }


        }




        @Override
        public int getItemCount() {
            return listdata.size();
        }

        public  class ViewHolder extends RecyclerView.ViewHolder {
            public TextView  examdesc,examnametv1,classnametv1,prize,registredon ;
            LinearLayout seemore;
            public LinearLayout enroll;
         ImageView img_user;

            public ViewHolder(View itemView) {
                super(itemView);
              this. img_user=(ImageView) itemView.findViewById(R.id.img_user);
                this.examdesc = (TextView) itemView.findViewById(R.id.nametv);
                this.examnametv1 = (TextView) itemView.findViewById(R.id.examnametv);
                this.classnametv1 = (TextView) itemView.findViewById(R.id.classnametv);
                this.prize = (TextView) itemView.findViewById(R.id.prizetv);
                this.registredon = (TextView) itemView.findViewById(R.id.registerdatetv);
                this.seemore = (LinearLayout) itemView.findViewById(R.id.viewdetailstv);



            }
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

    }
}