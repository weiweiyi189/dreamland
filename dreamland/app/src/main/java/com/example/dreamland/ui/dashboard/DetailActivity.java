package com.example.dreamland.ui.dashboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dreamland.R;
import com.example.dreamland.databinding.ActivityDreamDetailBinding;
import com.example.dreamland.entity.Dream;
import com.example.dreamland.entity.DreamComment;
import com.example.dreamland.service.BaseHttpService;
import com.example.dreamland.service.DreamCommentService;
import com.example.dreamland.service.NewDownloadTask;
import com.example.dreamland.ui.adapter.DreamAdapter;
import com.example.dreamland.ui.adapter.DreamCommentAdapter;
import com.example.dreamland.ui.util.SystemUtil;
import com.example.dreamland.ui.util.dateUtil;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.*;

public class DetailActivity extends AppCompatActivity {

    private ActivityDreamDetailBinding binding;

    private RecyclerView recyclerView;

    private DreamCommentAdapter dreamCommentAdapter;

    TextInputLayout queryEdt;

    private TextView comment_nums;

    List<DreamComment> commentList = new ArrayList<>();

    Dream dream;

    private DreamCommentService dreamCommentService = DreamCommentService.getInstance();
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 绑定v层
        binding = ActivityDreamDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        comment_nums = binding.commentNums;
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        dream = (Dream) bundle.getSerializable("dream");
        new NewDownloadTask(dream.getCreateUser()).download();
        this.setDate(dream);
        this.setListener();

        recyclerView = (RecyclerView) findViewById(R.id.detail_recycle);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        this.dreamCommentAdapter = new DreamCommentAdapter(commentList);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(this.dreamCommentAdapter);

        MaterialToolbar topAppBar = findViewById(R.id.Dream_detail_topAppBar);
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void setDate(Dream dream) {
        binding.dreamDetailContent.setText(dream.getContent());
        String creatTimeText = dateUtil.getTimeBeforeAccurate(new Date(dream.getCreateTime().getTime()));
        binding.dreamDetailTime.setText(creatTimeText);
        binding.dreamDetailUser.setText(dream.getCreateUser().getUsername());
        binding.userDetailImage.setImageBitmap(dream.getCreateUser().getImage());

        dreamCommentService.getAll(new BaseHttpService.CallBack() {
            @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
            @Override
            public void onSuccess(BaseHttpService.CustomerResponse result) {
                updateItem(result, commentList, dreamCommentAdapter);
                comment_nums.setText("   共 " + commentList.size() + "条评论   ");
            }
        }, dream.getId());
    }


    public void setListener() {
        queryEdt = binding.comment;
        queryEdt.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (queryEdt.getEditText().getText().toString().length() > 0) {
                    DreamComment dreamComment = new DreamComment();
                    Dream dream1 = new Dream();
                    dream1.setId(dream.getId());
                    dreamComment.setDream(dream1);
                    dreamComment.setContent(queryEdt.getEditText().getText().toString());
                    queryEdt.getEditText().setText("");
                    dreamCommentService.add(new BaseHttpService.CallBack() {
                        @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
                        @Override
                        public void onSuccess(BaseHttpService.CustomerResponse result) {
                            DreamComment dreamCommentResult = (DreamComment) result.getData();
                            new NewDownloadTask(dreamCommentResult.getCreateUser()).download();
                            commentList.add(0, dreamCommentResult);
                            dreamCommentAdapter.notifyDataSetChanged();
                            SystemUtil.hideKeyboard(DetailActivity.this);
                            binding.detailRecycle.post(new Runnable() {
                                @Override
                                public void run() {
                                    binding.detailRecycle.smoothScrollToPosition(0);
                                }
                            });
                        }
                    }, dreamComment);
                } else {
                    Toast.makeText(DetailActivity.this, "说点什么吧", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateItem(BaseHttpService.CustomerResponse result, List<DreamComment> dreamComments, DreamCommentAdapter dreamCommentAdapter) {
        List<DreamComment> comments = new ArrayList<>(Arrays.asList((DreamComment[]) result.getData()));
        downloadFun(comments);
        dreamComments.clear();
        dreamComments.addAll(comments);
        dreamCommentAdapter.notifyDataSetChanged();
    }

    /**
     * 先加载7个头像，剩余慢慢加载
     * @param dreamComments
     */
    private void downloadFun(List<DreamComment> dreamComments) {
        List<DreamComment> commentsToDownload = dreamComments.subList(0, Math.min(dreamComments.size(), 7));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            commentsToDownload.forEach(dreamComment -> new NewDownloadTask(dreamComment.getCreateUser()).download());
        }

        if (dreamComments.size() > 7) {
            new Thread(() -> {
                for (int i = 7; i < dreamComments.size(); i++) {
                    DreamComment dreamComment = dreamComments.get(i);
                    new NewDownloadTask(dreamComment.getCreateUser()).download();
                }
                Timer timer = new Timer(true);
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(() -> dreamCommentAdapter.notifyDataSetChanged());
                    }
                };
                timer.schedule(timerTask, 0, 500);
            }).start();
        } else {
            runOnUiThread(() -> dreamCommentAdapter.notifyDataSetChanged());
        }
    }
}
