package com.example.dreamland.ui.writeDream;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import com.example.dreamland.R;
import com.example.dreamland.databinding.ActivityWritedreamBinding;
import com.example.dreamland.db.initDataBase;
import com.example.dreamland.entity.Dream;
import com.example.dreamland.entity.User;
import com.example.dreamland.service.BaseHttpService;
import com.example.dreamland.service.DreamService;
import com.example.dreamland.ui.dashboard.DashboardActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;

import java.sql.Timestamp;
import java.util.Date;


public class WriteDreamActivity extends AppCompatActivity {

    private ActivityWritedreamBinding binding;


    NavigationBarView navigationView;

    private DreamService dreamService = DreamService.getInstance();

    private long exitTime = 0;

    private int mode = OPEN;
    static final int OPEN = 0;
    static final int ANONYMOUYS = 1;


    //android 捕获返回（后退）按钮事件
    public void onBackPressed() {
        finish();
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

        Menu menu = topAppBar.getMenu();
        //导航栏发布和匿名的图片按钮监听
        topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.duolaameng:
                        if (mode == OPEN) {
                            mode = ANONYMOUYS;
                            menu.getItem(0).setIcon(ContextCompat.getDrawable(WriteDreamActivity.this, R.drawable.paidaxing));
                            Toast.makeText(WriteDreamActivity.this, "匿名发布", Toast.LENGTH_SHORT).show();
                        } else {
                            mode = OPEN;
                            menu.getItem(0).setIcon(ContextCompat.getDrawable(WriteDreamActivity.this, R.drawable.haimianbaobao));
                            Toast.makeText(WriteDreamActivity.this, "公开发布", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.send:
                        final EditText context = binding.context;
                        Dream dream = new Dream();
                        dream.setContent(context.getText().toString());
                        User user = new User();
                        user.setUsername("匿名用户");
                        dream.setCreateUser(user);
                        dreamService.add(new BaseHttpService.CallBack() {
                            @Override
                            public void onSuccess(BaseHttpService.CustomerResponse result) {
                                // 登陆成功
                                if (result.getResponse().code() >= 200 && result.getResponse().code() < 300) {
                                    Toast.makeText(WriteDreamActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    // 登陆失败 提示错误
                                    Toast.makeText(WriteDreamActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
                                }
                                finish();
                            }
                        }, dream);

                        break;
                }
                return true;
            }
        });
    }

}