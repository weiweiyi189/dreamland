package com.example.dreamland.db;

import com.example.dreamland.entity.Dream;
import com.example.dreamland.entity.User;
import org.litepal.LitePal;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 为数据库提供初始化。
 */
public class initDataBase {

    // 暂时设置当前登录用户
    static public User currentLoginUser;

    static public void init() {
        List<Dream> dreams = LitePal.findAll(Dream.class, true);
        if (dreams.size() == 0) {
            initDataBase.save();
        }
        currentLoginUser = LitePal.findLast(User.class, true);
    }

    static public void save() {
        LitePal.deleteAll(Dream.class);
        LitePal.deleteAll(User.class);
        for (int i = 0; i < 10; i++) {
            User newUser = new User();
            newUser.setUsername("用户" + new Random().nextInt(1000));
            newUser.save();
            newUser = LitePal.find(User.class, newUser.getId());
            Dream dream = new Dream();
            dream.setContent("梦到世界大危机，反派与正派对峙，正派死伤惨重，\n" +
                    "几个主角都被抓。而我被委托以重要使命，跳井穿越时空拿到重要宝物压制反派.结果时间乱流寄了。");
            dream.setCreateTime(new Timestamp(new Date().getTime()));
            dream.setCreateUser(newUser);
            boolean result = dream.save();
        }
    }
}
