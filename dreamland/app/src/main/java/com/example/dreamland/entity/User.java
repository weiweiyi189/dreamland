package com.example.dreamland.entity;

import android.graphics.Bitmap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统用户
 */
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends LitePalSupport implements Serializable {

    private Long id;

    @Column(unique = true, defaultValue = "unknown")
    private String username;
    @Column(defaultValue = "123")
    private String password;

    private String imageUrl;

    private List<Letter> letters = new ArrayList<>();
    private List<Dream> collectDream;

    private transient Bitmap image;
}
