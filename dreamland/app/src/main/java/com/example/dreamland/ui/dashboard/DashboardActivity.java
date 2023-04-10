package com.example.dreamland.ui.dashboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.RelativeLayout;
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
import com.example.dreamland.entity.User;
import com.example.dreamland.service.BaseHttpService;
import com.example.dreamland.service.DownloadImageTask;
import com.example.dreamland.service.DreamService;
import com.example.dreamland.service.UserService;
import com.example.dreamland.ui.adapter.ClickListener;
import com.example.dreamland.ui.adapter.DreamAdapter;
import com.example.dreamland.ui.chat.MessageListActivity;
import com.example.dreamland.ui.dreams.DreamsActivity;
import com.example.dreamland.ui.setting.SettingActivity;
import com.example.dreamland.ui.floater.FloaterActivity;
import com.google.android.material.color.DynamicColors;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import de.hdodenhof.circleimageview.CircleImageView;
import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;

import java.util.*;

/**
 * 首页
 */
public class DashboardActivity extends AppCompatActivity {

    private final UserService userService = UserService.getInstance();

    private DrawerLayout drawerLayout;

    private DreamAdapter dreamAdapter;

    private ActivityDashboardBinding binding;

    private List<Dream> dreams = new LinkedList<>();


    NavigationBarView navigationView;

    private long exitTime = 0;

    private SwipeRefreshLayout swipe_refresh;

    RecyclerView recyclerView;

    private DreamService dreamService = DreamService.getInstance();;

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

                //加载头像
                CircleImageView headshott =  findViewById(R.id.headshot);
                userService.getCurrentUser(new BaseHttpService.CallBack() {
                    @Override
                    public void onSuccess(BaseHttpService.CustomerResponse result) {
                        //获取当前登陆用户
                        User currentUser= (User) result.getData();
                        if (result.getResponse().code() >= 200 && result.getResponse().code() < 300) {
                            String urlString = BaseHttpService.BASE_URL + currentUser.getImageUrl();
                            new DownloadImageTask(headshott)
                                    .execute(urlString);
                        } else {
                            Toast.makeText(DashboardActivity.this, "头像加载失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
                        overridePendingTransition(0,0);
                        break;
                    case R.id.sleep:
                        share();
                        break;
                    case R.id.dreams:
                        drawerLayout.close();
                        Intent intent2 = new Intent(DashboardActivity.this, DreamsActivity.class);
                        startActivity(intent2, null);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.setting:
                        drawerLayout.close();
                        Intent intent3 = new Intent(DashboardActivity.this, SettingActivity.class);
                        startActivity(intent3, null);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.floater:
                        drawerLayout.close();
                        Intent intent4 = new Intent(DashboardActivity.this, FloaterActivity.class);
                        startActivity(intent4, null);
                        break;
                }
                return true;
            }
        });
        this.initList();
        this.setRefresh();}


    /**
     * 点击进行分享
     */
    public void share(){
        // 设置要分享的内容
        String shareContent="#神码工作室#博客地址：https://blog.csdn.net/qq15577969";
        SharePopupWindow spw = new SharePopupWindow(this, shareContent);
        // 显示窗口
        spw.showAtLocation(drawerLayout, Gravity.BOTTOM, 0, 0);
    }

    public void OnItemClick(View view){
        // 获取itemView的位置
        int position = recyclerView.getChildAdapterPosition(view);
        Intent intent = new Intent(DashboardActivity.this, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("dream", dreams.get(position));
        intent.putExtras(bundle);
        startActivity(intent, null);
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
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        this.dreamAdapter = new DreamAdapter(this.dreams, new ClickListener() {
            @Override public void onPositionClicked(int position) {

            }

        });
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(this.dreamAdapter);
    }


    private void initDataAndSort() {
        dreamService.getAll(new BaseHttpService.CallBack() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(BaseHttpService.CustomerResponse result) {
                List<Dream> dreamList = new ArrayList<>(Arrays.asList((Dream[]) result.getData()));
                dreams.clear();
                dreams.addAll(dreamList);
                dreamAdapter.notifyDataSetChanged();
            }
        });
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
                        swipe_refresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

}
