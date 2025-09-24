package com.example.projectsetup.repo;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.projectsetup.data.GeocodeResponse;
import com.example.projectsetup.retrofit.GeocodeApiService;
import com.example.projectsetup.retrofit.RetrofitClient;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeocodeRepository {
    private final GeocodeApiService apiService;

    public GeocodeRepository() {
        apiService = RetrofitClient.getInstance().create(GeocodeApiService.class);
    }

    public LiveData<GeocodeResponse> reverseGeocode(double lat, double lon, String apiKey) {
        MutableLiveData<GeocodeResponse> data = new MutableLiveData<>();
        apiService.reverseGeocode(lat, lon, apiKey).enqueue(new Callback<GeocodeResponse>() {
            @Override
            public void onResponse(Call<GeocodeResponse> call, Response<GeocodeResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GeocodeResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<List<GeocodeResponse>> forwardGeocode(String query, String apiKey) {
        MutableLiveData<List<GeocodeResponse>> data = new MutableLiveData<>();
        apiService.forwardGeocode(query, apiKey).enqueue(new Callback<List<GeocodeResponse>>() {
            @Override
            public void onResponse(Call<List<GeocodeResponse>> call, Response<List<GeocodeResponse>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<GeocodeResponse>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}

