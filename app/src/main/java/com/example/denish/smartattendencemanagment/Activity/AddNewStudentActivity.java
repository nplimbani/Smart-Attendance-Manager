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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.denish.smartattendencemanagment.Constants.AppConstants;
import com.example.denish.smartattendencemanagment.Constants.URL;
import com.example.denish.smartattendencemanagment.Model.BranchResponse.BranchMainResponse;
import com.example.denish.smartattendencemanagment.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class AddNewStudentActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    Spinner sp_branch, sp_semester, sp_class;
    EditText et_fname, et_mname, et_lname, et_studentno, et_parentsno;
    Button btn_Addst;
    ArrayList<String> list_branch, list_semester, list_class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_student);
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.Darkbule));
        }

        initView();
        setListener();
    }

    private void setListener() {

        btn_Addst.setOnClickListener(this);
    }

    private void initView() {
        getSupportActionBar().hide();

        context = AddNewStudentActivity.this;
        sp_branch = (Spinner) findViewById(R.id.sp_branch);
        sp_semester = (Spinner) findViewById(R.id.sp_semester);
        sp_class = (Spinner) findViewById(R.id.sp_class);
        et_fname = (EditText) findViewById(R.id.et_fname);
        et_mname = (EditText) findViewById(R.id.et_mname);
        et_lname = (EditText) findViewById(R.id.et_lname);
        btn_Addst = (Button) findViewById(R.id.btn_Addst);
        et_studentno = (EditText) findViewById(R.id.et_studentno);
        et_parentsno = (EditText) findViewById(R.id.et_parentsno);

        CallBranchApi();
        list_branch = new ArrayList<>();

        list_semester = new ArrayList<>();
        list_semester.add("1");
        list_semester.add("2");
        list_semester.add("3");
        list_semester.add("4");
        list_semester.add("5");
        list_semester.add("6");
        list_semester.add("7");
        list_semester.add("8");
        setAdapter(sp_semester,list_semester);

        list_class = new ArrayList<>();
        list_class = new ArrayList<>();
        list_class.add("1");
        list_class.add("2");
        setAdapter(sp_class,list_class);
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

                setAdapter(sp_branch,list_branch);


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }
    private void setAdapter(Spinner spinner,ArrayList<String> list) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, R.layout.spinner, R.id.txt_view, list);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(arrayAdapter);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_Addst:
                if (et_fname.getText().length() == 0) {
                    AppConstants.openErrorDialog(context, "Please Enter first name First...");
                } else if (et_mname.getText().length() == 0) {
                    AppConstants.openErrorDialog(context, "Please Enter middle name First...");
                } else if (et_lname.getText().length() == 0) {
                    AppConstants.openErrorDialog(context, "Please Enter last name First...");
                } else if (et_studentno.getText().length() == 0) {
                    AppConstants.openErrorDialog(context, "Please Enter Student mobile number First...");
                } else if (et_parentsno.getText().length() == 0) {
                    AppConstants.openErrorDialog(context, "Please Enter Parents mobile number First...");
                } else {
                    if (AppConstants.isNetworkAvailable(context)) {
                        CallApi();

                    } else {
                        Log.e("Internet", "Not Available");
                    }
                }
                break;
        }


    }

    private void CallApi() {
        AppConstants.showDialog(context);

        String fname = et_fname.getText().toString().trim();
        String mname = et_mname.getText().toString().trim();
        String lname = et_lname.getText().toString().trim();
        String st_no=et_studentno.getText().toString().trim();
        String p_no=et_parentsno.getText().toString().trim();

        RequestParams requestParams = new RequestParams();
        requestParams.put("branch", sp_branch.getSelectedItem().toString());
        requestParams.put("semester", sp_semester.getSelectedItem().toString());
        requestParams.put("class", sp_class.getSelectedItem().toString());
        requestParams.put("st_firstname", fname);
        requestParams.put("st_middlename", mname);
        requestParams.put("st_lastname", lname);
        requestParams.put("student_no",st_no);
        requestParams.put("parents_no",p_no);

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.post(context, URL.ADDSTUDENTDETAILS, requestParams, new AsyncHttpResponseHandler() {
            @SuppressLint("WrongConstant")
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                Log.e("Response", response);
                AppConstants.dissmissDialog();

                et_fname.setText(null);
                et_mname.setText(null);
                et_lname.setText(null);
                et_studentno.setText(null);
                et_parentsno.setText(null);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                AppConstants.dissmissDialog();

            }
        });

    }

}

