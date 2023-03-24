package com.example.dreamland;


import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
