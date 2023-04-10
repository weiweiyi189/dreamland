package com.example.dreamland.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class DreamComment implements Serializable {

    private int id;

    private User createUser;


    private String content;

    // 创建时间
    private Timestamp createTime;

    // 所属梦境
    private Dream dream;
}
