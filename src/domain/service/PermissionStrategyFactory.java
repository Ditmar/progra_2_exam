package domain.service;

import domain.model.Role;

public final class PermissionStrategyFactory {
    private PermissionStrategyFactory() {}

    public static PermissionStrategy getStrategy(Role role) {
        switch (role) {
            case ADMIN: return new AdminPermissionStrategy();
            case CASHIER: return new CashierPermissionStrategy();
            case CLIENT:
            default: return new ClientPermissionStrategy();
        }
    }
}