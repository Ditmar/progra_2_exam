package domain.service;

public interface PermissionStrategy {
    boolean canCreate();
    boolean canEdit();
    boolean canDelete();
}