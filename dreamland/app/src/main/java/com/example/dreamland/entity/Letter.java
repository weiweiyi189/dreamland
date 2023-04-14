package com.example.dreamland.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.litepal.crud.LitePalSupport;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Letter extends LitePalSupport{
    private int id;
    private User createUser;
    private String title;
    private String content;
    private Timestamp createTime;
}
