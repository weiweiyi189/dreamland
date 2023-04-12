package com.example.dreamland.ui.auth;

import android.content.Intent;
import android.os.Build;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import com.example.dreamland.R;
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

    private long exitTime = 0;

    //两次返回，返回到home界面（System.exit决定是否退出当前界面，重新加载程序）
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
                //退出系统，不保存之前页面
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
        final TextView enroll = findViewById(R.id.tv_register);

        //登陆按键绑定事件
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

        //注册界面跳转
        enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, EnrollActivity.class);
                startActivity(intent);
            }
        });

//        //找回密码界面跳转
//        forgotPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(LoginActivity.this, ForgetActivity.class);
//                startActivity(intent);
//            }
//        });

    }

}