package com.indicium.services;
import com.indicium.repository.LogsRepo;
import com.indicium.services.AuditCategory;

public class AuditLog {
    LogsRepo logsRepo;
    public AuditLog() {
        this.logsRepo = new LogsRepo();
    }

    // timestamp not needed as DB already adds that, I hope so
    public String logEvent(int userID, String action, AuditCategory auditCategory) {
        logsRepo.saveLog(auditCategory, action,  userID);
        return "Event logged: " + auditCategory.name() + " - " + action;
    }
    public String logEvent(int userID, String action, AuditCategory auditCategory, int caseID) {
        logsRepo.saveLog(auditCategory, action,  userID, caseID);
        return "Event logged: " + auditCategory.name() + " - " + action;
    }
    public String logEvent(int userID, String action, AuditCategory auditCategory, int caseID, int evidenceID) {
        logsRepo.saveLog(auditCategory, action,  userID, caseID, evidenceID);
        return "Event logged: " + auditCategory.name() + " - " + action;
    }
    // =====================================================================
    // ASYNCHRONOUS LOGGING METHODS (Non-blocking for JavaFX UI)
    // =====================================================================

    /**
     * Logs an event asynchronously on a background thread.
     * Use this in JavaFX Controllers to prevent UI freezing during network latency.
     */
    public void logEventAsync(int userID, String action, AuditCategory auditCategory) {
        AsyncDatabaseExecutor.runAsync(() -> {
            logsRepo.saveLog(auditCategory, action, userID);
            System.out.println("[AuditLog-Async] Event logged: " + auditCategory.name() + " - " + action);
        });
    }

    public void logEventAsync(int userID, String action, AuditCategory auditCategory, int caseID) {
        AsyncDatabaseExecutor.runAsync(() -> {
            logsRepo.saveLog(auditCategory, action, userID, caseID);
            System.out.println("[AuditLog-Async] Event logged: " + auditCategory.name() + " - " + action);
        });
    }

    public void logEventAsync(int userID, String action, AuditCategory auditCategory, int caseID, int evidenceID) {
        AsyncDatabaseExecutor.runAsync(() -> {
            logsRepo.saveLog(auditCategory, action, userID, caseID, evidenceID);
            System.out.println("[AuditLog-Async] Event logged: " + auditCategory.name() + " - " + action);
        });
    }

    //STUB
    public String record(String nme, String a, int n)
    {
        String b ="";
        return b ;
    }
     public void logEvidenceEvent(){}

}
