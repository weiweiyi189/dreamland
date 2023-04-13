package com.example.dreamland.ui.dreamtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.dreamland.R;
import com.google.android.material.appbar.MaterialToolbar;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initView();
    }

    private void initView() {
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView tv_name = findViewById(R.id.tv_name);
        String name = getIntent().getStringExtra("name");
        tv_name.setText(name);
    }
}