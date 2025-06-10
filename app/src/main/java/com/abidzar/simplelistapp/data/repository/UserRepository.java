package com.abidzar.simplelistapp.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.abidzar.simplelistapp.data.model.User;
import com.abidzar.simplelistapp.data.service.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRepository {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private ApiService apiService;

    public UserRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public MutableLiveData<List<User>> getUsers() {
        MutableLiveData<List<User>> data = new MutableLiveData<>();

        apiService.listUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

}
