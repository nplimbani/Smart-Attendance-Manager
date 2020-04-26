package com.example.denish.smartattendencemanagment.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.denish.smartattendencemanagment.Constants.AppConstants;
import com.example.denish.smartattendencemanagment.Constants.AppData;
import com.example.denish.smartattendencemanagment.R;

public class MainActivity extends AppCompatActivity {
    Button btn_splash;
    ImageView img_splash;
    Animation frombottom, fromtop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.Darkbule));
        }

        getSupportActionBar().hide();
        initView();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                if(AppConstants.getData(MainActivity.this, AppData.LOGIN_STATUS).equals("")
                    || AppConstants.getData(MainActivity.this,AppData.LOGIN_STATUS).equals("0"))
                {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent=new Intent(MainActivity.this,UserAcessActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 4000);
    }

    private void initView() {

        btn_splash = (Button) findViewById(R.id.btn_splash);
        img_splash = (ImageView) findViewById(R.id.img_splash);
        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        fromtop = AnimationUtils.loadAnimation(this, R.anim.fromtop);
        btn_splash.setAnimation(frombottom);

        Glide.with(MainActivity.this)
                .load(R.drawable.mainlogo)
                .override(300, 300)
                .into(img_splash);
        img_splash.setAnimation(fromtop);
    }
}


