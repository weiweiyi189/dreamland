package com.example.dreamland.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统用户
 */
@EqualsAndHashCode(callSuper=false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends LitePalSupport {

    private int id;

    @Column(unique = true, defaultValue = "unknown")
    private String username;
    @Column(defaultValue = "123")
    private String password;

    private List<Dream> dreams = new ArrayList<>();
}
