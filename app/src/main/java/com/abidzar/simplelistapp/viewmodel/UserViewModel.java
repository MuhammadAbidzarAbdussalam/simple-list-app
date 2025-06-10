package com.abidzar.simplelistapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.abidzar.simplelistapp.data.model.User;
import com.abidzar.simplelistapp.data.repository.UserRepository;

import java.util.List;

public class UserViewModel extends ViewModel {
    private UserRepository repository;

    public UserViewModel() {
        repository = new UserRepository();
    }

    public LiveData<List<User>> getUsers() {
        return repository.getUsers();
    }
}
