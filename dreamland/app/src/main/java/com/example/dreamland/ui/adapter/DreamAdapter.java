package com.example.dreamland.ui.adapter;


import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dreamland.R;
import com.example.dreamland.entity.Dream;
import com.example.dreamland.service.BaseHttpService;
import com.example.dreamland.service.DownloadImageTask;
import com.example.dreamland.service.UserService;
import com.example.dreamland.ui.auth.EnrollActivity;
import com.example.dreamland.ui.auth.LoginActivity;
import com.example.dreamland.ui.util.dateUtil;
import de.hdodenhof.circleimageview.CircleImageView;
import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.List;

public class DreamAdapter  extends RecyclerView.Adapter<DreamAdapter.ViewHolder>  {

    private List<Dream> dreams;

    private final ClickListener listener;

    private static final UserService userService = UserService.getInstance();

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView username;
        TextView createTime;
        TextView content;

        Button favourite;
        Button comment;
        Button share;
        Button more;

        CircleImageView proFile;

        private WeakReference<ClickListener> listenerRef;

        public ViewHolder(View view, ClickListener listener) {
            super(view);
            listenerRef = new WeakReference<>(listener);
            username = (TextView) view.findViewById(R.id.dream_user);
            createTime = (TextView) view.findViewById(R.id.dream_time);
            content = (TextView) view.findViewById(R.id.dream_content);
            favourite = (Button) view.findViewById(R.id.favorite);
            favourite.setOnClickListener(this);
            comment = (Button) view.findViewById(R.id.commentButton);
            comment.setOnClickListener(this);
            share = (Button) view.findViewById(R.id.share);
            share.setOnClickListener(this);
            more = (Button) view.findViewById(R.id.more);
            more.setOnClickListener(this);
            proFile = (CircleImageView) view.findViewById(R.id.pro_file);
            String urlString = BaseHttpService.BASE_URL + userService.currentUser.getValue().getImageUrl();
            new DownloadImageTask(proFile)
                    .execute(urlString);
        }

        @Override
        public void onClick(View v) {
            listenerRef.get().onPositionClicked(getAdapterPosition(), v);
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public DreamAdapter(List<Dream> dataSet , ClickListener listener) {
        dreams = dataSet;
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_dream_item, viewGroup, false);

        return new ViewHolder(view, listener);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Dream dream = dreams.get(position);
        viewHolder.content.setText(dream.getContent());
        String creatTimeText = dateUtil.getTimeBeforeAccurate(new Date(dream.getCreateTime().getTime()));
        viewHolder.createTime.setText(creatTimeText);
        viewHolder.username.setText(dream.getCreateUser().getUsername());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dreams.size();
    }

}
