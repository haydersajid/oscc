package com.osc.backend.service;

import com.osc.backend.dto.GroupRequest;
import com.osc.backend.dto.GroupResponse;
import com.osc.backend.entity.Category;
import com.osc.backend.entity.Group;
import com.osc.backend.exception.OscException;
import com.osc.backend.repository.CategoryRepository;
import com.osc.backend.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final CategoryRepository categoryRepository;

    public GroupService(GroupRepository groupRepository, CategoryRepository categoryRepository) {
        this.groupRepository = groupRepository;
        this.categoryRepository = categoryRepository;
    }

    public GroupResponse createGroup(GroupRequest request) {
        if (request.getName() == null || request.getName().isBlank()) {
            throw new OscException("Group name is required.");
        }
        if (request.getCategoryId() == null) {
            throw new OscException("Category is required for group.");
        }
        if (request.getLevelCode() == null || request.getLevelCode().isBlank()) {
            throw new OscException("Level code is required for group.");
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new OscException("Category not found."));

        Group group = Group.builder()
                .name(request.getName())
                .categoryId(request.getCategoryId())
                .levelCode(request.getLevelCode().toUpperCase())
                .build();

        Group saved = groupRepository.save(group);
        return toResponse(saved, category);
    }

    public List<GroupResponse> getAllGroups() {
        return groupRepository.findAll().stream()
                .map(g -> {
                    Category cat = categoryRepository.findById(g.getCategoryId()).orElse(null);
                    return toResponse(g, cat);
                })
                .collect(Collectors.toList());
    }

    private GroupResponse toResponse(Group group, Category category) {
        return GroupResponse.builder()
                .id(group.getId())
                .name(group.getName())
                .categoryId(group.getCategoryId())
                .categoryName(category != null ? category.getName() : null)
                .categoryCode(category != null ? category.getCode() : null)
                .levelCode(group.getLevelCode())
                .build();
    }
}
