package com.example.dreamland.ui.auth;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dreamland.R;

public class BeginSplash extends AppCompatActivity {

    Animation topAnim,bottomAnim;
    ImageView imageView;
    TextView app_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin_splash);

        WindowInsetsController controller = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            controller = getWindow().getInsetsController();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            controller.hide(WindowInsets.Type.statusBars());
        }

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        imageView = findViewById(R.id.logo);
        app_name = findViewById(R.id.wel);

        imageView.setAnimation(topAnim);
        app_name.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(BeginSplash.this,LoginActivity.class);
                Pair[] pairs = new Pair[2];
                pairs[0]=new Pair(imageView,"splash_image");
                pairs[1]=new Pair(app_name,"splash_text");

                ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(BeginSplash.this,pairs);
                startActivity(i,options.toBundle());
                finish();
            }
        }, 1666);
    }
}