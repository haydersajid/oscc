package com.osc.backend.dto;

public class EntityResponse {

    private Long id;
    private String name;
    private Long parentId;
    private Integer level;
    private String levelCode;
    private String fullCode;
    private Long categoryId;
    private String categoryName;
    private String categoryCode;
    private Long affiliationId;
    private String affiliationName;
    private String affiliationCode;
    private Long groupId;
    private Long functionalCodeId;

    public EntityResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }
    public Integer getLevel() { return level; }
    public void setLevel(Integer level) { this.level = level; }
    public String getLevelCode() { return levelCode; }
    public void setLevelCode(String levelCode) { this.levelCode = levelCode; }
    public String getFullCode() { return fullCode; }
    public void setFullCode(String fullCode) { this.fullCode = fullCode; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public String getCategoryCode() { return categoryCode; }
    public void setCategoryCode(String categoryCode) { this.categoryCode = categoryCode; }
    public Long getAffiliationId() { return affiliationId; }
    public void setAffiliationId(Long affiliationId) { this.affiliationId = affiliationId; }
    public String getAffiliationName() { return affiliationName; }
    public void setAffiliationName(String affiliationName) { this.affiliationName = affiliationName; }
    public String getAffiliationCode() { return affiliationCode; }
    public void setAffiliationCode(String affiliationCode) { this.affiliationCode = affiliationCode; }
    public Long getGroupId() { return groupId; }
    public void setGroupId(Long groupId) { this.groupId = groupId; }
    public Long getFunctionalCodeId() { return functionalCodeId; }
    public void setFunctionalCodeId(Long functionalCodeId) { this.functionalCodeId = functionalCodeId; }

    public static EntityResponseBuilder builder() { return new EntityResponseBuilder(); }

    public static class EntityResponseBuilder {
        private Long id;
        private String name;
        private Long parentId;
        private Integer level;
        private String levelCode;
        private String fullCode;
        private Long categoryId;
        private String categoryName;
        private String categoryCode;
        private Long affiliationId;
        private String affiliationName;
        private String affiliationCode;
        private Long groupId;
        private Long functionalCodeId;

        public EntityResponseBuilder id(Long id) { this.id = id; return this; }
        public EntityResponseBuilder name(String name) { this.name = name; return this; }
        public EntityResponseBuilder parentId(Long parentId) { this.parentId = parentId; return this; }
        public EntityResponseBuilder level(Integer level) { this.level = level; return this; }
        public EntityResponseBuilder levelCode(String levelCode) { this.levelCode = levelCode; return this; }
        public EntityResponseBuilder fullCode(String fullCode) { this.fullCode = fullCode; return this; }
        public EntityResponseBuilder categoryId(Long categoryId) { this.categoryId = categoryId; return this; }
        public EntityResponseBuilder categoryName(String categoryName) { this.categoryName = categoryName; return this; }
        public EntityResponseBuilder categoryCode(String categoryCode) { this.categoryCode = categoryCode; return this; }
        public EntityResponseBuilder affiliationId(Long affiliationId) { this.affiliationId = affiliationId; return this; }
        public EntityResponseBuilder affiliationName(String affiliationName) { this.affiliationName = affiliationName; return this; }
        public EntityResponseBuilder affiliationCode(String affiliationCode) { this.affiliationCode = affiliationCode; return this; }
        public EntityResponseBuilder groupId(Long groupId) { this.groupId = groupId; return this; }
        public EntityResponseBuilder functionalCodeId(Long functionalCodeId) { this.functionalCodeId = functionalCodeId; return this; }

        public EntityResponse build() {
            EntityResponse r = new EntityResponse();
            r.id = this.id; r.name = this.name; r.parentId = this.parentId;
            r.level = this.level; r.levelCode = this.levelCode; r.fullCode = this.fullCode;
            r.categoryId = this.categoryId; r.categoryName = this.categoryName; r.categoryCode = this.categoryCode;
            r.affiliationId = this.affiliationId; r.affiliationName = this.affiliationName; r.affiliationCode = this.affiliationCode;
            r.groupId = this.groupId; r.functionalCodeId = this.functionalCodeId;
            return r;
        }
    }
}
