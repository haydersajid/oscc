package com.osc.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, length = 1)
    private String code;

    @Column(nullable = false)
    private Integer level;

    public Category() {}

    public Category(Long id, String name, String code, Integer level) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.level = level;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public Integer getLevel() { return level; }
    public void setLevel(Integer level) { this.level = level; }

    public static CategoryBuilder builder() { return new CategoryBuilder(); }

    public static class CategoryBuilder {
        private Long id;
        private String name;
        private String code;
        private Integer level;

        public CategoryBuilder id(Long id) { this.id = id; return this; }
        public CategoryBuilder name(String name) { this.name = name; return this; }
        public CategoryBuilder code(String code) { this.code = code; return this; }
        public CategoryBuilder level(Integer level) { this.level = level; return this; }

        public Category build() { return new Category(id, name, code, level); }
    }
}
