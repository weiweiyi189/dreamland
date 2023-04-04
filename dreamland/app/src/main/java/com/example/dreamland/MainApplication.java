package com.example.dreamland;


import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.example.dreamland.db.initDataBase;
import org.litepal.LitePal;

public class MainApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LitePal.initialize(this);
        initDataBase.init();
    }

    public static Context getContext() {
        return context;
    }
}
