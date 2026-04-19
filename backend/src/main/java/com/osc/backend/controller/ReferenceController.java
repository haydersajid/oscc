package com.osc.backend.controller;

import com.osc.backend.entity.Affiliation;
import com.osc.backend.entity.Category;
import com.osc.backend.entity.FunctionalCode;
import com.osc.backend.repository.AffiliationRepository;
import com.osc.backend.repository.CategoryRepository;
import com.osc.backend.repository.FunctionalCodeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReferenceController {

    private final CategoryRepository categoryRepository;
    private final AffiliationRepository affiliationRepository;
    private final FunctionalCodeRepository functionalCodeRepository;

    public ReferenceController(CategoryRepository categoryRepository,
                                AffiliationRepository affiliationRepository,
                                FunctionalCodeRepository functionalCodeRepository) {
        this.categoryRepository = categoryRepository;
        this.affiliationRepository = affiliationRepository;
        this.functionalCodeRepository = functionalCodeRepository;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @GetMapping("/affiliations")
    public ResponseEntity<List<Affiliation>> getAffiliations() {
        return ResponseEntity.ok(affiliationRepository.findAll());
    }

    @GetMapping("/functional-codes")
    public ResponseEntity<List<FunctionalCode>> getFunctionalCodes() {
        return ResponseEntity.ok(functionalCodeRepository.findAll());
    }
}
