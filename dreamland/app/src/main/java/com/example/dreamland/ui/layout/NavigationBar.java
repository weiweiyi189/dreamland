package com.example.dreamland.ui.layout;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.dreamland.MainApplication;
import com.example.dreamland.R;
import com.example.dreamland.databinding.LayoutNavigationBarBinding;
import com.example.dreamland.ui.auth.LoginActivity;
import com.example.dreamland.ui.chat.ChatActivity;
import com.example.dreamland.ui.dashboard.DashboardActivity;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import org.jetbrains.annotations.NotNull;

import static androidx.core.content.ContextCompat.startActivity;

/**
 * 底部的导航栏
 * @author weiweiyi
 */
public class NavigationBar extends ConstraintLayout {

    private LayoutNavigationBarBinding binding;

    public NavigationBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_navigation_bar, this);
        NavigationBarView navigationView = (NavigationBarView) findViewById(R.id.bottom_navigation);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                int id = item.getItemId();
                switch(id){
                    case R.id.item_3:
                        Intent intent = new Intent(context, ChatActivity.class);
                        startActivity(context,intent, null);
                }
                return true;
            }
        });
    }
}
