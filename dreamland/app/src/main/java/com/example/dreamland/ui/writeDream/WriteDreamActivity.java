package com.example.dreamland.ui.writeDream;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.dreamland.R;
import com.example.dreamland.databinding.ActivityWritedreamBinding;
import com.example.dreamland.db.initDataBase;
import com.example.dreamland.entity.Dream;
import com.example.dreamland.ui.dashboard.DashboardActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationBarView;

import java.sql.Timestamp;
import java.util.Date;


public class WriteDreamActivity extends AppCompatActivity {

    private ActivityWritedreamBinding binding;


    NavigationBarView navigationView;

    private long exitTime = 0;


    //android 捕获返回（后退）按钮事件
    public void onBackPressed() {
        Intent intent = new Intent(WriteDreamActivity.this, DashboardActivity.class);
        startActivity(intent, null);
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 绑定v层
        binding = ActivityWritedreamBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //绑定返回监听按钮
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //导航栏发布和匿名的图片按钮监听
        topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.duolaameng:
                        System.out.println("点击了哆啦A梦");
                        break;
                    case R.id.send:
                        final EditText context = binding.context;
                        Dream dream = new Dream();
                        dream.setContent(context.getText().toString());
                        dream.setCreateTime(new Timestamp(new Date().getTime()));
                        dream.setCreateUser(initDataBase.currentLoginUser);
                        dream.save();
                        Toast.makeText(WriteDreamActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                }
                return true;
            }
        });
    }

}