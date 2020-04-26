package com.example.denish.smartattendencemanagment.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.denish.smartattendencemanagment.Constants.AppConstants;
import com.example.denish.smartattendencemanagment.Constants.URL;
import com.example.denish.smartattendencemanagment.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

import static com.example.denish.smartattendencemanagment.Constants.AppConstants.isValidMail;
import static com.example.denish.smartattendencemanagment.Constants.AppConstants.isValidMobile;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_Signup_name, et_Signup_email, et_Signup_pwd, et_Signup_mobileno;
    Button btn_submit;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.Darkbule));
        }
        setContentView(R.layout.activity_signup);

        initView();
        setListener();
    }


    private void initView() {
        getSupportActionBar().hide();

        context = SignupActivity.this;
        et_Signup_name = (EditText) findViewById(R.id.et_Signupname);
        et_Signup_email = (EditText) findViewById(R.id.et_Signupemail);
        et_Signup_pwd = (EditText) findViewById(R.id.et_Signup_pwd);
        et_Signup_mobileno = (EditText) findViewById(R.id.et_Signup_mobileno);
        btn_submit = (Button) findViewById(R.id.btn_submit);

    }

    private void setListener() {
        btn_submit.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_submit:

                String name=et_Signup_name.getText().toString().trim();
                String password=et_Signup_pwd.getText().toString().trim();
                String mobile = et_Signup_mobileno.getText().toString().trim();
                String email = et_Signup_email.getText().toString().trim();

                if(name.length()==0){
                    AppConstants.openErrorDialog(context, "Please Enter Name First...");

                }
                else if (email.length() == 0) {
                    AppConstants.openErrorDialog(context, "Please Enter EmailId First...");
                }else if(password.length()==0){
                    AppConstants.openErrorDialog(context, "Please Enter Password First...");

                } else if (mobile.length() == 0) {
                    AppConstants.openErrorDialog(context, "Please Enter mobile number First...");
                }
                boolean check_email = isValidMail(email);
                boolean check_mobile = isValidMobile(mobile);
                if (check_mobile == true && check_email == true ) {
                    CallAPi();
                } else if (check_email == false && email.length()!=0) {
                    AppConstants.openErrorDialog(context, "Please Enter Valid Email First...");

                } else if(check_mobile ==false && mobile.length()!=0){
                    AppConstants.openErrorDialog(context, "Please Enter Valid Mobile First.. ");
                }
                break;

        }

    }


    private void CallAPi() {
        AppConstants.showDialog(context);
        RequestParams requestParams = new RequestParams();
        requestParams.put("name", et_Signup_name.getText().toString().trim());
        requestParams.put("email_id", et_Signup_email.getText().toString().trim());
        requestParams.put("password", et_Signup_pwd.getText().toString().trim());
        requestParams.put("mobile_no", et_Signup_mobileno.getText().toString().trim());

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        asyncHttpClient.post(context, URL.SIGNUP_URL,
                requestParams,
                new AsyncHttpResponseHandler() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        AppConstants.dissmissDialog();
                        String response = new String(responseBody);

                        //check response
                        Log.e("Response", response);
                        et_Signup_name.setText("");
                        et_Signup_email.setText("");
                        et_Signup_pwd.setText("");
                        et_Signup_mobileno.setText("");

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        AppConstants.dissmissDialog();

                    }
                });


    }
}
