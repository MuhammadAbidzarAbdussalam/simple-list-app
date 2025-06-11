package com.abidzar.simplelistapp.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.abidzar.simplelistapp.data.model.User;

import java.util.List;

public class FakeUserRepository extends UserRepository {

    private final MutableLiveData<List<User>> usersLiveData = new MutableLiveData<>();
    private List<User> usersToReturn;

    public void setUsersToReturn(List<User> users) {
        this.usersToReturn = users;
        // Post value to LiveData so observers are notified
        usersLiveData.postValue(users);
    }

    @Override
    public MutableLiveData<List<User>> getUsers() {
        // Simulate fetching data, in a real scenario this might involve network calls or database queries.
        // For this fake, we just return the LiveData which might already have data or will get it via setUsersToReturn.
        if (usersToReturn != null && usersLiveData.getValue() == null) {
            usersLiveData.setValue(usersToReturn);
        }
        return usersLiveData;
    }
}