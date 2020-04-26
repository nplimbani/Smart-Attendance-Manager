package com.example.denish.smartattendencemanagment.Model.BranchResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Denish on 31-10-2018.
 */

public class BranchDetails {
    @SerializedName("branch")
    @Expose
    private String branch;

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
