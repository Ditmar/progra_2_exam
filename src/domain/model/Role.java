package domain.model;

public enum Role {
    CLIENT("Cliente"),
    CASHIER("Cajero"),
    ADMIN("Administrador");

    private final String label;

    Role(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}