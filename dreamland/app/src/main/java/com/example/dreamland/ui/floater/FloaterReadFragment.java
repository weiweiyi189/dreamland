package com.example.dreamland.ui.floater;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dreamland.R;

import com.example.dreamland.entity.Letter;
import com.example.dreamland.service.BaseHttpService;
import com.example.dreamland.service.DreamService;
import com.example.dreamland.service.LetterService;
import com.example.dreamland.service.UserService;
import com.example.dreamland.ui.adapter.LetterAdapter;

import com.example.dreamland.entity.Dream;
import com.example.dreamland.entity.User;
import com.example.dreamland.ui.adapter.ClickListener;
import com.example.dreamland.ui.adapter.DreamAdapter;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FloaterReadFragment extends Fragment {
    private LetterAdapter letterAdapter;
    private List<Letter> letters=new LinkedList<>();
    private LetterService letterService = LetterService.getInstance();
    private UserService userService = UserService.getInstance();
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_floater_read, container, false);
        initList();
        return view;
    }
    public void initList() {
        letterService.getAllshowed(new BaseHttpService.CallBack() {
            @Override
            public void onSuccess(BaseHttpService.CustomerResponse result) {
                List<Letter> letterList = new ArrayList<>(Arrays.asList((Letter[]) result.getData()));
                letters.clear();
                letters.addAll(letterList);
                letterAdapter.notifyDataSetChanged();
            }
        },new Long(userService.currentUser.getValue().getId()));


        RecyclerView recyclerView= view.findViewById(R.id.floaterReadRecyclerView);
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        this.letterAdapter = new LetterAdapter(this.letters);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(this.letterAdapter);
    }
}