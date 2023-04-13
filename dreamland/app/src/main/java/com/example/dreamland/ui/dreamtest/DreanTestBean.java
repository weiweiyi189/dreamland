package com.example.dreamland.ui.dreamtest;

public class DreanTestBean {
    private String title;
    private String content;
    //0,单选；1多选
    private int type;

    public DreanTestBean(String title, String content, int type) {
        this.title = title;
        this.content = content;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
