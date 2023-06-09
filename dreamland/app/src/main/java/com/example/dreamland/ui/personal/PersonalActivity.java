package com.example.dreamland.ui.personal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
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

import com.example.dreamland.databinding.ActivityPersonalBinding;
import com.example.dreamland.entity.Dream;
import com.example.dreamland.service.*;
import com.example.dreamland.ui.adapter.ClickListener;
import com.example.dreamland.ui.adapter.DreamAdapter;
import com.example.dreamland.ui.chat.MessageListActivity;
import com.example.dreamland.ui.dashboard.DashboardActivity;
import com.example.dreamland.ui.dashboard.DetailActivity;
import com.example.dreamland.ui.dashboard.PersonalDreamsActivity;
import com.example.dreamland.ui.dashboard.SharePopupWindow;
import com.example.dreamland.ui.dreams.DreamsActivity;
import com.example.dreamland.ui.dreamtest.DreamTestActivity;
import com.example.dreamland.ui.floater.FloaterActivity;
import com.example.dreamland.ui.setting.SettingActivity;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.color.DynamicColors;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import de.hdodenhof.circleimageview.CircleImageView;
import org.jetbrains.annotations.NotNull;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.example.dreamland.service.UserService.setCollectDreamToCurrentUser;

public class PersonalActivity extends AppCompatActivity {
    private final UserService userService = UserService.getInstance();

    private DrawerLayout drawerLayout;

    private DreamAdapter dreamAdapter;

    private ActivityPersonalBinding binding;

    private List<Dream> dreams = new LinkedList<>();

    private SwipeRefreshLayout swipe_refresh;

    RecyclerView recyclerView;

    private final DreamService dreamService = DreamService.getInstance();

    private long exitTime = 0;

    NavigationBarView navigationView;

    static final String MY = "my";

    static final String COLLECT = "collect";

    String currentModel = MY;

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

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        DynamicColors.applyToActivityIfAvailable(this);
        super.onCreate(savedInstanceState);
        // 绑定v层
        binding = ActivityPersonalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navigationView = (NavigationBarView) findViewById(R.id.bottom_navigation);

        //设置下部图标跟随选中
        BottomNavigationItemView menuItem = findViewById(R.id.item_3);
        menuItem.setChecked(true);
        BottomNavigationItemView menuItem1 = findViewById(R.id.item_1);
        menuItem1.setChecked(false);

        //设置不可再次点击
        menuItem.setClickable(false);

        //解决分享bug
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout2);
        drawerLayout.openDrawer(GravityCompat.START);
        drawerLayout.close();

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
                drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout2);
                drawerLayout.openDrawer(GravityCompat.START);
                CircleImageView headshot = findViewById(R.id.headshot);
                userService.loadCurrentUserImage(headshot);
            }
        });

        //设置上部菜单的监听事件
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getContentDescription().toString().equals(MY)) {
                    currentModel = MY;
                } else if (tab.getContentDescription().toString().equals(COLLECT)) {
                    currentModel = COLLECT;
                }
                swipe_refresh.post(new Runnable() {
                    @Override
                    public void run() {
                        swipe_refresh.setRefreshing(true);
                    }
                });
                refreshData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                System.out.println("没有选中他，，id是" + tab.getId());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                System.out.println("重复选中了他");
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
                        Intent intent1 = new Intent(PersonalActivity.this, MessageListActivity.class);
                        startActivity(intent1, null);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.sleep:
                        drawerLayout.close();
                        break;
                    case R.id.dreams:
                        drawerLayout.close();
                        Intent intent2 = new Intent(PersonalActivity.this, DreamsActivity.class);
                        startActivity(intent2, null);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.setting:
                        drawerLayout.close();
                        Intent intent3 = new Intent(PersonalActivity.this, SettingActivity.class);
                        startActivity(intent3, null);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.floater:
                        drawerLayout.close();
                        Intent intent4 = new Intent(PersonalActivity.this, FloaterActivity.class);
                        startActivity(intent4, null);
                        break;
                    case R.id.dream_test:
                        drawerLayout.close();
                        startActivity(new Intent(PersonalActivity.this, DreamTestActivity.class), null);
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
    }


    public void initList() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        this.dreamAdapter = new DreamAdapter(this.dreams, new ClickListener() {
            @Override
            public void onPositionClicked(int position, View view) {
                if (view.getId() == R.id.share) {
                    share("您的好友：" + userService.currentUser.getValue().getUsername() + "\n给您分享了一个有趣的梦境：\n" + dreams.get(position).getContent().toString() + "\n\n来自伯奇·梦境分享");
                } else if (view.getId() == R.id.favorite) {
                    userService.likeDream(new BaseHttpService.CallBack() {
                        @Override
                        public void onSuccess(BaseHttpService.CustomerResponse result) {
                            Dream resultDream = (Dream) result.getData();
                            dreams.get(position).setLikes(resultDream.getLikes());
                            setCollectDreamToCurrentUser(resultDream);
                            dreamAdapter.notifyDataSetChanged();
                        }
                    }, dreams.get(position));
                } else if (view.getId() == R.id.more) {

                } else if (view.getId() == R.id.more) {
                    onImageClick(position);
                } else {
                    onItemClick(position);
                }
            }
        });
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(this.dreamAdapter);
    }

    private void initDataAndSort() {
        switch (currentModel) {
            case MY:
                this.getAllByCurrentUser();
                break;
            case COLLECT:
                this.getCollectDream();
                break;
        }
    }

    private void onImageClick(int position) {
        Intent intent = new Intent(PersonalActivity.this, PersonalDreamsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", dreams.get(position).getCreateUser());
        intent.putExtras(bundle);
        startActivity(intent, null);
    }

    void getAllByCurrentUser() {
        dreamService.getAllByCurrentUser(new BaseHttpService.CallBack() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(BaseHttpService.CustomerResponse result) {
                DashboardActivity.updateItem(result, dreams, dreamAdapter);
                swipe_refresh.setRefreshing(false);
            }
        });
    }

    void getCollectDream() {
        dreamService.getCollectDream(new BaseHttpService.CallBack() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(BaseHttpService.CustomerResponse result) {
                DashboardActivity.updateItem(result, dreams, dreamAdapter);
                swipe_refresh.setRefreshing(false);
            }
        });
    }

    /**
     * 跳转详细页面
     *
     * @param position
     */
    public void onItemClick(int position) {
        Intent intent = new Intent(PersonalActivity.this, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("dream", dreams.get(position));
        intent.putExtras(bundle);
        startActivity(intent, null);
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
        this.refreshData();
    }

    private void refreshData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void run() {
                        initDataAndSort();
                    }
                });
            }
        }).start();
    }

    public void share(String str) {
        SharePopupWindow spw = new SharePopupWindow(this, str);
        // 显示窗口
        spw.showAtLocation(drawerLayout, Gravity.BOTTOM, 0, 0);
    }


}
