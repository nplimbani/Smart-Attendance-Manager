package com.example.denish.smartattendencemanagment.Model.Classes;

/**
 * Created by Denish on 31-10-2018.
 */

public class Bean {

    String st_id;
    String st_name;
    String status;
    String student_mobile;

    public String getStudent_mobile() {
        return student_mobile;
    }

    public void setStudent_mobile(String student_mobile) {
        this.student_mobile = student_mobile;
    }

    public String getParent_mobile() {
        return Parent_mobile;
    }

    public void setParent_mobile(String parent_mobile) {
        Parent_mobile = parent_mobile;
    }

    String Parent_mobile;

    public String getSt_id() {
        return st_id;
    }

    public void setSt_id(String st_id) {
        this.st_id = st_id;
    }

    public String getSt_name() {
        return st_name;
    }

    public void setSt_name(String st_name) {
        this.st_name = st_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
