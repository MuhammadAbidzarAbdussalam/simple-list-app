package com.abidzar.simplelistapp.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.abidzar.simplelistapp.data.model.User;
import com.abidzar.simplelistapp.data.repository.FakeUserRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class UserViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private FakeUserRepository userRepository;
    private Observer<List<User>> observer;
    private UserViewModel userViewModel;
    private List<User> capturedUsers;
    private CountDownLatch latch;

    @Before
    public void setUp() {
        userRepository = new FakeUserRepository();
        userViewModel = new UserViewModel(userRepository);
        capturedUsers = new ArrayList<>();
        latch = new CountDownLatch(1);
        observer = new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                capturedUsers = users;
                latch.countDown();
            }
        };
    }

    @Test
    public void getUsers_shouldReturnListOfUsers() throws InterruptedException {
        // Given
        List<User> dummyUsers = new ArrayList<>();
        dummyUsers.add(new User(1, 1, "user.one@example.com", "avatar1.png"));
        dummyUsers.add(new User(1, 2, "user.two@example.com", "avatar2.png"));
        userRepository.setUsersToReturn(dummyUsers);

        // When
        LiveData<List<User>> actualUsers = userViewModel.getUsers();
        actualUsers.observeForever(observer);
        latch.await(2, TimeUnit.SECONDS);

        // Then
        assertNotNull(capturedUsers);
        assertEquals(dummyUsers.size(), capturedUsers.size());
        assertEquals(dummyUsers.get(0).getTitle(), capturedUsers.get(0).getTitle());
    }

    @Test
    public void getUsers_whenRepositoryReturnsNull_shouldReturnNull() throws InterruptedException {
        // Given
        userRepository.setUsersToReturn(null);

        // When
        LiveData<List<User>> actualUsers = userViewModel.getUsers();
        actualUsers.observeForever(observer);
        latch.await(2, TimeUnit.SECONDS);

        // Then
        assertNull(capturedUsers);
    }

    @Test
    public void getUsers_whenRepositoryReturnsEmptyList_shouldReturnEmptyList() throws InterruptedException {
        // Given
        List<User> emptyList = new ArrayList<>();
        userRepository.setUsersToReturn(emptyList);

        // When
        LiveData<List<User>> actualUsers = userViewModel.getUsers();
        actualUsers.observeForever(observer);
        latch.await(2, TimeUnit.SECONDS);

        // Then
        assertNotNull(capturedUsers);
        assertEquals(0, capturedUsers.size());
    }

    @Test
    public void getUsers_whenLiveDataUpdatedMultipleTimes_shouldUpdateObserver() throws InterruptedException {
        // Given
        List<User> firstList = new ArrayList<>();
        firstList.add(new User(1, 1, "user.one@example.com", "avatar1.png"));
        
        List<User> secondList = new ArrayList<>();
        secondList.add(new User(2, 2, "user.two@example.com", "avatar2.png"));
        
        LiveData<List<User>> actualUsers = userViewModel.getUsers();
        actualUsers.observeForever(observer);

        // When
        userRepository.setUsersToReturn(firstList);
        latch.await(2, TimeUnit.SECONDS);
        latch = new CountDownLatch(1);
        userRepository.setUsersToReturn(secondList);
        latch.await(2, TimeUnit.SECONDS);

        // Then
        assertNotNull(capturedUsers);
        assertEquals(secondList.size(), capturedUsers.size());
        assertEquals(secondList.get(0).getTitle(), capturedUsers.get(0).getTitle());
    }
}