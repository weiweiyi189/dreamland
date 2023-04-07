package com.example.dreamland.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dreamland.R;
import com.example.dreamland.entity.User;
import com.example.dreamland.service.BaseHttpService;
import com.example.dreamland.service.UserService;
import com.example.dreamland.ui.auth.EnrollActivity;
import com.example.dreamland.ui.auth.LoginActivity;
import com.example.dreamland.ui.dashboard.DashboardActivity;
import com.example.dreamland.ui.dreams.DreamsActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class SettingActivity extends AppCompatActivity {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //导航栏返回按钮
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, DashboardActivity.class);
                startActivity(intent, null);
            }
        });

        LinearLayout avatar = findViewById(R.id.avatar);
        LinearLayout account = findViewById(R.id.account);
        LinearLayout email = findViewById(R.id.email);
        LinearLayout password = findViewById(R.id.password);
        LinearLayout about = findViewById(R.id.about);
        LinearLayout exit = findViewById(R.id.exit);

        TextView accounts = findViewById(R.id.accounts);
        TextView emails = findViewById(R.id.emails);



        // 加载个人信息
        userService.getCurrentUser(new BaseHttpService.CallBack() {
            @Override
            public void onSuccess(BaseHttpService.CustomerResponse result) {
                //获取当前登陆用户
                User currentUser= (User) result.getData();
                System.out.println(currentUser.getUsername());
                // 加载个人信息成功
                if (result.getResponse().code() >= 200 && result.getResponse().code() < 300) {
                    System.out.println(currentUser.getImageUrl());
                    accounts.append("        "+currentUser.getUsername());
                    emails.append("        "+currentUser.getUsername());
                } else {
                    // 登陆失败 提示错误
                    Toast.makeText(SettingActivity.this, "信息预加载失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //修改信息绑定
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, AccountActivity.class);
                startActivity(intent);
                finish();
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, EmailActivity.class);
                startActivity(intent);
                finish();
            }
        });
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, PasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //关于我们
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, AboutActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //退出登陆
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}