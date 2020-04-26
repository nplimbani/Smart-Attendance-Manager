package com.example.denish.smartattendencemanagment.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

public class AddBranchActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    Button btn_Branch;
    EditText et_addbranch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_branch);
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.Darkbule));
        }

        initView();
        setListener();
    }

    private void setListener() {

        btn_Branch.setOnClickListener(this);
    }

    private void initView() {
        getSupportActionBar().hide();

        context=AddBranchActivity.this;
        et_addbranch=(EditText)findViewById(R.id.et_addbranch);
        btn_Branch=(Button)findViewById(R.id.btn_Branch);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_Branch:

                CallAPi();
                break;

        }
    }

    private void CallAPi() {

        AppConstants.showDialog(context);
        RequestParams requestParams = new RequestParams();
        requestParams.put("branch", et_addbranch.getText().toString());

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.post(context, URL.BRANCH, requestParams, new AsyncHttpResponseHandler() {
            @SuppressLint("WrongConstant")
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);

                Log.e("Response",response);
                et_addbranch.setText(null);
                AppConstants.dissmissDialog();


            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });
    }
}
