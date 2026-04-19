package com.osc.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "level_code", nullable = false, length = 2)
    private String levelCode;

    public Group() {}

    public Group(Long id, String name, Long categoryId, String levelCode) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.levelCode = levelCode;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public String getLevelCode() { return levelCode; }
    public void setLevelCode(String levelCode) { this.levelCode = levelCode; }

    public static GroupBuilder builder() { return new GroupBuilder(); }

    public static class GroupBuilder {
        private Long id;
        private String name;
        private Long categoryId;
        private String levelCode;

        public GroupBuilder id(Long id) { this.id = id; return this; }
        public GroupBuilder name(String name) { this.name = name; return this; }
        public GroupBuilder categoryId(Long categoryId) { this.categoryId = categoryId; return this; }
        public GroupBuilder levelCode(String levelCode) { this.levelCode = levelCode; return this; }

        public Group build() { return new Group(id, name, categoryId, levelCode); }
    }
}
