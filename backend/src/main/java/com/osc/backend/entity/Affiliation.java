package com.osc.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "affiliations")
public class Affiliation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, length = 1)
    private String code;

    public Affiliation() {}

    public Affiliation(Long id, String name, String code) {
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

    public static AffiliationBuilder builder() { return new AffiliationBuilder(); }

    public static class AffiliationBuilder {
        private Long id;
        private String name;
        private String code;

        public AffiliationBuilder id(Long id) { this.id = id; return this; }
        public AffiliationBuilder name(String name) { this.name = name; return this; }
        public AffiliationBuilder code(String code) { this.code = code; return this; }

        public Affiliation build() { return new Affiliation(id, name, code); }
    }
}
