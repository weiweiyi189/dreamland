package com.example.dreamland.ui.auth;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dreamland.R;
import com.example.dreamland.entity.User;
import com.example.dreamland.service.BaseHttpService;
import com.example.dreamland.service.UserService;
import com.example.dreamland.ui.dashboard.DashboardActivity;

import static com.example.dreamland.service.UserService.userService;

public class EnrollActivity extends AppCompatActivity {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);

        //开启动画
        WindowInsetsController controller = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            controller = getWindow().getInsetsController();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            controller.hide(WindowInsets.Type.statusBars());
        }

        // 获取表单
        final EditText usernameEditText = findViewById(R.id.eusername);
        final EditText passwordEditText = findViewById(R.id.epassword);
        final Button enrollButton = findViewById(R.id.enroll);
        final TextView returnLogin = findViewById(R.id.return_login);

        //登陆按键绑定事件
        enrollButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        User user = new User();
                        user.setUsername(usernameEditText.getText().toString());
                        user.setPassword(passwordEditText.getText().toString());

                        // 注册
                        userService.regester(new BaseHttpService.CallBack() {
                            @Override
                            public void onSuccess(BaseHttpService.CustomerResponse result) {
                                // 注册成功
                                if (result.getResponse().code() >= 200 && result.getResponse().code() < 300) {
                                    // 启动首页
                                    Intent intent = new Intent(EnrollActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(EnrollActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    // 登陆失败 提示错误
                                    Toast.makeText(EnrollActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, user);

                    }
                }
        );

        //登陆界面跳转
        returnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EnrollActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}