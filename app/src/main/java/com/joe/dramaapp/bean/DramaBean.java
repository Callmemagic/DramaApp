package com.joe.dramaapp.bean;

import com.google.gson.annotations.SerializedName;

/**
 * author: Joe Cheng
 */
public class DramaBean {
    @SerializedName("drama_id")
    private String DramaId;

    @SerializedName("thumb")
    private String ThumbUrl;

    @SerializedName("name")
    private String Name;

    @SerializedName("rating")
    private String Rating;

    @SerializedName("created_at")
    private String CreatedAt;

    @SerializedName("total_views")
    private String totalViews;

    public DramaBean(String name, String rating, String createdAt, String totalViews) {
        Name = name;
        Rating = rating;
        CreatedAt = createdAt;
        this.totalViews = totalViews;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        CreatedAt = createdAt;
    }

    public String getTotalViews() {
        return totalViews;
    }

    public void setTotalViews(String totalViews) {
        this.totalViews = totalViews;
    }

    public String getDramaId() {
        return DramaId;
    }

    public void setDramaId(String dramaId) {
        DramaId = dramaId;
    }

    public String getThumbUrl() {
        return ThumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        ThumbUrl = thumbUrl;
    }
}
