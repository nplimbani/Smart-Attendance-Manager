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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.denish.smartattendencemanagment.Constants.AppConstants;
import com.example.denish.smartattendencemanagment.Constants.AppData;
import com.example.denish.smartattendencemanagment.Constants.URL;
import com.example.denish.smartattendencemanagment.Model.BranchResponse.BranchMainResponse;
import com.example.denish.smartattendencemanagment.Model.Login_SignupResponse.LoginResponse;
import com.example.denish.smartattendencemanagment.Model.StudentResponse.StudentResponse;
import com.example.denish.smartattendencemanagment.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class UserAcessActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    Spinner sp_branch, sp_semester, sp_class;
    Button btn_select;
    ImageButton btn_logout;
    TextView txt_user;
    ArrayList<String> list_branch, list_semester, list_class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_acess);
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.Darkbule));
        }

        initView();
        setListener();
    }

    private void setListener() {

        btn_select.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
    }

    private void initView() {
        getSupportActionBar().hide();

        context = UserAcessActivity.this;
        sp_branch = (Spinner) findViewById(R.id.sp_branch);
        sp_semester = (Spinner) findViewById(R.id.sp_semester);
        sp_class = (Spinner) findViewById(R.id.sp_class);
        btn_select = (Button) findViewById(R.id.btn_select);
        btn_logout = (ImageButton) findViewById(R.id.btn_logout);
        txt_user=(TextView)findViewById(R.id.txt_user);
        String name=getIntent().getStringExtra("name");
        txt_user.setText(name);

        CallBranchApi();
        list_semester = new ArrayList<>();
        list_semester = new ArrayList<>();
        list_semester.add("1");
        list_semester.add("2");
        list_semester.add("3");
        list_semester.add("4");
        list_semester.add("5");
        list_semester.add("6");
        list_semester.add("7");
        list_semester.add("8");

        setAdapter(sp_semester, list_semester);

        list_class = new ArrayList<>();
        list_class = new ArrayList<>();
        list_class.add("1");
        list_class.add("2");
        setAdapter(sp_class, list_class);


    }

    private void CallBranchApi() {

        AppConstants.showDialog(context);
        RequestParams requestParams = new RequestParams();
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.post(context, URL.GETBRANCH, requestParams, new AsyncHttpResponseHandler() {
            @SuppressLint("WrongConstant")
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                AppConstants.dissmissDialog();

                Log.e("response", response);

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                BranchMainResponse branchMainResponse = gson.fromJson(response, BranchMainResponse.class);

                list_branch = new ArrayList<>();
                for (int i = 0; i < branchMainResponse.getBranchDetails().size(); i++) {

                    list_branch.add(branchMainResponse.getBranchDetails().get(i).getBranch());


                }
                setAdapter(sp_branch, list_branch);


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

    private void setAdapter(Spinner spinner, ArrayList<String> list) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, R.layout.spinner, R.id.txt_view, list);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(arrayAdapter);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_select:
                if (AppConstants.isNetworkAvailable(context)) {

                    CallApi();

                } else {
                    Log.e("Internet", "Not Available");
                }


                break;

            case R.id.btn_logout:


                AppConstants.setData(context, AppData.LOGIN_STATUS, "0");
                finish();
                break;

        }
    }


    private void CallApi() {

        AppConstants.showDialog(context);
        RequestParams requestParams = new RequestParams();
        requestParams.put("branch", sp_branch.getSelectedItem().toString());
        requestParams.put("semester", sp_semester.getSelectedItem().toString());
        requestParams.put("class", sp_class.getSelectedItem().toString());

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.post(context, URL.GETSTUDENTDETAILS, requestParams, new AsyncHttpResponseHandler() {
            @SuppressLint("WrongConstant")
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);

                Log.e("Response", response);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                StudentResponse studentResponse = gson.fromJson(response, StudentResponse.class);

//                Log.e("size",studentResponse.getStudentDetails().size()+"");
                if (studentResponse.getStudentDetails() != null && studentResponse.getStudentDetails().size() > 0) {

                    Intent intent = new Intent(context, AttendenceAcitvity.class);
                    intent.putExtra("list", response);
                    intent.putExtra("branch", sp_branch.getSelectedItem().toString());
                    intent.putExtra("semester", sp_semester.getSelectedItem().toString());
                    intent.putExtra("class", sp_class.getSelectedItem().toString());
                    startActivity(intent);
                } else {
                    AppConstants.dissmissDialog();
                    AppConstants.openErrorDialog(context, "Details not found");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
}

