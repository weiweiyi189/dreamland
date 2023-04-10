package com.example.dreamland.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
public class DreamComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonView(UserJsonView.class)
    private User createUser;

    // 梦境内容
    private String content;

    // 创建时间
    @CreationTimestamp
    private Date createTime;

    @ManyToOne
    @JsonView(DreamJsonView.class)
    private Dream dream;

    public interface UserJsonView {
    }

    public interface DreamJsonView {
    }

}
