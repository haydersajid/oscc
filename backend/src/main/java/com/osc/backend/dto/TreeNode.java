package com.osc.backend.dto;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {

    private Long id;
    private String name;
    private Integer level;
    private String levelCode;
    private String fullCode;
    private String categoryName;
    private String affiliationName;
    private List<TreeNode> children = new ArrayList<>();

    public TreeNode() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getLevel() { return level; }
    public void setLevel(Integer level) { this.level = level; }
    public String getLevelCode() { return levelCode; }
    public void setLevelCode(String levelCode) { this.levelCode = levelCode; }
    public String getFullCode() { return fullCode; }
    public void setFullCode(String fullCode) { this.fullCode = fullCode; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public String getAffiliationName() { return affiliationName; }
    public void setAffiliationName(String affiliationName) { this.affiliationName = affiliationName; }
    public List<TreeNode> getChildren() { return children; }
    public void setChildren(List<TreeNode> children) { this.children = children; }

    public static TreeNodeBuilder builder() { return new TreeNodeBuilder(); }

    public static class TreeNodeBuilder {
        private Long id;
        private String name;
        private Integer level;
        private String levelCode;
        private String fullCode;
        private String categoryName;
        private String affiliationName;
        private List<TreeNode> children = new ArrayList<>();

        public TreeNodeBuilder id(Long id) { this.id = id; return this; }
        public TreeNodeBuilder name(String name) { this.name = name; return this; }
        public TreeNodeBuilder level(Integer level) { this.level = level; return this; }
        public TreeNodeBuilder levelCode(String levelCode) { this.levelCode = levelCode; return this; }
        public TreeNodeBuilder fullCode(String fullCode) { this.fullCode = fullCode; return this; }
        public TreeNodeBuilder categoryName(String categoryName) { this.categoryName = categoryName; return this; }
        public TreeNodeBuilder affiliationName(String affiliationName) { this.affiliationName = affiliationName; return this; }
        public TreeNodeBuilder children(List<TreeNode> children) { this.children = children; return this; }

        public TreeNode build() {
            TreeNode n = new TreeNode();
            n.id = this.id; n.name = this.name; n.level = this.level;
            n.levelCode = this.levelCode; n.fullCode = this.fullCode;
            n.categoryName = this.categoryName; n.affiliationName = this.affiliationName;
            n.children = this.children;
            return n;
        }
    }
}
