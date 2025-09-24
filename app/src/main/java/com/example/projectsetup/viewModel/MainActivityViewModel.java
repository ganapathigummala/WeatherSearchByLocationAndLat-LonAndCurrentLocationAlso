package com.example.projectsetup.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projectsetup.data.WeatherResponse;
import com.example.projectsetup.repo.WeatherRepository;

public class MainActivityViewModel extends ViewModel {
    private  WeatherRepository repository;

    private  MutableLiveData<WeatherResponse> weatherData = new MutableLiveData<>();

    public MainActivityViewModel() {
        repository = new WeatherRepository();
    }

    public void fetchWeather(double lat, double lon, String apiKey) {
        LiveData<WeatherResponse> responseLiveData = repository.getWeather(lat, lon, apiKey);
        responseLiveData.observeForever(weatherResponse -> {
            if (weatherResponse != null) {
                weatherData.setValue(weatherResponse);
            }
        });
    }

    public LiveData<WeatherResponse> getWeatherData() {
        return weatherData;
    }
}
