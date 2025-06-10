package com.abidzar.simplelistapp.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abidzar.simplelistapp.R;
import com.abidzar.simplelistapp.data.model.User;
import com.abidzar.simplelistapp.ui.adapter.UserAdapter;
import com.abidzar.simplelistapp.ui.detail.DetailActivity;
import com.abidzar.simplelistapp.viewmodel.UserViewModel;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private UserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new UserAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        viewModel.getUsers().observe(this, users -> adapter.setUsers(users));

        adapter.setOnClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("userId", adapter.getItem(position).getUserId());
            intent.putExtra("id", adapter.getItem(position).getId());
            intent.putExtra("title", adapter.getItem(position).getTitle());
            intent.putExtra("body", adapter.getItem(position).getBody());
            startActivity(intent);
        });

        viewModel.getUsers().observe(this, users -> {
            if (users != null)
                adapter.setUsers(users);
        });

    }
}