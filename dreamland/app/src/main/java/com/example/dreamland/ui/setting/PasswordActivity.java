package com.example.dreamland.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.dreamland.R;
import com.example.dreamland.entity.User;
import com.example.dreamland.entity.VoUser;
import com.example.dreamland.service.BaseHttpService;
import com.example.dreamland.service.UserService;
import com.example.dreamland.ui.auth.EnrollActivity;
import com.example.dreamland.ui.auth.LoginActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class PasswordActivity extends AppCompatActivity {
    private final UserService userService = UserService.getInstance();
    private VoUser voUser = new VoUser();

    //android 捕获返回（后退）按钮事件
    public void onBackPressed() {
        Intent intent = new Intent(PasswordActivity.this,SettingActivity.class);
        startActivity(intent, null);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        //绑定返回监听按钮和返回
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PasswordActivity.this,SettingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        EditText npassword = findViewById(R.id.npassword);
        EditText opassword = findViewById(R.id.opassword);
        //导航栏发布和匿名的图片按钮监听
        topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.send:
                        System.out.println(npassword.getText());
                        System.out.println(opassword.getText());
                        voUser.setPassword(opassword.getText().toString());
                        voUser.setNewPassword(npassword.getText().toString());
                        // 加载个人信息
                        userService.updatePassword(new BaseHttpService.CallBack() {
                            @Override
                            public void onSuccess(BaseHttpService.CustomerResponse result) {
                                // 加载个人信息成功
                                if (result.getResponse().code() >= 200 && result.getResponse().code() < 300) {
                                    Intent intent = new Intent(PasswordActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(PasswordActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    // 登陆失败 提示错误
                                    Toast.makeText(PasswordActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },voUser);
                        break;
                }
                return true;
            }
        });
    }
}