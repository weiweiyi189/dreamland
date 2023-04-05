package com.example.dreamland.ui.auth;

import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.example.dreamland.databinding.ActivityLoginBinding;
import com.example.dreamland.entity.User;

import com.example.dreamland.service.BaseHttpService;
import com.example.dreamland.service.UserService;
import com.example.dreamland.ui.dashboard.DashboardActivity;
import com.google.android.material.color.DynamicColors;
import com.google.android.material.snackbar.Snackbar;

/**
 * 登录界面
 */
public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private final UserService userService = UserService.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        DynamicColors.applyToActivityIfAvailable(this);
        super.onCreate(savedInstanceState);
        // 绑定v层
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // 获取表单
        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;

        loginButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println("登陆成功");
                        User user = new User();
                        user.setUsername(usernameEditText.getText().toString());
                        user.setPassword(passwordEditText.getText().toString());

                        // 登录示例
                        userService.login(new BaseHttpService.CallBack() {
                            @Override
                            public void onSuccess(BaseHttpService.CustomerResponse result) {
                                // 登陆成功
                                if (result.getResponse().code() >= 200 && result.getResponse().code() < 300) {
                                    // 存储token 用户名 密码
                                    String token = result.getResponse().header(UserService.TOKEN_HEADER);
                                    BaseHttpService.setToken(token);
                                    userService.currentUser.onNext((User) result.getData());
                                    // 启动首页
                                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    // 登陆失败 提示错误
                                    Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, user);

                    }
                }
        );

    }

}