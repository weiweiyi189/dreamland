package com.example.dreamland.ui.auth;

import android.content.Intent;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.example.dreamland.R;
import com.example.dreamland.entity.User;

import com.example.dreamland.ui.dashboard.DashboardActivity;

/**
 * 登录界面
 */
public class LoginActivity extends AppCompatActivity {


    //android 捕获返回（后退）按钮事件
    public void onBackPressed() {
        overridePendingTransition(0,0);
        finish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // 绑定v层
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);

        //开启动画
        WindowInsetsController controller = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            controller = getWindow().getInsetsController();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            controller.hide(WindowInsets.Type.statusBars());
        }

        // 获取表单
        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);

        loginButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println("登陆成功");
                        User user = new User();
                        user.setUsername(usernameEditText.toString());
                        user.setPassword(passwordEditText.toString());
                        Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                        // 启动首页
                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

}