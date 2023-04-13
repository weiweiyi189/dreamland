package com.example.dreamland.db;

import com.example.dreamland.entity.Dream;
import com.example.dreamland.entity.Letter;
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

    public static void saveLetter() {
        LitePal.deleteAll(Letter.class);

        User user=new User();
        user.setUsername("快斗斗丶");
        user.save();
        user=LitePal.find(User.class,user.getId());

        Letter letter1=new Letter();
        letter1.setTitle("漂流——一方天地, 一方奇遇");
        letter1.setContent("  是载满星云的玄霄, 亦是播洒清梦的红壤.\n  漂出你的思绪, 捞起我的奇遇.\n  这里, 皆你我的天地.\n");
        letter1.setCreateTime(new Timestamp(new Date().getTime()));
        letter1.setCreateUser(user);
        letter1.save();

        Letter letter2=new Letter();
        letter2.setTitle("发病时间");
        letter2.setContent("  If not for Scaramouche, who would be working?\n嘿嘿嘿, Little Meow Meow, 嘶哈嘶哈\\uD83E\\uDD24, （尖叫）（扭曲）（阴暗地爬行）（尖叫）（扭曲）（阴暗地爬行）（尖叫） （爬行）（扭动）（分裂）（阴暗地蠕动）（翻滚）（激烈地爬动）（扭曲）（痉挛）（嘶吼）（蠕动）（阴森地低吼）（爬行）（分裂）（走上岸）（扭动）（痉挛）（蠕动）（扭曲地行走）\n");
        letter2.setCreateTime(new Timestamp(new Date().getTime()));
        letter2.setCreateUser(user);
        letter2.save();
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
