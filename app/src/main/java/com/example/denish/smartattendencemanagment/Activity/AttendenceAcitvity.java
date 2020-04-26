package com.example.denish.smartattendencemanagment.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.denish.smartattendencemanagment.Model.Classes.Bean;
import com.example.denish.smartattendencemanagment.Model.Classes.Item;
import com.example.denish.smartattendencemanagment.Adapter.adapter;
import com.example.denish.smartattendencemanagment.Model.StudentResponse.StudentResponse;
import com.example.denish.smartattendencemanagment.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class AttendenceAcitvity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    Button btn_confirm;
    RecyclerView recyclerView;
    String response;
    ArrayList<Item> Responselist;
    ArrayList<String> PresentList;
    String branch,semester,classes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence_acitvity);
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.Darkbule));
        }

        initView();
        setListener();
    }

    private void setListener() {

        btn_confirm.setOnClickListener(this);
    }

    public void initView() {
        getSupportActionBar().hide();

        context = AttendenceAcitvity.this;
        getPermission();

        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        response = getIntent().getStringExtra("list");
        branch=getIntent().getStringExtra("branch");
        semester=getIntent().getStringExtra("semester");
        classes=getIntent().getStringExtra("class");
        PresentList = new ArrayList<>();
        Responselist = new ArrayList<>();

        StudentResponse studentResponse = parseJsonString(response);
        for (int i = 0; i < studentResponse.getStudentDetails().size(); i++) {

            String st_id = studentResponse.getStudentDetails().get(i).getStudentid().toString();
            String fname = studentResponse.getStudentDetails().get(i).getStFirstname().toString();
            String mname = studentResponse.getStudentDetails().get(i).getStMiddlename().toString();
            String lname = studentResponse.getStudentDetails().get(i).getStLastname().toString();
            String st_no = studentResponse.getStudentDetails().get(i).getStudentNo().toString();
            String p_no = studentResponse.getStudentDetails().get(i).getParentsNo().toString();

            Item item = new Item();
            item.setFname(fname);
            item.setMname(mname);
            item.setSt_id(st_id);
            item.setSt_no(st_no);
            item.setP_no(p_no);

            Responselist.add(item);

        }

        CallAdapter();
    }

    private void CallAdapter() {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        adapter adapter = new adapter(AttendenceAcitvity.this, Responselist);
        recyclerView.setAdapter(adapter);
    }

    private StudentResponse parseJsonString(String response) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        StudentResponse studentResponse = gson.fromJson(response, StudentResponse.class);

        return studentResponse;
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.btn_confirm:
                SparseBooleanArray selectedRows = adapter.getmSelectedIds();
                int j = 0;
                if (selectedRows.size() > 0) {

                    StringBuilder stringBuilder = new StringBuilder();
                    j = 0;
                    int i = 0;
                    for (i = 0; i < selectedRows.size(); i++) {


                        if (selectedRows.valueAt(i)) {
                            j = j + 1;
                            String selectedRowLabel = String.valueOf(Responselist.get(selectedRows.keyAt(i)).getSt_id());
                            Log.e("St_present", selectedRowLabel);
                            PresentList.add(selectedRowLabel);
                            stringBuilder.append(selectedRowLabel + "\n");
                        }

                    }

                }
                openAttendenceDialog(context, "" + j, "" + (Responselist.size() - j));
                break;
        }


    }

    public void openAttendenceDialog(Context context, String present, String Absent) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.confirm_error);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        final Button btn_done = (Button) dialog.findViewById(R.id.btn_done);
        TextView txt_present = (TextView) dialog.findViewById(R.id.txt_present);
        TextView txt_absent = (TextView) dialog.findViewById(R.id.txt_absent);
        txt_present.setText(present);
        txt_absent.setText(Absent);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                createExcelSheet();
                sendEmail();

            }
        });
        dialog.show();
    }

    File directory, sd, file;
    WritableWorkbook workbook;

    void createExcelSheet() {
        String csvFile = "StudentAttendent" + branch+"_"+semester+"_"+classes+".xls";
        sd = Environment.getExternalStorageDirectory();
        directory = new File(sd.getAbsolutePath());
        file = new File(directory, csvFile);
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        try {
            workbook = Workbook.createWorkbook(file, wbSettings);
            createFirstSheet();
            //  createSecondSheet();
            //closing cursor
            workbook.write();
            workbook.close();
            sendEmail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void createFirstSheet() {
        try {
            List<Bean> listdata = new ArrayList<>();

            StudentResponse studentResponse = parseJsonString(response);
            for (int i = 0; i < studentResponse.getStudentDetails().size(); i++) {
                Bean bean = new Bean();
                bean.setSt_id(studentResponse.getStudentDetails().get(i).getStudentid());
                bean.setSt_name(studentResponse.getStudentDetails().get(i).getStFirstname() + " " +
                        studentResponse.getStudentDetails().get(i).getStMiddlename());
                bean.setStudent_mobile(studentResponse.getStudentDetails().get(i).getStudentNo());
                bean.setParent_mobile(studentResponse.getStudentDetails().get(i).getParentsNo());
                bean.setStatus("Absent");
                for (int j = 0; j < PresentList.size(); j++) {
                    if (PresentList.get(j).equals(studentResponse.getStudentDetails().get(i).getStudentid())) {
                        bean.setStatus("Present");
                        break;
                    } else {
                        bean.setStatus("Absent");
                    }
                }

                listdata.add(bean);

                //  bean.setStatus();

            }
            //Excel sheet name. 0 (number)represents first sheet
            WritableSheet sheet = workbook.createSheet("sheet1", 0);
            // column and row title
            sheet.addCell(new Label(0, 0, "Student_ID"));
            sheet.addCell(new Label(1, 0, "Student_Name"));
            sheet.addCell(new Label(2, 0, "Status"));
            sheet.addCell(new Label(3, 0, "Student_mobile"));
            sheet.addCell(new Label(4, 0, "Parent_mobile"));

            // sheet.addCell(new Label(3, 0, "lastName"));
            for (int i = 0; i < listdata.size(); i++) {
                sheet.addCell(new Label(0, i + 1, listdata.get(i).getSt_id()));
                sheet.addCell(new Label(1, i + 1, listdata.get(i).getSt_name()));
                sheet.addCell(new Label(2, i + 1, listdata.get(i).getStatus()));
                sheet.addCell(new Label(3, i + 1, listdata.get(i).getStudent_mobile()));
                sheet.addCell(new Label(4, i + 1, listdata.get(i).getParent_mobile()));

                //sheet.addCell(new Label(3, i + 1, listdata.get(i).getLastName()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @SuppressLint("NewApi")
    private void getPermission() {
        if (
                ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                }, 0);
            }
        }
    }

    public void sendEmail()
    {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"cgpbmd@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "subject here");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "body text");
        String filename = "StudentAttendent" + branch+"_"+semester+"_"+classes+".xls";
        // Creating Input Stream
        File sd = Environment.getExternalStorageDirectory();
        File directory = new File(sd.getAbsolutePath());
        File file = new File(directory, filename);
        if (!file.exists()) {
            return;
        }
        Uri uri = Uri.fromFile(file);
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"));
    }

}


