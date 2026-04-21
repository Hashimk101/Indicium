package com.indicium.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DashBoardController implements Initializable {

    // ── Top nav ──
    @FXML private Button navDashboard;
    @FXML private Button navNotes;
    @FXML private Button navVideos;
    @FXML private Button navTools;
    @FXML private Button navForum;

    // ── Sidebar ──
    @FXML private Button sideHome;
    @FXML private Button sideCases;
    @FXML private Button sideEvidence;
    @FXML private Button sideTimeline;
    @FXML private Button sideAuditLog;
    @FXML private Button sideReport;
    @FXML private Button sideSettings;
    @FXML private Button sideIntegrity;
    @FXML private Button sideUserMgr;

    // ── Content ──
    @FXML private Label welcomeLabel;
    @FXML private Label statCases;
    @FXML private Label statEvidence;
    @FXML private Label statTimeline;
    @FXML private Label statAudit;
    @FXML private VBox  recentActivityBox;

    // Track active buttons
    private Button activeTopBtn;
    private Button activeSideBtn;

    private List<Button> topNavButtons;
    private List<Button> sideNavButtons;

    // ─────────────────────────────────────────
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        topNavButtons  = List.of(navDashboard, navNotes, navVideos, navTools, navForum);
        sideNavButtons = List.of(sideHome, sideCases, sideEvidence, sideTimeline,
                sideAuditLog, sideReport, sideSettings,
                sideIntegrity, sideUserMgr);

        // Set defaults on startup
        setActiveTopNav(navDashboard);
        setActiveSideNav(sideHome);

        // Load placeholder stats (replace with real data calls later)
        loadStats();
    }

    // ─────────────────────────────────────────
    // Top Nav Handlers
    // ─────────────────────────────────────────

    @FXML private void handleNavDashboard() {
        setActiveTopNav(navDashboard);
        setActiveSideNav(sideHome);
        // TODO: load dashboard content pane
    }

    @FXML private void handleNavNotes() {
        setActiveTopNav(navNotes);
        // TODO: load notes pane
    }

    @FXML private void handleNavVideos() {
        setActiveTopNav(navVideos);
        // TODO: load videos pane
    }

    @FXML private void handleNavTools() {
        setActiveTopNav(navTools);
        // TODO: load tools pane
    }

    @FXML private void handleNavForum() {
        setActiveTopNav(navForum);
        // TODO: load forum pane
    }

    // ─────────────────────────────────────────
    // Sidebar Handlers
    // ─────────────────────────────────────────

    @FXML private void handleSideCases()       { setActiveSideNav(sideCases);    /* TODO */ }
    @FXML private void handleSideEvidence()    { setActiveSideNav(sideEvidence); /* TODO */ }
    @FXML private void handleSideTimeline()    { setActiveSideNav(sideTimeline); /* TODO */ }
    @FXML private void handleSideAuditLog()    { setActiveSideNav(sideAuditLog); /* TODO */ }
    @FXML private void handleSideReport()      { setActiveSideNav(sideReport);   /* TODO */ }
    @FXML private void handleSideSettings()    { setActiveSideNav(sideSettings); /* TODO */ }
    @FXML private void handleSideIntegrity()   { setActiveSideNav(sideIntegrity);/* TODO */ }
    @FXML private void handleSideUserManager() { setActiveSideNav(sideUserMgr);  /* TODO */ }

    // ─────────────────────────────────────────
    // Active State Helpers
    // ─────────────────────────────────────────

    private void setActiveTopNav(Button target) {
        topNavButtons.forEach(b -> b.getStyleClass().remove("active"));
        target.getStyleClass().add("active");
        activeTopBtn = target;
    }

    private void setActiveSideNav(Button target) {
        sideNavButtons.forEach(b -> b.getStyleClass().remove("side-active"));
        target.getStyleClass().add("side-active");
        activeSideBtn = target;
    }

    // ─────────────────────────────────────────
    // Data Loading
    // ─────────────────────────────────────────

    private void loadStats() {
        // Replace these with real DB / service calls
        statCases.setText("12");
        statEvidence.setText("47");
        statTimeline.setText("8");
        statAudit.setText("134");
    }

    /**
     * Call this from your main app to set the logged-in username.
     */
    public void setUsername(String name) {
        welcomeLabel.setText("Welcome back, " + name + "!");
    }
}
