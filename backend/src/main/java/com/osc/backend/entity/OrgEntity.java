package com.osc.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "org_entities", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"parent_id", "level", "level_code"})
})
public class OrgEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(nullable = false)
    private Integer level;

    @Column(name = "level_code", length = 2)
    private String levelCode;

    @Column(name = "full_code", nullable = false, unique = true)
    private String fullCode;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "affiliation_id", nullable = false)
    private Long affiliationId;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "functional_code_id")
    private Long functionalCodeId;

    public OrgEntity() {}

    public OrgEntity(Long id, String name, Long parentId, Integer level, String levelCode,
                     String fullCode, Long categoryId, Long affiliationId, Long groupId, Long functionalCodeId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.level = level;
        this.levelCode = levelCode;
        this.fullCode = fullCode;
        this.categoryId = categoryId;
        this.affiliationId = affiliationId;
        this.groupId = groupId;
        this.functionalCodeId = functionalCodeId;
    }

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
    public Long getAffiliationId() { return affiliationId; }
    public void setAffiliationId(Long affiliationId) { this.affiliationId = affiliationId; }
    public Long getGroupId() { return groupId; }
    public void setGroupId(Long groupId) { this.groupId = groupId; }
    public Long getFunctionalCodeId() { return functionalCodeId; }
    public void setFunctionalCodeId(Long functionalCodeId) { this.functionalCodeId = functionalCodeId; }

    public static OrgEntityBuilder builder() { return new OrgEntityBuilder(); }

    public static class OrgEntityBuilder {
        private Long id;
        private String name;
        private Long parentId;
        private Integer level;
        private String levelCode;
        private String fullCode;
        private Long categoryId;
        private Long affiliationId;
        private Long groupId;
        private Long functionalCodeId;

        public OrgEntityBuilder id(Long id) { this.id = id; return this; }
        public OrgEntityBuilder name(String name) { this.name = name; return this; }
        public OrgEntityBuilder parentId(Long parentId) { this.parentId = parentId; return this; }
        public OrgEntityBuilder level(Integer level) { this.level = level; return this; }
        public OrgEntityBuilder levelCode(String levelCode) { this.levelCode = levelCode; return this; }
        public OrgEntityBuilder fullCode(String fullCode) { this.fullCode = fullCode; return this; }
        public OrgEntityBuilder categoryId(Long categoryId) { this.categoryId = categoryId; return this; }
        public OrgEntityBuilder affiliationId(Long affiliationId) { this.affiliationId = affiliationId; return this; }
        public OrgEntityBuilder groupId(Long groupId) { this.groupId = groupId; return this; }
        public OrgEntityBuilder functionalCodeId(Long functionalCodeId) { this.functionalCodeId = functionalCodeId; return this; }

        public OrgEntity build() {
            return new OrgEntity(id, name, parentId, level, levelCode, fullCode, categoryId, affiliationId, groupId, functionalCodeId);
        }
    }
}
