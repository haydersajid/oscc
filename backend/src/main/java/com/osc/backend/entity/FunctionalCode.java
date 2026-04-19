package com.osc.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "functional_codes")
public class FunctionalCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    public FunctionalCode() {}

    public FunctionalCode(Long id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public static FunctionalCodeBuilder builder() { return new FunctionalCodeBuilder(); }

    public static class FunctionalCodeBuilder {
        private Long id;
        private String name;
        private String code;

        public FunctionalCodeBuilder id(Long id) { this.id = id; return this; }
        public FunctionalCodeBuilder name(String name) { this.name = name; return this; }
        public FunctionalCodeBuilder code(String code) { this.code = code; return this; }

        public FunctionalCode build() { return new FunctionalCode(id, name, code); }
    }
}
