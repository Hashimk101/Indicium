package com.indicium.models;
import com.indicium.models.SystemUser;
import com.indicium.models.UserRole;
import com.indicium.ui.ID_UI;
import com.indicium.ui.AdminUI;

public class Admin extends SystemUser {
    String adminToken;

    public Admin(int userID, String name, String email, String credentials, String adminToken) {
        this.adminToken = adminToken;
        super(userID, name, email, credentials, UserRole.ADMIN);
    }

    // UC # 1: Manage User Identity
    public void manageUserIdentity() {
        ID_UI.selectIdentityManagement();
        ID_UI.chooseCreateOrModify();
        ID_UI.enterUserDetails(name, email, role);
    }

    // UC # 12: Manage System Integrity
    public void manageSystemIntegrity()
    {
        String option = AdminUI.openMaintenanceDashboard();
        if (option.equals("Archive"))
        {
            AdminUI.selectOption(option);
        }
    }
}
