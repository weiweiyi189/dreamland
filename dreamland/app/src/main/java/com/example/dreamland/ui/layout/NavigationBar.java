package com.example.dreamland.ui.layout;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.dreamland.R;
import com.example.dreamland.databinding.LayoutNavigationBarBinding;
<<<<<<< HEAD
import com.example.dreamland.ui.WritedreamActivity.WritedreamActivity;
=======
>>>>>>> origin/main
import com.example.dreamland.ui.dashboard.DashboardActivity;
import com.example.dreamland.ui.personal.PersonalActivity;

import com.google.android.material.navigation.NavigationBarView;
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
                    case R.id.item_1:
                        Intent intent1 = new Intent(context, DashboardActivity.class);
                        startActivity(context,intent1, null);
                        break;
                    case R.id.item_2:
                        Intent intent2 = new Intent(context, WritedreamActivity.class);
                        startActivity(context,intent2, null);
                        break;
                    case R.id.item_3:
                        Intent intent3 = new Intent(context, PersonalActivity.class);
                        startActivity(context,intent3, null);
                        break;
                }
                return true;
            }
        });
    }
}
