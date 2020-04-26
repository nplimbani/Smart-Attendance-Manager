package com.example.denish.smartattendencemanagment.Model.BranchResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Denish on 31-10-2018.
 */

public class BranchMainResponse {
    @SerializedName("branchDetails")
    @Expose
    private List<BranchDetails> branchDetails = null;

    public List<BranchDetails> getBranchDetails() {
        return branchDetails;
    }

    public void setBranchDetails(List<BranchDetails> branchDetails) {
        this.branchDetails = branchDetails;
    }
}
