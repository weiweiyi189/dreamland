package com.example.dreamland.ui.floater;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.dreamland.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

public class FloaterMessagesActivity extends AppCompatActivity {

    static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_floater_messages);

        fragmentManager=getSupportFragmentManager();

        TabLayout tabLayout=findViewById(R.id.floaterTabLayout);

        switchFragment(new FloaterReadFragment());

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()){
                    case 0:
                        switchFragment(new FloaterReadFragment());
                        break;
                    case 1:
                        switchFragment(new FloaterWriteFragment());
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        MaterialToolbar topAppBar=findViewById(R.id.floaterMessagesTopAppBar);
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public static void switchFragment(Fragment fragment){
        fragmentManager.beginTransaction().replace(R.id.floaterContainer,fragment).commit();
    }
}