package com.example.dreamland.service;

import com.example.dreamland.entity.Dream;
import com.example.dreamland.entity.Letter;

public class LetterService {
    public static LetterService letterService;
    private static final String LOCAL_URL = "api/";

    public static LetterService getInstance() {
        if (letterService == null) {
            letterService = new LetterService();
        }
        return letterService;
    }

    BaseHttpService httpService = BaseHttpService.getInstance();

    public void getAll(BaseHttpService.CallBack callBack) {
        httpService.get("api/letter/getAll", callBack, Letter[].class);
    }

    public void getAllByOrderByCreateTimeDesc(BaseHttpService.CallBack callBack) {
        httpService.get(LOCAL_URL +"letter/getAllByOrderByCreateTimeDesc", callBack, Letter[].class);
    }

    public void getTopNotShowed(BaseHttpService.CallBack callBack,Long id){
        httpService.get(LOCAL_URL +"letter/getTopNotShowed/"+id.toString(),callBack,Letter.class);
    }

    public void getAllshowed(BaseHttpService.CallBack callBack,Long id){
        httpService.get(LOCAL_URL +"letter/getAllshowed/"+id.toString(),callBack,Letter[].class);
    }

    public void add(BaseHttpService.CallBack callBack, Letter letter) {
        httpService.post( LOCAL_URL +"letter/add", letter, callBack, Letter.class);
    }

    public void modifyLetter(BaseHttpService.CallBack callBack, Letter letter){
        httpService.post(LOCAL_URL +"letter/modifyLetter", letter, callBack, Letter.class);
    }

    public void getAllByCreateUserId(BaseHttpService.CallBack callBack, Long id){
        httpService.get(LOCAL_URL +"letter/getAllByCreateUserId/"+id.toString(), callBack, Letter[].class);
    }

}
