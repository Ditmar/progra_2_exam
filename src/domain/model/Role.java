package domain.model;

public enum Role {
    CLIENT("Cliente") {
        @Override
        public boolean canEdit() {
            return false;
        }
    },
    CASHIER("Cajero") {
        @Override
        public boolean canEdit() {
            return false;
        }
    },
    ADMIN("Administrador") {
        @Override
        public boolean canEdit() {
            return true;
        }
    };

    private final String label;

    Role(String label) {
        this.label = label;
    }

    public abstract boolean canEdit();

    @Override
    public String toString() {
        return label;
    }
}