package com.abidzar.simplelistapp.data.service;

import com.abidzar.simplelistapp.data.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("/posts")
    Call<List<User>> listUsers();

}
