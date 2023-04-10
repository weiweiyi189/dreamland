package com.example.dreamland.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.dreamland.R;
import com.google.android.material.appbar.MaterialToolbar;

public class EmailActivity extends AppCompatActivity {

    //android 捕获返回（后退）按钮事件
    public void onBackPressed() {
        Intent intent = new Intent(EmailActivity.this,SettingActivity.class);
        startActivity(intent, null);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        //绑定返回监听按钮和返回
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmailActivity.this,SettingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        EditText nemail = findViewById(R.id.nemail);
        //导航栏发布和匿名的图片按钮监听
        topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.send:
                        System.out.println(nemail.getText());
                        break;
                }
                return true;
            }
        });
    }
}