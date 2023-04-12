package com.example.dreamland.ui.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dreamland.R;
import com.example.dreamland.entity.Dream;
import com.example.dreamland.ui.dreamtest.DreanTestBean;
import com.example.dreamland.ui.util.dateUtil;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;

public class DreamTestAdapter extends RecyclerView.Adapter<DreamTestAdapter.ViewHolder>  {

    private List<DreanTestBean> dreams;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        RadioGroup rg;
        RadioButton rb1;
        RadioButton rb2;
        RadioButton rb3;
        RadioButton rb4;

        public ViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            rg = (RadioGroup) view.findViewById(R.id.rg);
            rb1 = (RadioButton) view.findViewById(R.id.rb1);
            rb2 = (RadioButton) view.findViewById(R.id.rb2);
            rb3 = (RadioButton) view.findViewById(R.id.rb3);
            rb4 = (RadioButton) view.findViewById(R.id.rb4);
        }

    }

    public DreamTestAdapter(List<DreanTestBean> dataSet) {
        dreams = dataSet;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_dream_test, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        DreanTestBean dream = dreams.get(position);
        viewHolder.tv_name.setText(dream.getTitle());
        String[] split = dream.getContent().split(",");
        viewHolder.rb1.setText(split[0]);
        viewHolder.rb2.setText(split[1]);
        viewHolder.rb3.setText(split[2]);
        viewHolder.rb4.setText(split[3]);
        if (dream.getType() == 0){
            viewHolder.rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int checkid) {
                    switch (checkid){

                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return dreams.size();
    }

}
