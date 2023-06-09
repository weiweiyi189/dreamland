package com.example.dreamland.service;

import android.os.Build;
import com.example.dreamland.R;
import com.example.dreamland.entity.Dream;
import com.example.dreamland.entity.User;
import com.example.dreamland.entity.VoUser;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import okhttp3.RequestBody;

import java.util.List;

public class UserService {
    // 若启用nginx 则为后台的转发url
    private static final String LOCAL_URL = "api/";
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
     * 点赞梦境
     * @param callBack
     * @param dream
     */
    public void likeDream(BaseHttpService.CallBack callBack, Dream dream) {
        httpService.post(LOCAL_URL + "user/likeDream", dream, callBack, Dream.class);
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

    /**
     * 注册
     *
     */
    public void regester(BaseHttpService.CallBack callBack, User user) {
        httpService.post(LOCAL_URL + "user/register", user, callBack, User.class);
    }

    /**
     * 修改密码
     *
     */
    public void updatePassword(BaseHttpService.CallBack callBack,VoUser user) {
        httpService.put(LOCAL_URL + "user/updatePassword", user, callBack, VoUser.class);
    }

    static public void setCollectDreamToCurrentUser(Dream dream) {
        User currentUser = userService.currentUser.getValue();
        List<Dream> dreamList = currentUser.getCollectDream();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            boolean deleteResult = dreamList.removeIf((dream1 -> dream1.getId().equals(dream.getId())));
            if (!deleteResult) {
                dreamList.add(dream);
            }
        }
        currentUser.setCollectDream(dreamList);
        userService.currentUser.onNext(currentUser);
    }

    public static void loadCurrentUserImage(CircleImageView headshot) {
        User newUser = new NewDownloadTask(userService.currentUser.getValue()).download();
        headshot.setImageBitmap(newUser.getImage());
        userService.currentUser.onNext(newUser);
    }

}
