package com.example.dreamland.ui.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.dreamland.R;
/**
 * 底部的导航栏
 * @author weiweiyi
 */
public class NavigationBar extends ConstraintLayout {

    public NavigationBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_navigation_bar, this);
    }
}
