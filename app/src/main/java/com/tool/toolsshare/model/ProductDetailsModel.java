package com.tool.toolsshare.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductDetailsModel {

    @SerializedName("response")
    @Expose
    private List<ProducDetailsResponse> response = null;

    public List<ProducDetailsResponse> getResponse() {
        return response;
    }

    public void setResponse(List<ProducDetailsResponse> response) {
        this.response = response;
    }
}
