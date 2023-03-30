package com.example.dreamland.ui.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dreamland.R;
import com.example.dreamland.databinding.ActivityDashboardBinding;
import com.example.dreamland.databinding.ActivityPersonalBinding;
import com.example.dreamland.entity.Dream;
import com.example.dreamland.entity.User;
import com.example.dreamland.ui.adapter.DreamAdapter;
import com.example.dreamland.ui.chat.ChatActivity;
import com.example.dreamland.ui.dashboard.DashboardActivity;
import com.example.dreamland.ui.dreams.DreamsActivity;
import com.example.dreamland.ui.layout.NavigationBar;
import com.google.android.material.color.DynamicColors;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class PersonalActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    private DreamAdapter dreamAdapter;

    private ActivityPersonalBinding binding;

    private List<Dream> dreams = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        DynamicColors.applyToActivityIfAvailable(this);
        super.onCreate(savedInstanceState);
        // 绑定v层
        binding = ActivityPersonalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // 隐藏系统自带的标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
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

        //设置上部菜单的监听事件
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                System.out.println("选中了他");

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                System.out.println("没有选中他");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                System.out.println("重复选中了他");
            }
        });
    }


        public void initList () {
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