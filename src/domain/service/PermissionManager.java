package domain.service;

import domain.model.Role;

public class PermissionManager {
    private final boolean canCreate;
    private final boolean canEdit;
    private final boolean canDelete;

    public PermissionManager(Role role) {
        switch (role) {
            case ADMIN:
                canCreate = true;
                canEdit = true;
                canDelete = true;
                break;
            case CASHIER:
                canCreate = true;
                canEdit = true;
                canDelete = false;
                break;
            case CLIENT:
            default:
                canCreate = false;
                canEdit = false;
                canDelete = false;
                break;
        }
    }

    public boolean canCreate() { return canCreate; }
    public boolean canEdit() { return canEdit; }
    public boolean canDelete() { return canDelete; }
}