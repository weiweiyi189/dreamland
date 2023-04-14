package com.example.dreamland.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dreamland.R;

import com.example.dreamland.entity.DreamComment;
import com.example.dreamland.ui.util.dateUtil;
import de.hdodenhof.circleimageview.CircleImageView;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;

public class DreamCommentAdapter extends RecyclerView.Adapter<DreamCommentAdapter.ViewHolder>  {

    private List<DreamComment> comments;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        Button createTime;
        TextView content;

        CircleImageView proFile;

        public ViewHolder(View view) {
            super(view);
            username = (TextView) view.findViewById(R.id.dream_detail_user);
            createTime = (Button) view.findViewById(R.id.outlinedButton);
            content = (TextView) view.findViewById(R.id.dream_comment_content);
            proFile = (CircleImageView) view.findViewById(R.id.user_image);
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public DreamCommentAdapter(List<DreamComment> dataSet) {
        comments = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_dream_comment, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        DreamComment comment = comments.get(position);
        viewHolder.content.setText(comment.getContent());
        String creatTimeText = dateUtil.getTimeBeforeAccurate(new Date(comment.getCreateTime().getTime()));
        viewHolder.createTime.setText(creatTimeText);
        viewHolder.username.setText(comment.getCreateUser().getUsername());
        viewHolder.proFile.setImageBitmap(comment.getCreateUser().getImage());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return comments.size();
    }
}
