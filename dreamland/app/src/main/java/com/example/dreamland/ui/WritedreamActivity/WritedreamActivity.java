package com.example.dreamland.ui.WritedreamActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.dreamland.R;
import com.example.dreamland.databinding.ActivityWritedreamBinding;
import com.example.dreamland.ui.chat.MessageListActivity;
import com.example.dreamland.ui.dashboard.DashboardActivity;
import com.example.dreamland.ui.dreams.DreamsActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import org.jetbrains.annotations.NotNull;


public class WritedreamActivity extends AppCompatActivity{

    private ActivityWritedreamBinding binding;

    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 绑定v层
        binding = ActivityWritedreamBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //绑定返回监听按钮
//        Toolbar myToolbar = findViewById(R.id.topAppBar);

        MaterialToolbar topAppBar=findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WritedreamActivity.this, DashboardActivity.class);
                startActivity(intent, null);
            }
        });

        topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.duolaameng:
                        System.out.println("点击了哆啦A梦");
                        break;
                    case R.id.send:
                        System.out.println("点击了发送");
                        break;
                }
                return true;
            }
        });

    }


    //两次返回，返回到home界面（System.exit决定是否退出当前界面，重新加载程序）
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == android.view.KeyEvent.KEYCODE_BACK && event.getAction() == android.view.KeyEvent.ACTION_DOWN){

            if((System.currentTimeMillis()-exitTime) > 2000){
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

    }