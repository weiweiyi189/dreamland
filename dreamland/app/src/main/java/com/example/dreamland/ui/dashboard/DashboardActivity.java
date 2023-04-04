package com.example.dreamland.ui.dashboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.dreamland.R;
import com.example.dreamland.databinding.ActivityDashboardBinding;
import com.example.dreamland.entity.Dream;
import com.example.dreamland.ui.adapter.DreamAdapter;
import com.example.dreamland.ui.chat.MessageListActivity;
import com.example.dreamland.ui.dreams.DreamsActivity;

import com.example.dreamland.ui.floater.FloaterActivity;
import com.google.android.material.color.DynamicColors;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;

import java.util.Comparator;
import java.util.Date;
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


    NavigationBarView navigationView;

    private long exitTime = 0;

    private SwipeRefreshLayout swipe_refresh;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DynamicColors.applyToActivityIfAvailable(this);
        super.onCreate(savedInstanceState);
        // 绑定v层
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navigationView = (NavigationBarView) findViewById(R.id.bottom_navigation);
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

        NavigationView navigationView = binding.NavigationView;
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
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
        this.initList();
        this.setRefresh();
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.getMenu().findItem(R.id.item_1).setChecked(true);
        swipe_refresh.post(new Runnable() {
            @Override
            public void run() {
                swipe_refresh.setRefreshing(true);
            }
        });
        this.refreshData();
    }

    public void initList() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        this.dreamAdapter = new DreamAdapter(this.dreams);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(this.dreamAdapter);
    }

    private void initDataAndSort() {
        List<Dream> dreamList = LitePal.findAll(Dream.class, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            dreamList.sort(Comparator.comparing(Dream::getCreateTime, new Comparator<Date>() {
                @Override
                public int compare(Date date, Date t1) {
                    if (date.getTime() < t1.getTime()) {
                        return 1;
                    }
                    return -1;
                }
            }));
        }
        this.dreams.clear();
        this.dreams.addAll(dreamList);
    }

    private void setRefresh() {
        swipe_refresh = findViewById(R.id.swipe_refresh);
        swipe_refresh.setColorSchemeResources(R.color.md_theme_light_primary);//设置下拉进度条颜色
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        swipe_refresh.post(new Runnable() {
            @Override
            public void run() {
                swipe_refresh.setRefreshing(true);
            }
        });
    }

    private void refreshData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void run() {
                        initDataAndSort();
                        dreamAdapter.notifyDataSetChanged();
                        swipe_refresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

}
