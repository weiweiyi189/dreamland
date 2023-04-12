package com.example.dreamland.ui.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dreamland.R;
import com.example.dreamland.ui.dreamtest.DreanTestBean;
import com.example.dreamland.ui.dreamtest.UserActivity;
import com.example.dreamland.ui.personal.PersonalActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DreamTestMemberAdapter extends RecyclerView.Adapter<DreamTestMemberAdapter.ViewHolder>  {

    private List<String> dreams;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        LinearLayout ll_item;

        public ViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            ll_item = (LinearLayout) view.findViewById(R.id.ll_item);

        }

    }

    public DreamTestMemberAdapter(Context context,List<String> dataSet) {
        dreams = dataSet;
        mContext = context;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_dream_test_member, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        String dream = dreams.get(position);
        viewHolder.tv_name.setText(dream);
        viewHolder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, UserActivity.class)
                        .putExtra("name",dream));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dreams.size();
    }

}
