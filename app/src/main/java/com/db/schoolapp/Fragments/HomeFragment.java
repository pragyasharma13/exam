package com.db.schoolapp.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.db.schoolapp.Activity.EnrollActivity;
import com.db.schoolapp.Activity.EnrollActivity1;
import com.db.schoolapp.Activity.ForgetPassActivity;
import com.db.schoolapp.Activity.LoginActivity;
import com.db.schoolapp.Activity.ProfileActivity;
import com.db.schoolapp.Constants.AppConstants;
import com.db.schoolapp.Constants.StoreDataPrefernce;
import com.db.schoolapp.Constants.Urls;
import com.db.schoolapp.Helpers.Progress_Dialogue;
import com.db.schoolapp.Model.Homelist;
import com.db.schoolapp.Models.DataResultModel;
import com.db.schoolapp.Models.ExamEnrollModels;
import com.db.schoolapp.OtpActivity2;
import com.db.schoolapp.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment {
    List<DataResultModel> dataResultModels=new ArrayList<>();
    RecyclerView recyclerView;
    private ImageView image;
    private String userid1,classid;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_home, container, false);
         recyclerView = (RecyclerView)v. findViewById(R.id.homelistrv);
        StoreDataPrefernce storeDataPrefernce = new StoreDataPrefernce(getActivity());
        userid1 = storeDataPrefernce.getUserId();
        classid = storeDataPrefernce.getclassid();

        ExamEnrollApi();

        return  v;
    }
    private void ExamEnrollApi() {
        Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(getActivity(), "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.URL_EXAMlIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                myDialog.dismiss();
                                Log.e("pragyares",response);
                                JSONArray jsonObject = obj.optJSONArray("data_result");
                                if (dataResultModels != null) {
                                    dataResultModels.clear();
                                }
                                for (int i = 0; i < jsonObject.length(); i++) {
                                    JSONObject qstnArray = jsonObject.getJSONObject(i);
                                    DataResultModel dataResultModel = new DataResultModel();
//                                    dataResultModel.setUser_exam_id(qstnArray.getString("user_exam_id"));
                                    dataResultModel.setExam_name(qstnArray.getString("exam_name"));
                                    dataResultModel.setExam_id(qstnArray.getString("exam_id"));
                                    dataResultModel.setExam_desc(qstnArray.getString("exam_desc"));
                                    dataResultModel.setExam_price(qstnArray.getString("exam_price"));
                                    dataResultModel.setClass_name(qstnArray.getString("class_name"));
                                    dataResultModel.setImg(qstnArray.getString("img"));



                                    dataResultModels.add(dataResultModel);
                                }
                                MyListAdapter myListAdapter = new MyListAdapter(getActivity(),dataResultModels);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                recyclerView.setAdapter(myListAdapter);
                            } else {
                                myDialog.dismiss();
                                if (dataResultModels != null) {
                                    dataResultModels.clear();
                                }
                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();
                            Toast.makeText(getActivity(), getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();
                        Toast.makeText(getActivity(), getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("class_id",classid);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(AppConstants.PICKNDROPKEY, AppConstants.PICKNDROPKEYVALUE);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
        Context context;
        public List<DataResultModel> listdata;

        public MyListAdapter(Context context, List<DataResultModel> listdata) {
            this.context = context;
            this.listdata = listdata;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.row_homelist, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {


            Picasso.with(getActivity()).load(Urls.ImageBaseUrl + listdata.get(position).getImg()).placeholder(R.drawable.logo_examica).into(holder.img_user, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {

                }
            });
            holder.examname1.setText(listdata.get(position).getExam_name());
            String price=listdata.get(position).getExam_price();

            holder. prize.setText(price  + " " +getString(R.string.rupee));

            holder. class1.setText(listdata.get(position).getClass_name());
            String data= listdata.get(position).getExam_desc();
            Log.d("dartajhbdfjdf",data);
            holder.name.setText(data);
            if(data.length()>4)
            {

                holder.seemore.setVisibility(View.VISIBLE);
                                            holder.seemore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(getActivity());
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




            holder.enroll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });

        }


        @Override
        public int getItemCount() {
            return listdata.size();
        }

        public  class ViewHolder extends RecyclerView.ViewHolder {
            public TextView  examname1,name,class1,prize ;
            public LinearLayout enroll,seemore;
            ImageView img_user;

            public ViewHolder(View itemView) {
                super(itemView);
                this.examname1 = (TextView) itemView.findViewById(R.id.examnametv);
                this.name = (TextView) itemView.findViewById(R.id.nametv);
                this.class1 = (TextView) itemView.findViewById(R.id.classnametv);
                this.prize = (TextView) itemView.findViewById(R.id.prizetv);
                this.enroll=(LinearLayout) itemView.findViewById(R.id.enroll);
                this.img_user=(ImageView) itemView.findViewById(R.id.img_user);
                this.seemore=(LinearLayout) itemView.findViewById(R.id.viewdetailstv);



            }
        }
        private void Enrollapi() {
            Dialog myDialog;
            Progress_Dialogue DialogUtils = null;
            myDialog = DialogUtils.showProgressDialog(getActivity(), "Loading Please Wait...");
            StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.URL_EXAMENROLL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Log.e("ENROLLEXAM",response);
                        JSONObject obj=new JSONObject(response);
                        String status=obj.getString("status");
                        if(status.equals("1"))
                        {
                            myDialog.dismiss();
                            Toast.makeText(context, "User found sucessfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            myDialog.dismiss();
                            Toast.makeText(getActivity(), "User Does Not Exist", Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        myDialog.dismiss();
                        Toast.makeText(getActivity(), getString(R.string.SomethingWentWrong), Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    myDialog.dismiss();
                    Toast.makeText(getActivity(), getString(R.string.NetworkError), Toast.LENGTH_SHORT).show();

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id",userid1);
                    params.put("exam_id","1");

                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(AppConstants.PICKNDROPKEY, AppConstants.PICKNDROPKEYVALUE);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        }

    }
}