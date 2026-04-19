package com.osc.backend.dto;

public class GroupResponse {

    private Long id;
    private String name;
    private Long categoryId;
    private String categoryName;
    private String categoryCode;
    private String levelCode;

    public GroupResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public String getCategoryCode() { return categoryCode; }
    public void setCategoryCode(String categoryCode) { this.categoryCode = categoryCode; }
    public String getLevelCode() { return levelCode; }
    public void setLevelCode(String levelCode) { this.levelCode = levelCode; }

    public static GroupResponseBuilder builder() { return new GroupResponseBuilder(); }

    public static class GroupResponseBuilder {
        private Long id;
        private String name;
        private Long categoryId;
        private String categoryName;
        private String categoryCode;
        private String levelCode;

        public GroupResponseBuilder id(Long id) { this.id = id; return this; }
        public GroupResponseBuilder name(String name) { this.name = name; return this; }
        public GroupResponseBuilder categoryId(Long categoryId) { this.categoryId = categoryId; return this; }
        public GroupResponseBuilder categoryName(String categoryName) { this.categoryName = categoryName; return this; }
        public GroupResponseBuilder categoryCode(String categoryCode) { this.categoryCode = categoryCode; return this; }
        public GroupResponseBuilder levelCode(String levelCode) { this.levelCode = levelCode; return this; }

        public GroupResponse build() {
            GroupResponse r = new GroupResponse();
            r.id = this.id; r.name = this.name; r.categoryId = this.categoryId;
            r.categoryName = this.categoryName; r.categoryCode = this.categoryCode; r.levelCode = this.levelCode;
            return r;
        }
    }
}
