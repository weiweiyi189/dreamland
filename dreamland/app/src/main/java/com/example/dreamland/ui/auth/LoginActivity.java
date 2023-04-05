package com.example.dreamland.ui.auth;

import android.content.Intent;
import android.os.Build;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import com.example.dreamland.R;
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


    //android 捕获返回（后退）按钮事件
    public void onBackPressed() {
        overridePendingTransition(0,0);
        finish();
    }

    private final UserService userService = UserService.getInstance();

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