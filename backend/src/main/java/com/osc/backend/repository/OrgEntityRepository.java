package com.osc.backend.repository;

import com.osc.backend.entity.OrgEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrgEntityRepository extends JpaRepository<OrgEntity, Long> {

    List<OrgEntity> findByParentId(Long parentId);

    List<OrgEntity> findByParentIdIsNull();

    List<OrgEntity> findByLevel(Integer level);

    boolean existsByParentIdAndLevelAndLevelCode(Long parentId, Integer level, String levelCode);

    @Query("SELECT e FROM OrgEntity e WHERE e.level = 1 AND e.fullCode LIKE :prefix ORDER BY e.fullCode DESC")
    List<OrgEntity> findLevel1ByClassificationPrefix(@Param("prefix") String prefix);

    @Query("SELECT e FROM OrgEntity e WHERE e.level = 1 AND e.fullCode LIKE CONCAT(:classChar, '-%') ORDER BY e.fullCode DESC")
    List<OrgEntity> findLevel1ByClassification(@Param("classChar") String classChar);

    Optional<OrgEntity> findByFullCode(String fullCode);

    // For Level 1 with no parent: check uniqueness by level and levelCode where parentId is null
    boolean existsByParentIdIsNullAndLevelAndLevelCode(Integer level, String levelCode);

    // Fetch all level codes for entities with a given parentId and level (for auto-generation)
    @Query("SELECT e.levelCode FROM OrgEntity e WHERE e.parentId = :parentId AND e.level = :level")
    List<String> findLevelCodesByParentIdAndLevel(@Param("parentId") Long parentId, @Param("level") Integer level);

    // Fetch all level codes for root entities (null parent) at a given level (for auto-generation)
    @Query("SELECT e.levelCode FROM OrgEntity e WHERE e.parentId IS NULL AND e.level = :level")
    List<String> findLevelCodesByParentIdIsNullAndLevel(@Param("level") Integer level);
}

