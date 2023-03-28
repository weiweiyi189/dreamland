package com.example.dreamland.ui.auth;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.example.dreamland.databinding.ActivityLoginBinding;
import com.example.dreamland.entity.ChatGptResponse;
import com.example.dreamland.entity.User;

import com.example.dreamland.ui.dashboard.DashboardActivity;
import com.example.dreamland.ui.util.ProxiedHurlStack;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.color.DynamicColors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 登录界面
 */
public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

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