package domain.service;

import domain.model.Role;

public final class AccessControl {
    private AccessControl() {}

    public static boolean canCreate(Role role) {
        return role == Role.ADMIN || role == Role.CASHIER;
    }

    public static boolean canEdit(Role role) {
        return role == Role.ADMIN || role == Role.CASHIER;
    }

    public static boolean canDelete(Role role) {
        return role == Role.ADMIN;
    }
}