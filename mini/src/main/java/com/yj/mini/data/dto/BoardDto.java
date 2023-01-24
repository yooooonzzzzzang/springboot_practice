package com.yj.mini.data.dto;

import com.yj.mini.data.entity.SiteUser;

public class BoardDto {
    private String title;
    private String description;
    private SiteUser author;

    public BoardDto(String title, String description, SiteUser author) {
        this.title = title;
        this.description = description;
        this.author = author;
    }

    public SiteUser getAuthor() {
        return author;
    }

    public void setAuthor(SiteUser author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
