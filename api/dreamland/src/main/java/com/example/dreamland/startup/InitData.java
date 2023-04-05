package com.example.dreamland.startup;

import com.example.dreamland.entity.Dream;
import com.example.dreamland.entity.User;
import com.example.dreamland.repository.DreamRepository;
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

  private String Username = "admin";
  private String Password = "admin";


  public InitData(UserRepository userRepository,
                  DreamRepository dreamRepository) {
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
    this.userRepository.saveAll(users);
    this.initDream(users.get(0));

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
