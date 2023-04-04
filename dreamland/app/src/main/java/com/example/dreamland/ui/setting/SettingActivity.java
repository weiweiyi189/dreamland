package com.example.dreamland.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dreamland.R;
import com.example.dreamland.ui.dashboard.DashboardActivity;
import com.example.dreamland.ui.dreams.DreamsActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class SettingActivity extends AppCompatActivity {

//    private long exitTime = 0;
//    //两次返回，返回到home界面（System.exit决定是否退出当前界面，重新加载程序）
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode == android.view.KeyEvent.KEYCODE_BACK && event.getAction() == android.view.KeyEvent.ACTION_DOWN){
//
//            if((System.currentTimeMillis()-exitTime) > 2000){
//                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
//                exitTime = System.currentTimeMillis();
//            } else {
//                Intent home = new Intent(Intent.ACTION_MAIN);
//                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                home.addCategory(Intent.CATEGORY_HOME);
//                startActivity(home);
//                //退出系统，不保存之前页面
//                System.exit(0);
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //导航栏返回按钮
        MaterialToolbar topAppBar=findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, DashboardActivity.class);
                startActivity(intent, null);
            }
        });
    }
}