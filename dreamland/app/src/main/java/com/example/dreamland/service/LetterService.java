package com.example.dreamland.service;

import com.example.dreamland.entity.Letter;

public class LetterService {
    public static LetterService letterService;


    public static LetterService getInstance() {
        if (letterService == null) {
            letterService = new LetterService();
        }
        return letterService;
    }

    BaseHttpService httpService = BaseHttpService.getInstance();

    public void getAllByOrderByCreateTimeDesc(BaseHttpService.CallBack callBack) {
        httpService.get("letter/getAllByOrderByCreateTimeDesc", callBack, Letter[].class);
    }

    public void getTopNotShowed(BaseHttpService.CallBack callBack,Long id){
        httpService.get("letter/getTopNotShowed/"+id.toString(),callBack,Letter.class);
    }

    public void getAllshowed(BaseHttpService.CallBack callBack,Long id){
        httpService.get("letter/getAllshowed/"+id.toString(),callBack,Letter[].class);
    }

    public void add(BaseHttpService.CallBack callBack, Letter letter) {
        httpService.post( "letter/add", letter, callBack, Letter.class);
    }

    public void getAllByCreateUserId(BaseHttpService.CallBack callBack, Long id){
        httpService.get("letter/getAllByCreateUserId/"+id.toString(), callBack, Letter[].class);
    }

}
