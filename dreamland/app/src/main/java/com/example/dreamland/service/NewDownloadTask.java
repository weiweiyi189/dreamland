package com.example.dreamland.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.example.dreamland.entity.User;

import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 下载用户头像
 */
public class NewDownloadTask {
    private User user;


    public NewDownloadTask(User user) {
        this.user = user;
    }

    public User download() {
        // 若无头像 或着已下载缓存过 直接返回
        if (user.getImageUrl() == null || Objects.equals(user.getImageUrl(), "") || user.getImage() != null) {
            return user;
        }
        String urlString = BaseHttpService.BASE_URL + user.getImageUrl();
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urlString).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            user.setImage(mIcon11);
        });
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
        }
        return user;
    }

}
