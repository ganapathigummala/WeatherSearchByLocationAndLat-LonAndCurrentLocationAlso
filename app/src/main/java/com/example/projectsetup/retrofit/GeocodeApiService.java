package com.example.projectsetup.retrofit;


import com.example.projectsetup.data.GeocodeResponse;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeocodeApiService {

    @GET("reverse")
    Call<GeocodeResponse> reverseGeocode(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("api_key") String apiKey
    );

    @GET("search")
    Call<List<GeocodeResponse>> forwardGeocode(
            @Query("q") String query,
            @Query("api_key") String apiKey
    );
}

