package com.example.projectsetup.data;


import com.google.gson.annotations.SerializedName;

public class GeocodeResponse {
    @SerializedName("lat")
    private String lat;
    @SerializedName("lon")
    private String lon;

    @SerializedName("display_name")
    private String displayName;

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getDisplayName() {
        return displayName;
    }
}

