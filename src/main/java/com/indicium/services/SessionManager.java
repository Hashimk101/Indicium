package com.indicium.services;

import com.indicium.models.SystemUser;
import com.indicium.models.UserRole;
import com.indicium.services.AuditLog;
import com.indicium.services.AuditCategory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

public class SessionManager {
    private static SessionManager instance;
    private SystemUser currentUser;
    private LocalDateTime startTime;

    private SessionManager() {
        startTime = LocalDateTime.now();
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void loginUser(SystemUser user) {
        this.currentUser = user;
        this.startTime = LocalDateTime.now();
        // Audit the login event
        AuditLog auditLog = new AuditLog();
        auditLog.logEvent(user.getUserID(), "User logged in [" + user.getRole().name() + "]", AuditCategory.SYSTEM);
    }

    public void logoutUser() {
        if (this.currentUser != null) {
            // Audit the logout event before clearing the session
            AuditLog auditLog = new AuditLog();
            auditLog.logEvent(this.currentUser.getUserID(), "User logged out", AuditCategory.SYSTEM);
        }
        this.currentUser = null;
    }

    public SystemUser getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    // =====================================================================
    // Role-Checking Helpers
    // =====================================================================

    /**
     * Checks if the currently logged-in user holds the ADMIN role.
     * Used as a security gate before any admin-privileged operation.
     *
     * @return true if a user is logged in AND their role is ADMIN, false otherwise.
     */
    public boolean isAdminLoggedIn() {
        return currentUser != null && currentUser.getRole() == UserRole.ADMIN;
    }

    /**
     * Checks if the currently logged-in user holds the INVESTIGATOR role.
     * Used for role-based UI routing after authentication.
     *
     * @return true if a user is logged in AND their role is INVESTIGATOR, false otherwise.
     */
    public boolean isInvestigatorLoggedIn() {
        return currentUser != null && currentUser.getRole() == UserRole.INVESTIGATOR;
    }

    // =====================================================================
    // Shared Hashing Utility
    // =====================================================================

    /**
     * Converts plain text into an SHA-256 hex string.
     * Centralised here so both LoginController and any future
     * password-handling code use a single implementation.
     *
     * @param input The plain text to hash.
     * @return The lowercase hex SHA-256 digest, or null on failure.
     */
    public static String hashSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("CRITICAL: SHA-256 algorithm not found.");
            return null;
        }
    }
}

