package com.example.dreamland.ui.layout;

import android.content.Context;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.dreamland.R;
import com.example.dreamland.databinding.LayoutDreamItemBinding;
import org.jetbrains.annotations.NotNull;

public class DreamDetail extends ConstraintLayout {
    private LayoutDreamItemBinding dreamItemBinding;

    public DreamDetail(@NonNull @NotNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.layout_dream_item, this);
    }
}
