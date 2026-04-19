package com.osc.backend.config;

import com.osc.backend.entity.Affiliation;
import com.osc.backend.entity.Category;
import com.osc.backend.entity.User;
import com.osc.backend.repository.AffiliationRepository;
import com.osc.backend.repository.CategoryRepository;
import com.osc.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AffiliationRepository affiliationRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(AffiliationRepository affiliationRepository,
                           CategoryRepository categoryRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.affiliationRepository = affiliationRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        initAffiliations();
        initCategories();
        initDefaultUser();
    }

    private void initAffiliations() {
        if (affiliationRepository.count() > 0) return;

        affiliationRepository.save(Affiliation.builder().name("MOHESR").code("0").build());
        affiliationRepository.save(Affiliation.builder().name("Shiite Endowment").code("H").build());
        affiliationRepository.save(Affiliation.builder().name("Sunni Endowment").code("U").build());
        affiliationRepository.save(Affiliation.builder().name("Ministry of Oil").code("L").build());
        affiliationRepository.save(Affiliation.builder().name("Ministry of Communications").code("C").build());
        affiliationRepository.save(Affiliation.builder().name("Ministry of Electricity").code("E").build());
        affiliationRepository.save(Affiliation.builder().name("Ministry of Defense").code("M").build());
        affiliationRepository.save(Affiliation.builder().name("Ministry of Interior").code("N").build());
    }

    private void initCategories() {
        if (categoryRepository.count() > 0) return;

        // Level 1 categories
        categoryRepository.save(Category.builder().name("Public University").code("1").level(1).build());
        categoryRepository.save(Category.builder().name("Private University").code("2").level(1).build());
        categoryRepository.save(Category.builder().name("Non-university College").code("3").level(1).build());
        categoryRepository.save(Category.builder().name("Institute").code("4").level(1).build());

        // Level 2 categories
        categoryRepository.save(Category.builder().name("College").code("5").level(2).build());
        categoryRepository.save(Category.builder().name("Duplicate specialization college").code("R").level(2).build());
        categoryRepository.save(Category.builder().name("Council or Authority").code("A").level(2).build());
        categoryRepository.save(Category.builder().name("Directorate (inside ministry)").code("B").level(2).build());
        categoryRepository.save(Category.builder().name("Directorate (outside ministry)").code("C").level(2).build());

        // Level 3 categories
        categoryRepository.save(Category.builder().name("Academic Department specialization").code("6").level(3).build());
        categoryRepository.save(Category.builder().name("Postgraduate institute or center").code("7").level(3).build());
        categoryRepository.save(Category.builder().name("Research center").code("8").level(3).build());
        categoryRepository.save(Category.builder().name("Administrative department or equivalent").code("D").level(3).build());

        // Level 4 categories
        categoryRepository.save(Category.builder().name("Section").code("E").level(4).build());

        // Level 5 categories
        categoryRepository.save(Category.builder().name("Unit").code("F").level(5).build());
    }

    private void initDefaultUser() {
        if (userRepository.count() > 0) return;

        userRepository.save(User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .role(User.Role.MINISTRY_ADMIN)
                .scopeEntityId(null)
                .build());
    }
}
