package com.example.dreamland.ui.setting;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dreamland.R;
import com.google.android.material.appbar.MaterialToolbar;

public class AboutActivity extends AppCompatActivity {

    //android 捕获返回（后退）按钮事件
    public void onBackPressed() {
        Intent intent = new Intent(AboutActivity.this, SettingActivity.class);
        startActivity(intent, null);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        TextView textView = findViewById(R.id.about);
        String str = "<br>梦境中有个更真实的你<br>你的愿望，你的担心<br>你的邪恶，你的天真<br>在这里保存它<br>某一天重现它<br><br><br>" +
                "纷纷扰扰的世界中<br>我们只想做些直指人心的事<br>真实的<br>不虚伪的<br>不矫作的<br>以及<br>不必小心的<br><br><br><small>用户宝宝QQ群：2823316458</small><br><small><small>创作团队：车佳瑞 韦卫毅 李金宇 葛嘉宽 胡杨 李婷婷<small><small>";
        textView.setText(Html.fromHtml(str));

        //绑定返回监听按钮和返回
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this, SettingActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}