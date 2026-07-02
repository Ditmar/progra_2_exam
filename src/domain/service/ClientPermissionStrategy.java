package domain.service;

public class ClientPermissionStrategy implements PermissionStrategy {
    @Override public boolean canCreate() { return false; }
    @Override public boolean canEdit() { return false; }
    @Override public boolean canDelete() { return false; }
}