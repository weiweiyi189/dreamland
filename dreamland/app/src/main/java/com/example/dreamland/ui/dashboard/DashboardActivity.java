package com.example.dreamland.ui.dashboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.*;
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
import com.example.dreamland.entity.DreamComment;
import com.example.dreamland.entity.User;
import com.example.dreamland.service.*;
import com.example.dreamland.ui.adapter.ClickListener;
import com.example.dreamland.ui.adapter.DreamAdapter;
import com.example.dreamland.ui.chat.MessageListActivity;
import com.example.dreamland.ui.dreams.DreamsActivity;
import com.example.dreamland.ui.dreamtest.DreamTestActivity;
import com.example.dreamland.ui.music.MainActivity;
import com.example.dreamland.ui.setting.SettingActivity;
import com.example.dreamland.ui.floater.FloaterActivity;
import com.google.android.material.color.DynamicColors;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import de.hdodenhof.circleimageview.CircleImageView;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static com.example.dreamland.service.DreamCommentService.UIHandler;
import static com.example.dreamland.service.UserService.setCollectDreamToCurrentUser;

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

    private DreamService dreamService = DreamService.getInstance();

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


        //解决分享bug
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerLayout.openDrawer(GravityCompat.START);
        drawerLayout.close();


        // 设置侧边栏
        binding.topAppBar.setNavigationIcon(R.drawable.menu);
        // 设置navigation button 点击事件
        binding.topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
                loadCurrentUserImage();
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
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.sleep:
                        drawerLayout.close();
                        Intent intent5 = new Intent(DashboardActivity.this, MainActivity.class);
                        startActivity(intent5, null);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.dreams:
                        drawerLayout.close();
                        Intent intent2 = new Intent(DashboardActivity.this, DreamsActivity.class);
                        startActivity(intent2, null);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.setting:
                        drawerLayout.close();
                        Intent intent3 = new Intent(DashboardActivity.this, SettingActivity.class);
                        startActivity(intent3, null);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.floater:
                        drawerLayout.close();
                        Intent intent4 = new Intent(DashboardActivity.this, FloaterActivity.class);
                        startActivity(intent4, null);
                        break;
                    case R.id.dream_test:
                        drawerLayout.close();
                        startActivity(new Intent(DashboardActivity.this, DreamTestActivity.class), null);
                        break;
                }
                return true;
            }
        });
        this.initList();
        this.setRefresh();
    }

    private void loadCurrentUserImage() {
        CircleImageView headshot = findViewById(R.id.headshot);
        User newUser = new NewDownloadTask(userService.currentUser.getValue()).download();
        headshot.setImageBitmap(newUser.getImage());
        userService.currentUser.onNext(newUser);
    }

    /**
     * 点击进行分享
     */
    public void share(String str) {
        SharePopupWindow spw = new SharePopupWindow(this, str);
        // 显示窗口
        spw.showAtLocation(drawerLayout, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 跳转详细页面
     *
     * @param position
     */
    public void onItemClick(int position) {
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
    }

    public void initList() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        this.dreamAdapter = new DreamAdapter(this.dreams, new ClickListener() {
            @Override
            public void onPositionClicked(int position, View view) {
                if (view.getId() == R.id.favorite) {
                    userService.likeDream(new BaseHttpService.CallBack() {
                        @Override
                        public void onSuccess(BaseHttpService.CustomerResponse result) {
                            Dream resultDream = (Dream) result.getData();
                            dreams.get(position).setLikes(resultDream.getLikes());
                            setCollectDreamToCurrentUser(resultDream);
                            dreamAdapter.notifyDataSetChanged();
                        }
                    }, dreams.get(position));
                } else if (view.getId() == R.id.share) {
                    share("您的好友：" + userService.currentUser.getValue().getUsername() + "\n给您分享了一个有趣的梦境：\n" + dreams.get(position).getContent().toString() + "\n\n来自伯奇·梦境分享");
                } else if (view.getId() == R.id.more) {

                } else {
                    onItemClick(position);
                }
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
                updateItem(result, dreams, dreamAdapter);
                swipe_refresh.setRefreshing(false);
            }
        });
    }

    public static void updateItem(BaseHttpService.CustomerResponse result, List<Dream> dreams, DreamAdapter dreamAdapter) {
        List<Dream> dreamList = new ArrayList<>(Arrays.asList((Dream[]) result.getData()));
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            dreamList.forEach(dream -> new NewDownloadTask(dream.getCreateUser()).download());
//        }
        downloadFun(dreamList, dreamAdapter);
        dreams.clear();
        dreams.addAll(dreamList);
        dreamAdapter.notifyDataSetChanged();
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
        refreshData();
    }

    private void refreshData() {
        new Thread(() -> {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
            runOnUiThread(new Runnable() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void run() {
                    initDataAndSort();
                }
            });
        }).start();
    }

    /**
     * 先加载7个头像，剩余慢慢加载
     * @param dreams
     */
    private static void downloadFun(List<Dream> dreams, DreamAdapter dreamAdapter) {
        List<Dream> toDownload = dreams.subList(0, Math.min(dreams.size(), 7));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            toDownload.forEach(dream -> new NewDownloadTask(dream.getCreateUser()).download());
        }

        if (dreams.size() > 7) {
            new Thread(() -> {
                for (int i = 7; i < dreams.size(); i++) {
                    Dream dream = dreams.get(i);
                    new NewDownloadTask(dream.getCreateUser()).download();
                }
                Timer timer = new Timer(true);
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        UIHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                dreamAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                };
                timer.schedule(timerTask, 0, 500);
            }).start();
        } else {
            UIHandler.post(new Runnable() {
                @Override
                public void run() {
                    dreamAdapter.notifyDataSetChanged();
                }
            });
        }
    }

}
