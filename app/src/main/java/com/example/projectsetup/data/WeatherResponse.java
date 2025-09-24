package com.example.projectsetup.data;


import com.google.gson.annotations.SerializedName;

public class WeatherResponse {
    @SerializedName("name")
    private String cityName;

    @SerializedName("main")
    private Main main;
    public String getCityName() {
        return cityName;
    }

    public Main getMain() {
        return main;
    }

    public static class Main {
        @SerializedName("temp")
        private double temp;

        public double getTemp() {
            return temp;
        }
    }
}

