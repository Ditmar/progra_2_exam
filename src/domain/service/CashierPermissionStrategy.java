package domain.service;

public class CashierPermissionStrategy implements PermissionStrategy {
    @Override public boolean canCreate() { return true; }
    @Override public boolean canEdit() { return true; }
    @Override public boolean canDelete() { return false; }
}