package com.example.projectsetup.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.projectsetup.R;
import com.example.projectsetup.data.GeocodeResponse;
import com.example.projectsetup.data.WeatherResponse;
import com.example.projectsetup.databinding.GeocodeMainBinding;
import com.example.projectsetup.viewModel.GeocodeViewModel;
import com.example.projectsetup.viewModel.MainActivityViewModel;

public class GeocodeActivity extends AppCompatActivity {

    private GeocodeMainBinding binding;
    private GeocodeViewModel geocodeViewModel;
    private MainActivityViewModel weatherViewModel;
    private static final String API_KEY_WEATHER = "";

    private static final String API_KEY = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Use DataBinding
        binding = DataBindingUtil.setContentView(this, R.layout.geocode_main);

        // Init ViewModels
        geocodeViewModel = new ViewModelProvider(this).get(GeocodeViewModel.class);
        weatherViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        // Observe weather data (one-time)
        weatherViewModel.getWeatherData().observe(this, weatherResponse -> {
            if (weatherResponse != null) {
                String result = weatherResponse.getMain().getTemp() + "°C in " + weatherResponse.getCityName();
                binding.resultView.setText(result);
            } else {
                binding.resultView.setText("Failed to load weather data");
            }
        });

        // Reverse Geocoding Button Click
        binding.reverseBtn.setOnClickListener(v -> {
            String latStr = binding.latInput.getText().toString().trim();
            String lonStr = binding.lonInput.getText().toString().trim();

            if (!latStr.isEmpty() && !lonStr.isEmpty()) {
                double lat = Double.parseDouble(latStr);
                double lon = Double.parseDouble(lonStr);

                geocodeViewModel.reverseGeocode(lat, lon, API_KEY).observe(this, response -> {
                    if (response != null) {
                        binding.resultView.setText("Address: " + response.getDisplayName());
                    } else {
                        binding.resultView.setText("Reverse Geocoding Failed");
                    }
                });
            } else {
                Toast.makeText(this, "Enter both Latitude and Longitude", Toast.LENGTH_SHORT).show();
            }
        });

        binding.forwardBtn.setOnClickListener(v -> {
            String address = binding.addressInput.getText().toString().trim();

            if (!address.isEmpty()) {
                geocodeViewModel.forwardGeocode(address, API_KEY).observe(this, responses -> {
                    if (responses != null && !responses.isEmpty()) {
                        GeocodeResponse first = responses.get(0);
                        double lat = Double.parseDouble(first.getLat());
                        double lon = Double.parseDouble(first.getLon());

                        weatherViewModel.fetchWeather(lat, lon, API_KEY_WEATHER);
                    } else {
                        binding.resultView.setText("Forward Geocoding Failed");
                    }
                });
            } else {
                Toast.makeText(this, "Enter a valid address", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
