package com.example.dreamland.ui.floater;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dreamland.R;
import com.example.dreamland.entity.Dream;
import com.example.dreamland.entity.User;
import com.example.dreamland.ui.adapter.ClickListener;
import com.example.dreamland.ui.adapter.DreamAdapter;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

public class FloaterReadFragment extends Fragment {
    private DreamAdapter dreamAdapter;
    private List<Dream> dreams = new LinkedList<>();
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_floater_read, container, false);
        initList();
        return view;
    }
    public void initList() {
        User user = new User();
        user.setUsername("用户快斗斗丶");

        Dream dream = new Dream();
        dream.setContent("  是载满星云的玄霄, 亦是播洒清梦的红壤.\n  漂出你的思绪, 捞起我的奇遇.\n  这里, 皆你我的天地.\n");
        dream.setCreateTime(new Timestamp(1679383693000L));
        dream.setId(1L);
        dream.setCreateUser(user);

        for (int i = 0; i < 10; i++) {
            this.dreams.add(dream);
        }

        RecyclerView recyclerView= view.findViewById(R.id.floaterReadRecyclerView);
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        this.dreamAdapter = new DreamAdapter(this.dreams, new ClickListener() {
            @Override public void onPositionClicked(int position, View view) {
                // callback performed on click
            }

        });
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(this.dreamAdapter);
    }
}