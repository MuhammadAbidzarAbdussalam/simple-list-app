package com.abidzar.simplelistapp.ui.detail;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.abidzar.simplelistapp.R;

public class DetailActivity extends AppCompatActivity {

    private TextView txtUserId;
    private TextView txtId;
    private TextView txtTitle;
    private TextView txtBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initView();
        initBundle();
    }

    private void initView() {
        txtUserId = findViewById(R.id.txtUserId);
        txtId = findViewById(R.id.txtId);
        txtTitle = findViewById(R.id.txtTitle);
        txtBody = findViewById(R.id.txtBody);
    }

    private void initBundle() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            displayUserDetails(extras);
        }
    }

    private void displayUserDetails(Bundle extras) {
        String userId = "UserID: " + extras.getInt("userId");
        String id = "Id: " + extras.getInt("id");
        String title = "Title: " + extras.getString("title");
        String body = "Body: " + extras.getString("body");

        txtUserId.setText(userId);
        txtId.setText(id);
        txtTitle.setText(title);
        txtBody.setText(body);
    }
}