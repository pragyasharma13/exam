package com.db.schoolapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.db.schoolapp.Activity.ChagepassActivity;
import com.db.schoolapp.Activity.DashboardActivity;
import com.db.schoolapp.Activity.ForgetPassActivity;
import com.db.schoolapp.Activity.LoginActivity;
import com.google.android.material.textfield.TextInputEditText;

public class OtpActivity2 extends AppCompatActivity {
    Toolbar toolbar1;
    TextView toolbar_title;
    ImageView back_t;
    Button submit;
    String otps;
    TextInputEditText otptv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp2);
        getSupportActionBar().hide();
        toolbar1=(Toolbar)findViewById(R.id.toolbar);
        toolbar_title=(TextView)toolbar1.findViewById(R.id.toolbar_title);
        back_t=(ImageView) toolbar1.findViewById(R.id.bkBtn);
        back_t.setVisibility(View.VISIBLE);
        submit=findViewById(R.id.submit);
        otptv=findViewById(R.id.otptv1);
        String backid="1";
//        Intent myIntent = new Intent(this,  ChagepassActivity.class);
//        myIntent.putExtra("lastName", "1");
//        startActivity(myIntent);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intilization();
                validations();
            }
        });
        back_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void intilization() {
        otps=otptv.getText().toString();

    }
    private void validations() {
        if (otps.isEmpty()) {
            Toast.makeText(OtpActivity2.this, "Enter OTP", Toast.LENGTH_SHORT).show();
            otptv.requestFocus();

        } else if (otps.length()!=6) {
            Toast.makeText(OtpActivity2.this, "Enter Valid OTP number", Toast.LENGTH_SHORT).show();
            otptv.requestFocus();

        }
      else {
            Intent intent = new Intent(OtpActivity2.this, DashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("backid","1");
            startActivity(intent);

            overridePendingTransition(R.anim.enter, R.anim.leave);


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent i=new Intent(OtpActivity2.this,ForgetPassActivity.class);
//        startActivity(i);

        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);


    }

}