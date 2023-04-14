package com.example.dreamland.service;

import android.os.Handler;
import android.os.Looper;
import com.example.dreamland.entity.Dream;
import com.example.dreamland.entity.DreamComment;

public class DreamCommentService {

    /**
     * 解决 runOnUIThead 不能在 static 方法中使用的问题
     */
    public static Handler UIHandler = new Handler(Looper.getMainLooper());


    private static final String LOCAL_URL = "api/";

    public static DreamCommentService dreamCommentService;

    public static DreamCommentService getInstance() {
        if (dreamCommentService == null) {
            dreamCommentService = new DreamCommentService();
        }
        return dreamCommentService;
    }

    BaseHttpService httpService = BaseHttpService.getInstance();

    public void getAll(BaseHttpService.CallBack callBack, Long dreamId) {
        httpService.get(LOCAL_URL + "comment/getAll/"  + dreamId.toString() , callBack, DreamComment[].class);
    }

    public void add(BaseHttpService.CallBack callBack, DreamComment dreamComment) {
        httpService.post( LOCAL_URL + "comment/add", dreamComment, callBack, DreamComment.class);
    }
}
