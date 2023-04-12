package com.example.dreamland.ui.Page;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import androidx.fragment.app.Fragment;
import com.example.dreamland.R;
import com.example.dreamland.ui.Bean.Singer;
import com.example.dreamland.ui.Bean.SingerUtils;
import com.example.dreamland.ui.adapter.SingerGridAdapter;
import com.example.dreamland.ui.music.SingerDescActivity;

import java.util.List;

public class SingerPage extends Fragment {
    private View singerView;
    private GridView gv;
    private List<Singer> mDatas;
    private SingerGridAdapter adapter;

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        singerView = inflater.inflate(R.layout.singer_page, null);
        gv = singerView.findViewById(R.id.singer_gv);
        //1、获得数据源，也就是所有对象的列表
        mDatas = SingerUtils.getSingerList();
        //2、适配器加载数据源
        adapter = new SingerGridAdapter(getContext(), mDatas);
        //3、为布局设置适配器
        gv.setAdapter(adapter);
        setListener();
        return singerView;
    }
    public void setListener() {
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Singer singer = mDatas.get(position);
                Intent intent = new Intent(getActivity(), SingerDescActivity.class);
                intent.putExtra("singer", singer);
                startActivity(intent);
            }
        });
    }
}
