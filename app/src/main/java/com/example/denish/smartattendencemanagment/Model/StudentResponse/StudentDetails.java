package com.example.denish.smartattendencemanagment.Model.StudentResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Denish on 31-10-2018.
 */

public class StudentDetails {


    @SerializedName("studentid")
    @Expose
    private String studentid;
    @SerializedName("st_firstname")
    @Expose
    private String stFirstname;
    @SerializedName("st_middlename")
    @Expose
    private String stMiddlename;
    @SerializedName("st_lastname")
    @Expose
    private String stLastname;
    @SerializedName("student_no")
    @Expose
    private String studentNo;
    @SerializedName("parents_no")
    @Expose
    private String parentsNo;

    public String getStFirstname() {
        return stFirstname;
    }
    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }
    public void setStFirstname(String stFirstname) {
        this.stFirstname = stFirstname;
    }

    public String getStMiddlename() {
        return stMiddlename;
    }

    public void setStMiddlename(String stMiddlename) {
        this.stMiddlename = stMiddlename;
    }

    public String getStLastname() {
        return stLastname;
    }

    public void setStLastname(String stLastname) {
        this.stLastname = stLastname;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getParentsNo() {
        return parentsNo;
    }

    public void setParentsNo(String parentsNo) {
        this.parentsNo = parentsNo;
    }
}
