package com.example.dreamland.service;

import com.example.dreamland.entity.User;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import okhttp3.RequestBody;

public class UserService {
    // 若启用nginx 则为后台的转发url
    private static final String LOCAL_URL = "";
    public static UserService userService;

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public static final String TOKEN_HEADER = "Authorization";

    public BehaviorSubject<User> currentUser = BehaviorSubject.createDefault(new User());  // 当前登陆用户

    BaseHttpService httpService = BaseHttpService.getInstance();

    /**
     * 登陆
     *
     * @param callBack
     * @param user
     */
    public void login(BaseHttpService.CallBack callBack, User user) {
        httpService.post(LOCAL_URL + "user/login", user, callBack, User.class);
    }

    /**
     * 获取当前登陆用户
     *
     * @param callBack
     */
    public void getCurrentUser(BaseHttpService.CallBack callBack) {
        httpService.get(LOCAL_URL + "user/currentLoginUser", callBack, User.class);
    }

    /**
     * 修改头像
     *
     * @param data
     * @param callBack
     */
    public void uploadImage(RequestBody data, BaseHttpService.CallBack callBack) {
        httpService.putByForm(LOCAL_URL + "user/changeImage", data, callBack, String.class);
    }
}