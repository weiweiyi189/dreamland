package com.example.dreamland.service;

import com.example.dreamland.entity.Dream;

public class DreamService {

    private static final String LOCAL_URL = "api/";

    public static DreamService dreamService;

    public static DreamService getInstance() {
        if (dreamService == null) {
            dreamService = new DreamService();
        }
        return dreamService;
    }

    BaseHttpService httpService = BaseHttpService.getInstance();

    public void getAll(BaseHttpService.CallBack callBack) {
        httpService.get(LOCAL_URL + "dream/getAll", callBack, Dream[].class);
    }

    public void add(BaseHttpService.CallBack callBack, Dream dream) {
        httpService.post( LOCAL_URL + "api/dream/add", dream, callBack, Dream.class);
    }



}
