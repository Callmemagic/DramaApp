package com.joe.dramaapp.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * author: Joe Cheng
 */
@Entity
public class Drama {
    @PrimaryKey
    @NonNull
    String drama_id;

    @NonNull
    String name;

    @NonNull
    String total_views;

    @NonNull
    String created_at;

    @NonNull
    String thumb;

    @NonNull
    String rating;

    public Drama(String drama_id, @NonNull String name, @NonNull String total_views, @NonNull String created_at, @NonNull String thumb, @NonNull String rating) {
        this.drama_id = drama_id;
        this.name = name;
        this.total_views = total_views;
        this.created_at = created_at;
        this.thumb = thumb;
        this.rating = rating;
    }

    public String getDrama_id() {
        return drama_id;
    }

    public void setDrama_id(String drama_id) {
        this.drama_id = drama_id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getTotal_views() {
        return total_views;
    }

    public void setTotal_views(@NonNull String total_views) {
        this.total_views = total_views;
    }

    @NonNull
    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(@NonNull String created_at) {
        this.created_at = created_at;
    }

    @NonNull
    public String getThumb() {
        return thumb;
    }

    public void setThumb(@NonNull String thumb) {
        this.thumb = thumb;
    }

    @NonNull
    public String getRating() {
        return rating;
    }

    public void setRating(@NonNull String rating) {
        this.rating = rating;
    }
}
