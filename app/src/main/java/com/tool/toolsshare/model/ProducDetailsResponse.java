package com.tool.toolsshare.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProducDetailsResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("top_gift")
    @Expose
    private Boolean topGift;
    @SerializedName("conditions")
    @Expose
    private Integer conditions;
    @SerializedName("reason_for_gift")
    @Expose
    private String reasonForGift;
    @SerializedName("target_to_gift")
    @Expose
    private String targetToGift;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("images")
    @Expose
    private List<String> images = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean getTopGift() {
        return topGift;
    }

    public void setTopGift(Boolean topGift) {
        this.topGift = topGift;
    }

    public Integer getConditions() {
        return conditions;
    }

    public void setConditions(Integer conditions) {
        this.conditions = conditions;
    }

    public String getReasonForGift() {
        return reasonForGift;
    }

    public void setReasonForGift(String reasonForGift) {
        this.reasonForGift = reasonForGift;
    }

    public String getTargetToGift() {
        return targetToGift;
    }

    public void setTargetToGift(String targetToGift) {
        this.targetToGift = targetToGift;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
