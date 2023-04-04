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
        user.setUsername("用户12345985");

        Dream dream = new Dream();
        dream.setContent("梦到世界大危机，反派与正派对峙，正派死伤惨重，\n" +
                "几个主角都被抓。而我被委托以重要使命，跳井穿越时空拿到重要宝物压制反派.结果时间乱流寄了。");
        dream.setCreateTime(new Timestamp(1679383693000L));
        dream.setCreateUser(user);

        for (int i = 0; i < 10; i++) {
            this.dreams.add(dream);
        }

        RecyclerView recyclerView= view.findViewById(R.id.floaterReadRecyclerView);
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        this.dreamAdapter = new DreamAdapter(this.dreams);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(this.dreamAdapter);
    }
}