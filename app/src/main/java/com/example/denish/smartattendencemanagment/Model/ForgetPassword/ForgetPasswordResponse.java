package com.example.denish.smartattendencemanagment.Model.ForgetPassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Denish on 01-11-2018.
 */

public class ForgetPasswordResponse {

    @SerializedName("STATUS")
    @Expose
    private Boolean sTATUS;
    @SerializedName("password")
    @Expose
    private Integer password;

    public Boolean getSTATUS() {
        return sTATUS;
    }

    public void setSTATUS(Boolean sTATUS) {
        this.sTATUS = sTATUS;
    }

    public Integer getPassword() {
        return password;
    }

    public void setPassword(Integer password) {
        this.password = password;
    }
}
