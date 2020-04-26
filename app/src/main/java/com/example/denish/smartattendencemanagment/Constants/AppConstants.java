package com.example.denish.smartattendencemanagment.Constants;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.denish.smartattendencemanagment.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Denish on 30-10-2018.
 */

public class AppConstants {


    public static ProgressDialog progressDialog;


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public static void showDialog(Context context) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

    }


    public static  void dissmissDialog()
    {
        progressDialog.dismiss();
    }


    public static void setData(Context context,String key,String value)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences("Hello", 0).edit();
        editor.putString(key, value);
        editor.apply();
    }


    public static void openErrorDialog(Context context,String error_msg) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_login_error);
        dialog.setCancelable(false);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        final Button btn_done = (Button) dialog.findViewById(R.id.btn_done);
        TextView txt_error_msg = (TextView) dialog.findViewById(R.id.txt_error_msg);
        txt_error_msg.setText(error_msg);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();


    }



    public static String getData(Context context,String key)
    {
        SharedPreferences prefs = context.getSharedPreferences("Hello", 0);

        String data= prefs.getString(key,"");

        return  data;

    }
    public static boolean isValidMobile(String phone) {
        Pattern p;
        boolean check = false;

        if (!Pattern.matches("[a-zA-Z]+", phone) && phone!=null) {
            // if(phone.length() < 6 || phone.length() > 13) {
            if (phone.length() != 10) {
                check = false;
                // txtPhone.setError("Not Valid Number");
            } else {
                check = true;
            }
        } else {
            check = false;
        }
        return check;
    }

    public static boolean isValidMail(String email) {
        boolean check = true;
        Pattern p;
        Matcher m;

        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        p = Pattern.compile(EMAIL_STRING);

        m = p.matcher(email);
        check = m.matches();

        if (!check) {
            // txtEmail.setError("Not Valid Email");
            check = false;
        }
        return check;
    }

}
