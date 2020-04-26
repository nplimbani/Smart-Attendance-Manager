package com.example.denish.smartattendencemanagment.Model.Login_SignupResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Denish on 30-10-2018.
 */

public class LoginResponse {

    @SerializedName("STATUS")
    @Expose
    private String sTATUS;
    @SerializedName("MSG")
    @Expose
    private String mSG;
    @SerializedName("UserDetails")
    @Expose
    private LoginData userDetails;

    public String getSTATUS() {
        return sTATUS;
    }

    public void setSTATUS(String sTATUS) {
        this.sTATUS = sTATUS;
    }

    public String getMSG() {
        return mSG;
    }

    public void setMSG(String mSG) {
        this.mSG = mSG;
    }

    public LoginData getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(LoginData userDetails) {
        this.userDetails = userDetails;
    }


}
