package com.example.dreamland.entity;

import lombok.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.litepal.crud.LitePalSupport;

import java.util.Date;


/**
 * 梦境实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
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
