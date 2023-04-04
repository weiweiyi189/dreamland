package com.example.dreamland.entity;

import lombok.*;
import org.litepal.crud.LitePalSupport;

import java.util.Date;


/**
 * 梦境实体
 */
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dream extends LitePalSupport {

    private int id;

    private User createUser;

    // 梦境内容
    private String content;

    // 图片， 后期看时间再考虑是否可以上传图片
    private String[] imageUrl;

    // 创建时间
    private Date createTime;
}
