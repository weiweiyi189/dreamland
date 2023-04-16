package com.example.dreamland.entity;

import android.text.Editable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.litepal.crud.LitePalSupport;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
    private List<String> comment;

    public void addComment(String text) {
        comment.add(text);
    }
}
