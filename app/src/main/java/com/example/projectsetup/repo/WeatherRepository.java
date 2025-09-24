package com.example.projectsetup.repo;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.projectsetup.data.WeatherResponse;
import com.example.projectsetup.retrofit.WeatherRetrofitClient;
import com.example.projectsetup.retrofit.WeatherApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepository {
    private final WeatherApi apiService;

    public WeatherRepository() {
        apiService = WeatherRetrofitClient.getClient().create(WeatherApi.class);
    }

    public LiveData<WeatherResponse> getWeather(double lat, double lon, String apiKey) {
        MutableLiveData<WeatherResponse> data = new MutableLiveData<>();

        apiService.getWeatherByLocation(lat, lon, apiKey, "metric")
                .enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                        if (response.isSuccessful()) {
                            data.setValue(response.body());
                        } else {
                            data.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {
                        data.setValue(null);
                    }
                });

        return data;
    }
}

