package presentation.dashboard.security;

public class PermissionFactory {
    public static PermissionStrategy getStrategy(String role) {
        if (role != null && role.equalsIgnoreCase("ADMIN")) {
            return new AdminPermissionStrategy();
        }
        return new GuestPermissionStrategy(); // Por defecto o cualquier otro rol aplica restricciones
    }
}