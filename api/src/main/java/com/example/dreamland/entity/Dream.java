package com.example.dreamland.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * 梦境实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
public class Dream {

    public static final String TABLE_NAME = "dream";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonView(UserJsonView.class)
    private User createUser;

    // 梦境内容
    private String content;

    // 图片， 后期看时间再考虑是否可以上传图片
    @ElementCollection
    private List<String> imageUrl;

    // 创建时间
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createTime;

    /**
     * 点赞数
     */
    private Integer likes = 0;

    public interface UserJsonView {}

}
