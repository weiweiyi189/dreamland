package com.example.dreamland.ui.floater;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import com.example.dreamland.R;
import com.example.dreamland.ui.auth.LoginActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class FloaterActivity extends AppCompatActivity {
    Button read;
    Button write;
    ImageView capture;
    ImageView floaterColored;
    RelativeLayout readLetter;
    TextView readContent;
    Button stopRead;
    ImageView arrow;
    RelativeLayout writeLetter;
    Button stopWrite;
    Button sendWrite;

    private Animation hide;
    private Animation captureShow;
    private Animation captureHide;
    private Animation floaterShow;
    private Animation floaterRotate;
    private Animation floaterHideInPlace;
    private Animation readLetterShow;
    private Animation readLetterHide;
    private Animation arrowShake;
    private Animation writeLetterShow;
    private Animation writeLetterHide1;
    private Animation writeLetterHide2;
    private Animation floaterShowInPlace;
    private Animation floaterRotate2;
    private Animation floaterHide;
    private Animation arrowShake2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floater);

        MaterialToolbar topAppBar=findViewById(R.id.floaterTopAppBar);
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ActionMenuItemView messages=findViewById(R.id.floaterMessages);
        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FloaterActivity.this, FloaterMessagesActivity.class);
                startActivity(intent,null);
            }
        });

        initView();
        initAnimation();
        initAnimationListener();

        capture.startAnimation(hide);
        floaterColored.startAnimation(hide);
        readLetter.startAnimation(hide);
        arrow.startAnimation(hide);
        writeLetter.startAnimation(hide);

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //捞网show&&按钮hide->捞网hide&&漂流瓶show->漂流瓶rotate->漂流瓶hide->信show
                capture.startAnimation(captureShow);
            }
        });
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //信show&&按钮hide
                writeLetter.startAnimation(writeLetterShow);
            }
        });
        stopRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //信hide&&按钮show->箭头show->箭头hide
                readLetter.startAnimation(readLetterHide);
            }
        });
        stopWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //信hide&&按钮show
                writeLetter.startAnimation(writeLetterHide1);
            }
        });
        sendWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //信hide&&按钮show->漂流瓶show->漂流瓶rotate->漂流瓶hide&&箭头show->箭头hide
                writeLetter.startAnimation(writeLetterHide2);
            }
        });
    }

    private void initView() {
        read=findViewById(R.id.floaterRead);

        write=findViewById(R.id.floaterWrite);

        capture=findViewById(R.id.floaterCapture);

        floaterColored=findViewById(R.id.floaterColored);

        readLetter=findViewById(R.id.floaterReadLetterLayout);
        for(int i=0;i<readLetter.getChildCount();i++){
            readLetter.getChildAt(i).setEnabled(false);
        }

        readContent=findViewById(R.id.floaterReadContent);
        readContent.setMovementMethod(ScrollingMovementMethod.getInstance());

        stopRead=findViewById(R.id.floaterStopRead);

        arrow=findViewById(R.id.floaterArrow);

        writeLetter=findViewById(R.id.floaterWriteLetterLayout);
        for(int i=0;i<writeLetter.getChildCount();i++){
            writeLetter.getChildAt(i).setEnabled(false);
        }

        stopWrite=findViewById(R.id.floaterStopWrite);

        sendWrite=findViewById(R.id.floaterSendWrite);
    }


    private void initAnimation(){
        hide=AnimationUtils.loadAnimation(
          getApplicationContext(),
          R.anim.floater_hide
        );

        captureShow=AnimationUtils.loadAnimation(
                getApplicationContext(),
                R.anim.floater_capture_show
        );

        captureHide=AnimationUtils.loadAnimation(
                getApplicationContext(),
                R.anim.floater_capture_hide
        );

        floaterShow=AnimationUtils.loadAnimation(
                getApplicationContext(),
                R.anim.floater_floater_show
        );

        floaterRotate=AnimationUtils.loadAnimation(
                getApplicationContext(),
                R.anim.floater_floater_rotate
        );

        floaterHideInPlace =AnimationUtils.loadAnimation(
                getApplicationContext(),
                R.anim.floater_floater_hide_in_place
        );

        readLetterShow=AnimationUtils.loadAnimation(
                getApplicationContext(),
                R.anim.floater_read_letter_show
        );

        readLetterHide =AnimationUtils.loadAnimation(
                getApplicationContext(),
                R.anim.floater_read_letter_hide
        );

        arrowShake=AnimationUtils.loadAnimation(
                getApplicationContext(),
                R.anim.floater_arrow_shake
        );

        writeLetterShow=AnimationUtils.loadAnimation(
                getApplicationContext(),
                R.anim.floater_read_letter_show
        );

        writeLetterHide1=AnimationUtils.loadAnimation(
                getApplicationContext(),
                R.anim.floater_write_letter_hide
        );

        writeLetterHide2=AnimationUtils.loadAnimation(
                getApplicationContext(),
                R.anim.floater_write_letter_hide
        );

        floaterShowInPlace=AnimationUtils.loadAnimation(
                getApplicationContext(),
                R.anim.floater_floater_show_in_place
        );

        floaterRotate2=AnimationUtils.loadAnimation(
                getApplicationContext(),
                R.anim.floater_floater_rotate
        );

        floaterHide=AnimationUtils.loadAnimation(
                getApplicationContext(),
                R.anim.floater_floater_hide
        );

        arrowShake2=AnimationUtils.loadAnimation(
                getApplicationContext(),
                R.anim.floater_arrow_shake
        );
    }
    private void initAnimationListener() {
        //捞网show&&按钮hide->捞网hide&&漂流瓶show
        captureShow.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                read.setVisibility(View.GONE);
                write.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                capture.startAnimation(captureHide);
                floaterColored.startAnimation(floaterShow);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //漂流瓶show->漂流瓶rotate
        floaterShow.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                floaterColored.startAnimation(floaterRotate);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //漂流瓶rotate->漂流瓶hide&&信show
        floaterRotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                floaterColored.startAnimation(floaterHideInPlace);
                readLetter.startAnimation(readLetterShow);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //信show&&子控件show
        readLetterShow.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                for(int i=0;i<readLetter.getChildCount();i++){
                    readLetter.getChildAt(i).setEnabled(true);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //信hide&&子控件hide&&按钮show->箭头show
        readLetterHide.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                read.setVisibility(View.VISIBLE);
                write.setVisibility(View.VISIBLE);
                for(int i=0;i<readLetter.getChildCount();i++){
                    readLetter.getChildAt(i).setEnabled(false);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                arrow.startAnimation(arrowShake);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //箭头show->箭头hide
        arrowShake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                arrow.startAnimation(hide);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //信show&&子控件show&&按钮hide
        writeLetterShow.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                read.setVisibility(View.GONE);
                write.setVisibility(View.GONE);
                for(int i=0;i<writeLetter.getChildCount();i++){
                    writeLetter.getChildAt(i).setEnabled(true);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //信hide&&子控件hide&&按钮show
        writeLetterHide1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                read.setVisibility(View.VISIBLE);
                write.setVisibility(View.VISIBLE);
                for(int i=0;i<writeLetter.getChildCount();i++){
                    writeLetter.getChildAt(i).setEnabled(false);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //信hide&&子控件hide->漂流瓶show
        writeLetterHide2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                for(int i=0;i<writeLetter.getChildCount();i++){
                    writeLetter.getChildAt(i).setEnabled(false);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                floaterColored.startAnimation(floaterShowInPlace);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //漂流瓶show->漂流瓶rotate
        floaterShowInPlace.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                floaterColored.startAnimation(floaterRotate2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //漂流瓶rotate->漂流瓶hide&&按钮show
        floaterRotate2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                floaterColored.startAnimation(floaterHide);
                read.setVisibility(View.VISIBLE);
                write.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //漂流瓶hide->箭头show&&广播show
        floaterHide.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                arrow.startAnimation(arrowShake2);
                Toast.makeText(FloaterActivity.this, "漂流成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //箭头show->箭头hide
        arrowShake2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                arrow.startAnimation(hide);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}