package com.abidzar.simplelistapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.abidzar.simplelistapp.data.model.User;
import com.abidzar.simplelistapp.data.repository.UserRepository;

import java.util.List;

public class UserViewModel extends ViewModel {
    private final UserRepository repository;

    public UserViewModel() {
        this(new UserRepository());
    }

    // Constructor for testing
    public UserViewModel(UserRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<User>> getUsers() {
        return repository.getUsers();
    }
}
