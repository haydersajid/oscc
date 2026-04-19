package com.osc.backend.controller;

import com.osc.backend.dto.EntityRequest;
import com.osc.backend.dto.EntityResponse;
import com.osc.backend.dto.TreeNode;
import com.osc.backend.service.OrgEntityService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entities")
public class EntityController {

    private final OrgEntityService orgEntityService;

    public EntityController(OrgEntityService orgEntityService) {
        this.orgEntityService = orgEntityService;
    }

    @PostMapping
    public ResponseEntity<EntityResponse> createEntity(@RequestBody EntityRequest request,
                                                        HttpServletRequest httpRequest) {
        EntityResponse response = orgEntityService.createEntity(request, httpRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<EntityResponse>> getAllEntities(HttpServletRequest httpRequest) {
        List<EntityResponse> entities = orgEntityService.getAllEntities(httpRequest);
        return ResponseEntity.ok(entities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityResponse> getEntity(@PathVariable Long id,
                                                     HttpServletRequest httpRequest) {
        EntityResponse response = orgEntityService.getEntity(id, httpRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tree")
    public ResponseEntity<List<TreeNode>> getTree(HttpServletRequest httpRequest) {
        List<TreeNode> tree = orgEntityService.getTree(httpRequest);
        return ResponseEntity.ok(tree);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityResponse> updateEntity(@PathVariable Long id,
                                                        @RequestBody EntityRequest request,
                                                        HttpServletRequest httpRequest) {
        EntityResponse response = orgEntityService.updateEntity(id, request, httpRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntity(@PathVariable Long id,
                                              HttpServletRequest httpRequest) {
        orgEntityService.deleteEntity(id, httpRequest);
        return ResponseEntity.noContent().build();
    }
}
