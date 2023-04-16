package com.example.dreamland.ui.adapter;


import android.annotation.SuppressLint;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dreamland.R;
import com.example.dreamland.entity.Dream;
import com.example.dreamland.entity.Letter;
import com.example.dreamland.ui.util.dateUtil;
import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.List;

public class LetterAdapter  extends RecyclerView.Adapter<LetterAdapter.ViewHolder>  {

    private List<Letter> letters;

    private final ClickListener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final CardView cardView;
        TextView username;
        TextView createTime;
        TextView content;
        TextView title;
        Button button;
        TextView comment;

        private WeakReference<ClickListener> listenerRef;

        @SuppressLint("WrongViewCast")
        public ViewHolder(View view, ClickListener listener) {
            super(view);
            listenerRef = new WeakReference<>(listener);
            cardView = (CardView) view;
            username = (TextView) view.findViewById(R.id.letter_user);
            createTime = (TextView) view.findViewById(R.id.letter_time);
            title=(TextView) view.findViewById(R.id.letter_title);
            content = (TextView) view.findViewById(R.id.letter_content);
            button=(Button)view.findViewById(R.id.textButton);
            button.setOnClickListener(this);
            comment=(TextView) view.findViewById(R.id.test);
        }

        public CardView getCardView() {
            return cardView;
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
    public LetterAdapter(List<Letter> dataSet, ClickListener listener) {
        letters = dataSet;
        this.listener=listener;
    }

    // Create new views (invoked by the layout manager)
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_letter_item, viewGroup, false);

        return new ViewHolder(view,listener);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Letter letter = letters.get(position);
        viewHolder.content.setText(letter.getContent());
        viewHolder.title.setText(letter.getTitle());
        String creatTimeText = dateUtil.getTimeBeforeAccurate(new Date(letter.getCreateTime().getTime()));
        viewHolder.createTime.setText(creatTimeText);
        viewHolder.username.setText(letter.getCreateUser().getUsername());
        String comments="";
        for(int i=0;i<letter.getComment().size();i++){
            if(i==0)comments+=letter.getComment().get(i);
            else comments+="\n"+letter.getComment().get(i);
        }
        if(comments!="")viewHolder.comment.setVisibility(View.VISIBLE);
        viewHolder.comment.setText(comments);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return letters.size();
    }

}
