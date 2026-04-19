package com.osc.backend.dto;

public class GroupRequest {

    private String name;
    private Long categoryId;
    private String levelCode;

    public GroupRequest() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public String getLevelCode() { return levelCode; }
    public void setLevelCode(String levelCode) { this.levelCode = levelCode; }
}
