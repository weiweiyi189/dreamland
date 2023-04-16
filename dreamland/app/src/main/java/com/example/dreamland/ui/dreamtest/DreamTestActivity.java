package com.example.dreamland.ui.dreamtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dreamland.R;
import com.example.dreamland.ui.adapter.DreamTestAdapter;
import com.example.dreamland.ui.adapter.DreamTestMemberAdapter;
import com.example.dreamland.ui.util.StatusBar;
import com.example.dreamland.ui.util.cons;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.litepal.util.Const;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DreamTestActivity extends AppCompatActivity {

    private List<String> result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dream_test);
        initView();
    }

    private void initView() {
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        RecyclerView rv = findViewById(R.id.rv);
        FrameLayout ll_result = findViewById(R.id.ll_result);
        TextView tv_result = findViewById(R.id.tv_result);
        RecyclerView rv_member = findViewById(R.id.rv_member);
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_submit.setVisibility(View.GONE);
                rv.setVisibility(View.GONE);
                topAppBar.setVisibility(View.GONE);
                new StatusBar(DreamTestActivity.this).setColor(R.color.transparent);
                ll_result.setVisibility(View.VISIBLE);
                int i = cons.count % 4;
                tv_result.setText(result.get(i));
                cons.count = cons.count +1;
            }
        });


        rv.setLayoutManager(new LinearLayoutManager(this));
        rv_member.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        rv.setNestedScrollingEnabled(false);
        rv_member.setNestedScrollingEnabled(false);
        String fromAssets = getFromAssets("dreamtest.json");
        Type type = new TypeToken<List<DreanTestBean>>() {
        }.getType();
        List<DreanTestBean> list = new Gson().fromJson(fromAssets, type);
        DreamTestAdapter adapter = new DreamTestAdapter(list);
        rv.setAdapter(adapter);

        List<String> list1 = new ArrayList<>();
//        list1.add("张明明");
//        list1.add("赵天");
//        list1.add("王萌萌");
        DreamTestMemberAdapter adapter1 = new DreamTestMemberAdapter(this,list1);
        rv_member.setAdapter(adapter1);

        String fromAssets1 = getFromAssets("result.json");
        Type type1 = new TypeToken<List<String>>() {
        }.getType();
        result = new Gson().fromJson(fromAssets1, type1);
    }

    public String getFromAssets(String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}