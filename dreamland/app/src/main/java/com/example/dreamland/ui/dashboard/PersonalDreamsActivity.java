package com.example.dreamland.ui.dashboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.view.Gravity;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dreamland.R;
import com.example.dreamland.databinding.ActivityDreamDetailBinding;
import com.example.dreamland.databinding.ActivityPersonalDreamBinding;
import com.example.dreamland.entity.Dream;
import com.example.dreamland.entity.User;
import com.example.dreamland.service.BaseHttpService;
import com.example.dreamland.service.DreamService;
import com.example.dreamland.service.NewDownloadTask;
import com.example.dreamland.service.UserService;
import com.example.dreamland.ui.adapter.ClickListener;
import com.example.dreamland.ui.adapter.DreamAdapter;
import com.example.dreamland.ui.adapter.DreamCommentAdapter;
import com.example.dreamland.ui.personal.PersonalActivity;
import com.example.dreamland.ui.util.dateUtil;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.dreamland.service.UserService.setCollectDreamToCurrentUser;

public class PersonalDreamsActivity extends AppCompatActivity {

    ActivityPersonalDreamBinding binding;

    User user;

    DreamService dreamService = DreamService.getInstance();

    private RecyclerView recyclerView;

    private UserService userService = UserService.getInstance();

    DreamAdapter dreamAdapter;

    ConstraintLayout constraintLayout;

    List<Dream> dreams = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 绑定v层
        binding = ActivityPersonalDreamBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        constraintLayout = binding.personalDream;

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        user = (User) bundle.getSerializable("user");
        this.setDate(user);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_personal_dream);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        this.dreamAdapter = new DreamAdapter(dreams, (position, view) -> {
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
        });

        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(this.dreamAdapter);

        MaterialToolbar topAppBar = findViewById(R.id.Dream_detail_topAppBar);
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void share(String str) {
        SharePopupWindow spw = new SharePopupWindow(this, str);
        // 显示窗口
        spw.showAtLocation(constraintLayout, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 跳转详细页面
     *
     * @param position
     */
    public void onItemClick(int position) {
        Intent intent = new Intent(PersonalDreamsActivity.this, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("dream", dreams.get(position));
        intent.putExtras(bundle);
        startActivity(intent, null);
    }

    private void setDate(User user) {
        dreamService.getAll(new BaseHttpService.CallBack() {
            @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
            @Override
            public void onSuccess(BaseHttpService.CustomerResponse result) {
                DashboardActivity.updateItem(result, dreams, dreamAdapter);
            }
        });
    }

}
