package com.osc.backend.dto;

public class EntityRequest {

    private String name;
    private Long parentId;
    private Integer level;
    private Long categoryId;
    private Long affiliationId;
    private Long groupId;
    private Long functionalCodeId;

    public EntityRequest() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }
    public Integer getLevel() { return level; }
    public void setLevel(Integer level) { this.level = level; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public Long getAffiliationId() { return affiliationId; }
    public void setAffiliationId(Long affiliationId) { this.affiliationId = affiliationId; }
    public Long getGroupId() { return groupId; }
    public void setGroupId(Long groupId) { this.groupId = groupId; }
    public Long getFunctionalCodeId() { return functionalCodeId; }
    public void setFunctionalCodeId(Long functionalCodeId) { this.functionalCodeId = functionalCodeId; }
}

