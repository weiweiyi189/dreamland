package com.example.dreamland.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dreamland.R;
import com.example.dreamland.databinding.ActivityDashboardBinding;
import com.example.dreamland.entity.Dream;
import com.example.dreamland.entity.User;
import com.example.dreamland.ui.adapter.DreamAdapter;
import com.example.dreamland.ui.chat.MessageListActivity;
import com.example.dreamland.ui.dreams.DreamsActivity;

import com.example.dreamland.ui.floater.FloaterActivity;
import com.google.android.material.color.DynamicColors;
import com.google.android.material.navigation.NavigationView;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

/**
 * 首页
 */
public class DashboardActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    private DreamAdapter dreamAdapter;

    private ActivityDashboardBinding binding;

    private List<Dream> dreams = new LinkedList<>();

    private long exitTime = 0;
    //两次返回，返回到home界面（System.exit决定是否退出当前界面，重新加载程序）
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DynamicColors.applyToActivityIfAvailable(this);
        super.onCreate(savedInstanceState);
        // 绑定v层
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // 隐藏系统自带的标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        //设置不可再次点击
        findViewById(R.id.item_1).setClickable(false);

        // 设置侧边栏
        binding.topAppBar.setNavigationIcon(R.drawable.menu);
        // 设置navigation button 点击事件
        binding.topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        this.initList();

        NavigationView navigationView = binding.NavigationView;
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                int id = item.getItemId();
                switch(id){
                    case R.id.interpret_dream:
                        drawerLayout.close();
                        Intent intent1 = new Intent(DashboardActivity.this, MessageListActivity.class);
                        startActivity(intent1, null);
                        break;
                    case R.id.dreams:
                        drawerLayout.close();
                        Intent intent2 = new Intent(DashboardActivity.this, DreamsActivity.class);
                        startActivity(intent2, null);
                        break;
                    case R.id.floater:
                        drawerLayout.close();
                        Intent intent3 = new Intent(DashboardActivity.this, FloaterActivity.class);
                        startActivity(intent3, null);
                        break;
                }
                return true;
            }
        });

    }

    public void initList() {
        User user = new User();
        user.setUsername("用户12345985");

        Dream dream = new Dream();
        dream.setContent("梦到世界大危机，反派与正派对峙，正派死伤惨重，\n" +
                "几个主角都被抓。而我被委托以重要使命，跳井穿越时空拿到重要宝物压制反派.结果时间乱流寄了。");
        dream.setCreateTime(new Timestamp(1679383693000L));
        dream.setId(1L);
        dream.setCreateUser(user);

        for (int i = 0; i < 10; i++) {
            this.dreams.add(dream);
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        this.dreamAdapter = new DreamAdapter(this.dreams);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(this.dreamAdapter);
    }

}
