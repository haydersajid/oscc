package com.osc.backend.service;

import com.osc.backend.entity.Affiliation;
import com.osc.backend.entity.Category;
import com.osc.backend.entity.OrgEntity;
import com.osc.backend.exception.OscException;
import com.osc.backend.repository.OrgEntityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CodeGenerationService {

    private static final Set<Character> FORBIDDEN_CHARS = Set.of('I', 'O', 'X');

    private final OrgEntityRepository orgEntityRepository;

    public CodeGenerationService(OrgEntityRepository orgEntityRepository) {
        this.orgEntityRepository = orgEntityRepository;
    }

    /**
     * Generate the full OSC code for an entity.
     * Format: X1-X2X3-X4X5-X6X7-X8X9-X10X11-X12X13
     */
    public String generateFullCode(OrgEntity entity, OrgEntity parent,
                                   Category category, Affiliation affiliation) {
        int level = entity.getLevel();
        String affiliationCode = affiliation.getCode();
        String categoryCode = category.getCode();

        if (level == 1) {
            return generateLevel1Code(category, affiliationCode, categoryCode);
        } else {
            return generateChildCode(entity, parent, affiliationCode, categoryCode);
        }
    }

    /**
     * Generate Level 1 code with auto-generated X1 and X2X3.
     */
    private String generateLevel1Code(Category category, String affiliationCode, String categoryCode) {
        // Determine the classification range based on category
        int[] classRange = getClassificationRange(category.getCode());
        int minClass = classRange[0];
        int maxClass = classRange[1];

        // Find next available X1 and X2X3
        String levelCode = null;
        int chosenClassification = -1;

        for (int x1 = minClass; x1 <= maxClass; x1++) {
            String classStr = String.valueOf(x1);
            List<OrgEntity> existing = orgEntityRepository.findLevel1ByClassification(classStr);

            if (existing.isEmpty()) {
                // First entity in this classification
                levelCode = "01";
                chosenClassification = x1;
                break;
            }

            // Find max X2X3 in this classification
            String maxX2X3 = "00";
            for (OrgEntity e : existing) {
                String code = e.getFullCode();
                // Format: X1-X2X3-...
                String x2x3 = code.substring(2, 4);
                if (x2x3.compareTo(maxX2X3) > 0) {
                    maxX2X3 = x2x3;
                }
            }

            // Increment X2X3
            String next = incrementAlphanumeric(maxX2X3);
            if (next != null) {
                levelCode = next;
                chosenClassification = x1;
                break;
            }
            // X2X3 exhausted for this X1, try next X1
        }

        if (levelCode == null || chosenClassification == -1) {
            throw new OscException("Classification range exhausted. Cannot create more entities in this category.");
        }

        // Build full code: X1-X2X3-00-00-00-00-X12X13
        return chosenClassification + "-" + levelCode + "-00-00-00-00-" + affiliationCode + categoryCode;
    }

    /**
     * Generate child code (Level 2-5) from parent code.
     */
    private String generateChildCode(OrgEntity entity, OrgEntity parent,
                                     String affiliationCode, String categoryCode) {
        String levelCode = entity.getLevelCode();
        validateLevelCode(levelCode);

        String parentFullCode = parent.getFullCode();
        // Parse parent code segments: X1-X2X3-X4X5-X6X7-X8X9-X10X11-X12X13
        String[] parts = parentFullCode.split("-");

        // parts[0] = X1, parts[1] = X2X3, parts[2] = X4X5, parts[3] = X6X7,
        // parts[4] = X8X9, parts[5] = X10X11, parts[6] = X12X13

        String x1 = parts[0];
        String[] levelCodes = new String[5];
        levelCodes[0] = parts[1]; // Level 1 code (X2X3)
        levelCodes[1] = parts[2]; // Level 2 code (X4X5)
        levelCodes[2] = parts[3]; // Level 3 code (X6X7)
        levelCodes[3] = parts[4]; // Level 4 code (X8X9)
        levelCodes[4] = parts[5]; // Level 5 code (X10X11)

        // Set the current entity's level code at its position
        int levelIndex = entity.getLevel() - 1; // 0-based
        levelCodes[levelIndex] = levelCode;

        // Fill levels below current with "00"
        for (int i = levelIndex + 1; i < 5; i++) {
            levelCodes[i] = "00";
        }

        // Build full code
        return x1 + "-" + levelCodes[0] + "-" + levelCodes[1] + "-" + levelCodes[2]
                + "-" + levelCodes[3] + "-" + levelCodes[4] + "-" + affiliationCode + categoryCode;
    }

    /**
     * Validate that a level code follows alphanumeric rules.
     * Must be exactly 2 characters, A-Z or 0-9, excluding I, O, X.
     */
    public void validateLevelCode(String levelCode) {
        if (levelCode == null || levelCode.length() != 2) {
            throw new OscException("Level code must be exactly 2 characters.");
        }

        String upper = levelCode.toUpperCase();
        for (char c : upper.toCharArray()) {
            if (!((c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9'))) {
                throw new OscException("Level code contains invalid character: " + c
                        + ". Only A-Z and 0-9 are allowed.");
            }
            if (FORBIDDEN_CHARS.contains(c)) {
                throw new OscException("Level code contains forbidden character: " + c
                        + ". I, O, X are not allowed.");
            }
        }
    }

    /**
     * Get the classification range [min, max] based on category code.
     */
    private int[] getClassificationRange(String categoryCode) {
        return switch (categoryCode) {
            case "1" -> new int[]{1, 4};    // Public universities
            case "2" -> new int[]{5, 8};    // Private universities
            default -> new int[]{0, 0};     // MOHESR and others default to 0
        };
    }

    /**
     * Increment a 2-character alphanumeric code.
     * Uses 0-9, A-Z excluding I, O, X (33 valid characters).
     * Returns null if the code has reached the maximum ("99" if numeric only,
     * or the last valid combination).
     */
    private String incrementAlphanumeric(String code) {
        char[] validChars = getValidChars();
        int base = validChars.length;

        // Convert code to indices
        int idx0 = indexOf(validChars, code.charAt(0));
        int idx1 = indexOf(validChars, code.charAt(1));

        if (idx0 == -1 || idx1 == -1) {
            // Current code uses characters not in our set, start fresh
            return null;
        }

        // Increment
        idx1++;
        if (idx1 >= base) {
            idx1 = 0;
            idx0++;
            if (idx0 >= base) {
                return null; // Exhausted
            }
        }

        return "" + validChars[idx0] + validChars[idx1];
    }

    private char[] getValidChars() {
        // 0-9 then A-Z excluding I, O, X
        StringBuilder sb = new StringBuilder();
        for (char c = '0'; c <= '9'; c++) {
            sb.append(c);
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            if (!FORBIDDEN_CHARS.contains(c)) {
                sb.append(c);
            }
        }
        return sb.toString().toCharArray();
    }

    private int indexOf(char[] arr, char c) {
        char upper = Character.toUpperCase(c);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == upper) return i;
        }
        return -1;
    }
}
