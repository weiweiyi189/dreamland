package com.example.dreamland.ui.dashboard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dreamland.R;
import com.example.dreamland.databinding.ActivityDashboardBinding;
import com.example.dreamland.databinding.ActivityDreamDetailBinding;
import com.example.dreamland.entity.Dream;
import com.example.dreamland.entity.DreamComment;
import com.example.dreamland.entity.User;
import com.example.dreamland.service.BaseHttpService;
import com.example.dreamland.service.DreamCommentService;
import com.example.dreamland.service.DreamService;
import com.example.dreamland.ui.adapter.DreamAdapter;
import com.example.dreamland.ui.adapter.DreamCommentAdapter;
import com.example.dreamland.ui.chat.MessageListActivity;
import com.example.dreamland.ui.dreams.DreamsActivity;
import com.example.dreamland.ui.util.SystemUtil;
import com.example.dreamland.ui.util.dateUtil;
import com.google.android.material.textfield.TextInputLayout;
import org.litepal.LitePal;

import java.sql.Timestamp;
import java.util.*;

import static com.example.dreamland.ui.util.SystemUtil.hideKeyboard;

public class DetailActivity extends AppCompatActivity {

    private ActivityDreamDetailBinding binding;

    private RecyclerView recyclerView;

    private DreamCommentAdapter dreamCommentAdapter;

    TextInputLayout queryEdt;

    private TextView comment_nums;

    List<DreamComment> commentList = new ArrayList<>();

    Dream dream;

    private DreamCommentService dreamCommentService = DreamCommentService.getInstance();;

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
        this.setDate(dream);
        this.setListener();

        recyclerView = (RecyclerView) findViewById(R.id.detail_recycle);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        this.dreamCommentAdapter = new DreamCommentAdapter(commentList);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(this.dreamCommentAdapter);
    }

    private void setDate(Dream dream) {
        binding.dreamDetailContent.setText(dream.getContent());
        String creatTimeText = dateUtil.getTimeBeforeAccurate(new Date(dream.getCreateTime().getTime()));
        binding.dreamDetailTime.setText(creatTimeText);
        binding.dreamDetailUser.setText(dream.getCreateUser().getUsername());

        dreamCommentService.getAll(new BaseHttpService.CallBack() {
            @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
            @Override
            public void onSuccess(BaseHttpService.CustomerResponse result) {
                List<DreamComment> dreamList = new ArrayList<>(Arrays.asList((DreamComment[]) result.getData()));
                commentList.clear();
                commentList.addAll(dreamList);
                comment_nums.setText("   共 " + commentList.size() + "条评论   ");
                dreamCommentAdapter.notifyDataSetChanged();
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



}
