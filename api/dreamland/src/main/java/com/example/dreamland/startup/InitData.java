package com.example.dreamland.startup;

import com.example.dreamland.entity.Dream;
import com.example.dreamland.entity.Letter;
import com.example.dreamland.entity.User;
import com.example.dreamland.repository.DreamRepository;
import com.example.dreamland.repository.LetterRepository;
import com.example.dreamland.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 系统数据初始化.
 */
@Component
public class InitData {
  private static final Logger logger = LoggerFactory.getLogger(InitData.class);

  private UserRepository userRepository;

  private DreamRepository dreamRepository;
  private LetterRepository letterRepository;

  private String Username = "admin";
  private String Password = "admin";


  public InitData(UserRepository userRepository,
                  DreamRepository dreamRepository,
                  LetterRepository letterRepository) {
    this.letterRepository=letterRepository;
    this.dreamRepository = dreamRepository;
    this.userRepository = userRepository;
  }

  @PostConstruct
  public void postConstruct() {

    List<User> users = this.userRepository.findAll();

    if (!users.isEmpty()) {
      this.initDream(users.get(0));
      return;
    }

    User user1 = new User();
    user1.setUsername(this.Username);
    user1.setPassword(this.Password);
    users.add(user1);
    User user2 = new User();
    user2.setUsername("13900000000");
    user2.setPassword(this.Password);
    users.add(user2);
    User user3=new User();
    user3.setUsername("快斗斗丶");
    user3.setPassword(this.Password);
    users.add(user3);
    this.userRepository.saveAll(users);
    this.initDream(users.get(0));
    this.initLetter(user3);
  }

  private void initLetter(User user) {
    List<Letter> letters=this.letterRepository.findAll();
    if(!letters.isEmpty()){
      return;
    }

    Letter letter1=new Letter();
    letter1.setTitle("漂流——一方天地, 一方奇遇");
    letter1.setContent("  是载满星云的玄霄, 亦是播洒清梦的红壤.\n  漂出你的思绪, 捞起我的奇遇.\n  这里, 皆你我的天地.\n");
    letter1.setCreateTime(new Timestamp(new Date().getTime()+1));
    letter1.setCreateUser(user);
    this.letterRepository.save(letter1);

    Letter letter2=new Letter();
    letter2.setTitle("发病时间");
    letter2.setContent("  If not for Scaramouche, who would be working?\n嘿嘿嘿, Little Meow Meow, 嘶哈嘶哈\\uD83E\\uDD24, （尖叫）（扭曲）（阴暗地爬行）（尖叫）（扭曲）（阴暗地爬行）（尖叫） （爬行）（扭动）（分裂）（阴暗地蠕动）（翻滚）（激烈地爬动）（扭曲）（痉挛）（嘶吼）（蠕动）（阴森地低吼）（爬行）（分裂）（走上岸）（扭动）（痉挛）（蠕动）（扭曲地行走）\n");
    letter2.setCreateTime(new Timestamp(new Date().getTime()));
    letter2.setCreateUser(user);
    this.letterRepository.save(letter2);
  }

  private void initDream(User user1) {
    List<Dream> dreams = this.dreamRepository.findAll();

    if (!dreams.isEmpty()) {
      return;
    }

    for(int i = 0; i< 10; i++) {
      Dream dream = new Dream();
      dream.setContent("梦到世界大危机，反派与正派对峙，正派死伤惨重，\n" +
              "几个主角都被抓。而我被委托以重要使命，跳井穿越时空拿到重要宝物压制反派.结果时间乱流寄了。");
      dream.setCreateTime(new Timestamp(new Date().getTime()));
      dream.setCreateUser(user1);
      this.dreamRepository.save(dream);
    }
  }
}
