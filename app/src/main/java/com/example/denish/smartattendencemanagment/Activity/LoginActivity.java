package com.example.denish.smartattendencemanagment.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denish.smartattendencemanagment.Constants.AppConstants;
import com.example.denish.smartattendencemanagment.Constants.AppData;
import com.example.denish.smartattendencemanagment.Constants.URL;
import com.example.denish.smartattendencemanagment.Model.ForgetPassword.ForgetPasswordResponse;
import com.example.denish.smartattendencemanagment.Model.Login_SignupResponse.LoginResponse;
import com.example.denish.smartattendencemanagment.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_login;
    EditText et_loginemail, et_loginpassword;
    Context context;
    TextView txt_sign_up,txt_forgetpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
           window.setStatusBarColor(getResources().getColor(R.color.Darkbule));
       }

        setContentView(R.layout.activity_login);

        initView();
        setListener();

    }

    private void setListener() {
        btn_login.setOnClickListener(this);
        txt_sign_up.setOnClickListener(this);
        txt_forgetpwd.setOnClickListener(this);


    }

    private void initView() {

        getSupportActionBar().hide();

        context = LoginActivity.this;

        btn_login = (Button) findViewById(R.id.btn_login);
        et_loginemail = (EditText) findViewById(R.id.et_loginemail);
        et_loginpassword = (EditText) findViewById(R.id.et_loginpassword);
        et_loginemail.setText(null);
        et_loginpassword.setText(null);
        txt_sign_up=(TextView)findViewById(R.id.txt_sign_up);
        txt_forgetpwd=(TextView)findViewById(R.id.txt_forgetpwd);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_login:

                if (et_loginemail.getText().length() == 0) {
                    AppConstants.openErrorDialog(context,"Please Enter EmailId First...");
                } else if (et_loginpassword.getText().length() == 0) {
                    AppConstants.openErrorDialog(context,"Please Enter Password First...");
                } else {
                    String email = et_loginemail.getText().toString().trim();
                    String password = et_loginpassword.getText().toString().trim();

                    if (AppConstants.isNetworkAvailable(context)) {
                        if (email.equals("Admin@gmail.com") && password.equals("Admin123")) {
                            //Admin intnet
                           // AppConstants.setData(context, AppData.LOGIN_STATUS,"1");
                            Intent intent=new Intent(context,AdminAcessActivity.class);
                            startActivity(intent);
                            finish();

                        } else {

                            CallAPI();
                        }
                    } else {
                        Log.e("Internet", "Not Available");
                    }
                }

                break;

            case R.id.txt_sign_up:

                Intent intent=new Intent(context,SignupActivity.class);
                startActivity(intent);
                break;

            case R.id.txt_forgetpwd:

                if (AppConstants.isNetworkAvailable(context)) {

                    CallAPiForgetPassword();
                }
        }
    }

    private void CallAPiForgetPassword() {
        AppConstants.showDialog(context);
        RequestParams requestParams = new RequestParams();
        requestParams.put("email_id", et_loginemail.getText().toString());

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        asyncHttpClient.post(context, URL.FORGETPASSWORD, requestParams, new AsyncHttpResponseHandler() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


                        AppConstants.dissmissDialog();
                        String response = new String(responseBody);
                        Log.e("resopnse",response);


                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        ForgetPasswordResponse forgetPasswordResponse = gson.fromJson(response, ForgetPasswordResponse.class);

                        String new_password = forgetPasswordResponse.getPassword().toString();

                        Log.e("new_password",new_password+"");
                        sendEmail(new_password);

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        AppConstants.dissmissDialog();

                    }
                });

    }


    private void CallAPI() {

        AppConstants.showDialog(context);
        RequestParams requestParams = new RequestParams();
        requestParams.put("email_id", et_loginemail.getText().toString());
        requestParams.put("password", et_loginpassword.getText().toString());

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        asyncHttpClient.post(context, URL.LOGIN_URL,
                requestParams,
                new AsyncHttpResponseHandler() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


                        AppConstants.dissmissDialog();
                        String response = new String(responseBody);

                        //check response
                        Log.e("Response", response);

                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        LoginResponse mainResponse = gson.fromJson(response, LoginResponse.class);

                        String status = mainResponse.getSTATUS().toString();
                       String name=mainResponse.getUserDetails().getName().toString();
                        Log.e("name",name);
                        if (status.equals("1")) {

                            AppConstants.setData(context, AppData.LOGIN_STATUS,"1");

                            Intent intent=new Intent(context,UserAcessActivity.class);
                            intent.putExtra("name",name);
                            startActivity(intent);
                            finish();
                            // Log.e("Login", "sucess");
                            Toast.makeText(context, "sucess", 0).show();

                        } else {
                            // Log.e("Password", "incorrect");

                            //logout
                            AppConstants.setData(context, AppData.LOGIN_STATUS,"0");

                            AppConstants.openErrorDialog(context,"Invalid EmailId Or Password...");
                            et_loginpassword.setText("");

                        }


                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        AppConstants.dissmissDialog();

                    }
                });


    }
    public void sendEmail(String password)
    {

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {et_loginemail.getText().toString()});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "New Password");
        emailIntent.putExtra(Intent.EXTRA_TEXT, password);
        startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"));
    }
}
