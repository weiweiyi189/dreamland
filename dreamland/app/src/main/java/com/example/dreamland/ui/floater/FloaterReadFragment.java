package com.example.dreamland.ui.floater;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dreamland.R;

import com.example.dreamland.databinding.FragmentFloaterReadBinding;
import com.example.dreamland.databinding.LayoutLetterItemBinding;
import com.example.dreamland.entity.Letter;
import com.example.dreamland.service.BaseHttpService;
import com.example.dreamland.service.LetterService;
import com.example.dreamland.service.UserService;
import com.example.dreamland.ui.adapter.ClickListener;
import com.example.dreamland.ui.adapter.LetterAdapter;
import com.example.dreamland.ui.dashboard.DetailActivity;
import com.example.dreamland.ui.util.SystemUtil;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FloaterReadFragment extends Fragment {
    RecyclerView recyclerView;
    LinearLayoutManager layout;
    private LetterAdapter letterAdapter;
    private List<Letter> letters=new LinkedList<>();
    private LetterService letterService = LetterService.getInstance();
    private UserService userService = UserService.getInstance();
    RelativeLayout relativeLayout;
    EditText editText;
    Button send;
    private View view;
    private Letter clickedLetter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_floater_read, container, false);
        relativeLayout=view.findViewById(R.id.relativeLayout);
        editText=view.findViewById(R.id.EditText);
        send=view.findViewById(R.id.send);
        initList();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedLetter.addComment(userService.currentUser.getValue().getUsername()+": "+editText.getText().toString());

                letterService.modifyLetter(new BaseHttpService.CallBack() {
                    @Override
                    public void onSuccess(BaseHttpService.CustomerResponse result) {
                        if (result.getResponse().code() >= 200 && result.getResponse().code() < 300) {
                            Toast.makeText(getContext(), "评论成功", Toast.LENGTH_SHORT).show();
                            relativeLayout.setVisibility(View.GONE);
                            editText.setText("");
                        } else {
                            // 登陆失败 提示错误
                            Toast.makeText(getContext(), "评论失败", Toast.LENGTH_SHORT).show();
                        }
                        SystemUtil.hideKeyboard((Activity)getContext());
                        initList();
                    }
                },clickedLetter);






            }
        });

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


        recyclerView= view.findViewById(R.id.floaterReadRecyclerView);
        layout = new LinearLayoutManager(getContext());
        this.letterAdapter = new LetterAdapter(this.letters, new ClickListener() {
            @Override
            public void onPositionClicked(int position, View view) {
                if(view.getId()==R.id.textButton){
                    relativeLayout.setVisibility(View.VISIBLE);
                    clickedLetter=letters.get(position);
                }
            }
        });
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(this.letterAdapter);
    }
}