package com.example.dreamland.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseMessage {
    String message;
    User sender;
    long createdAt;
    int type;
}
