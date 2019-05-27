package com.robinkanatzar.weather.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public class SavedDailyForecast implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("date")
    public long date;
    @SerializedName("temperatureMin")
    public double minTemp;
    @SerializedName("temperatureMax")
    public double maxTemp;
    @SerializedName("imageUrl")
    public String imageUrl;

    public SavedDailyForecast() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
