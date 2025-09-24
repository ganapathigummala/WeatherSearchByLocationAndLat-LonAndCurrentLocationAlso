package com.example.projectsetup;

import static java.util.Objects.isNull;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.projectsetup.Activity.GeocodeActivity;
import com.example.projectsetup.data.WeatherResponse;
import com.example.projectsetup.databinding.ActivityMainBinding;
import com.example.projectsetup.viewModel.MainActivityViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private static final String API_KEY = "";
    private Observer<WeatherResponse> data;
    private TextView textView;
    private MainActivityViewModel weatherViewModel;
    private FusedLocationProviderClient fusedLocationClient;
    private ActivityMainBinding mainActivityBinding;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        mainActivityBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        textView = mainActivityBinding.textView;
         button = mainActivityBinding.button;

        weatherViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        button.setOnClickListener(v -> checkLocationPermission());
        mainActivityBinding.search.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), GeocodeActivity.class);
            startActivity(intent);
        });

        if (isNull(data)) {
            data = new Observer<>() {
                @Override
                public void onChanged(WeatherResponse weatherResponse) {
                    if (weatherResponse != null) {
                        Log.d("asdf",weatherResponse+" ");
                        String temp = weatherResponse.getMain().getTemp() + "°C in " + weatherResponse.getCityName();
                        textView.setText(temp);
                    } else {
                        textView.setText("Failed to load weather data");
                    }
                }
            };
            weatherViewModel.getWeatherData().observe(this, data);
        }
    }

    private void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE
            );
        } else {
            fetchLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE &&
                grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            fetchLocation();
        } else {
            Toast.makeText(this, "Location permission denied!", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    private void fetchLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        double lat = location.getLatitude();
                        double lon = location.getLongitude();
                        weatherViewModel.fetchWeather(lat, lon, API_KEY);
                    } else {
                        textView.setText("Location not available");
                    }
                });
    }
}