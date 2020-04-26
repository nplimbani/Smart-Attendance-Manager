package com.example.denish.smartattendencemanagment.Model.StudentResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Denish on 31-10-2018.
 */

public class StudentResponse {
    @SerializedName("StudentDetails")
    @Expose
    private List<StudentDetails> studentDetails = null;

    public List<StudentDetails> getStudentDetails() {
        return studentDetails;
    }

    public void setStudentDetails(List<StudentDetails> studentDetails) {
        this.studentDetails = studentDetails;
    }
}
