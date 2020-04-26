package com.example.denish.smartattendencemanagment.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.denish.smartattendencemanagment.R;

public class AdminAcessActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    Button  btn_addbranch, btn_addstudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_acess);
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.Darkbule));
        }

        initView();
        setListener();
    }

    private void setListener() {
        btn_addstudent.setOnClickListener(this);
        btn_addbranch.setOnClickListener(this);

    }

    private void initView() {

        getSupportActionBar().hide();

        context=AdminAcessActivity.this;
        btn_addbranch=(Button)findViewById(R.id.btn_addbranch);
        btn_addstudent=(Button)findViewById(R.id.btn_addstudent);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){

            case R.id.btn_addbranch:

                Intent intent=new Intent(context,AddBranchActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_addstudent:

                Intent intent1=new Intent(context,AddNewStudentActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
