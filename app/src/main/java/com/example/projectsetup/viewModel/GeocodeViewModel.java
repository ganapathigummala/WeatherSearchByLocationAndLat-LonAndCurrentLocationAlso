package com.example.projectsetup.viewModel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


import com.example.projectsetup.data.GeocodeResponse;
import com.example.projectsetup.repo.GeocodeRepository;

import java.util.List;

public class GeocodeViewModel extends ViewModel {
    private final GeocodeRepository repository;

    public GeocodeViewModel() {
        repository = new GeocodeRepository();
    }

    public LiveData<GeocodeResponse> reverseGeocode(double lat, double lon, String apiKey) {
        return repository.reverseGeocode(lat, lon, apiKey);
    }

    public LiveData<List<GeocodeResponse>> forwardGeocode(String address, String apiKey) {
        return repository.forwardGeocode(address, apiKey);
    }
}

