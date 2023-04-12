package com.example.dreamland.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统用户
 */
@EqualsAndHashCode(callSuper=false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    static public final String TABLE_NAME = "user";

    public static final String DEFAULT_PASSWORD = "hebut";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password = DEFAULT_PASSWORD;

    @ManyToMany
    @JoinTable(name = User.TABLE_NAME + "_" + Dream.TABLE_NAME,
            joinColumns = @JoinColumn(name = User.TABLE_NAME + "_id"),
            inverseJoinColumns = @JoinColumn(name = Dream.TABLE_NAME + "_id"))
    @JsonView(CollectDreamJsonView.class)
    private List<Dream> collectDream;

    // 头像
    private String imageUrl;

    @ManyToMany
    @JsonBackReference
    private List<Letter> letters=new ArrayList<>();

    public void addLetter(Letter letter) {
        letters.add(letter);
    }
    public interface CollectDreamJsonView {
    }
}
