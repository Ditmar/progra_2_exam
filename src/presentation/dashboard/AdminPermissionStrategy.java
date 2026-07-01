package presentation.dashboard.security;

import javax.swing.JButton;
import javax.swing.JTextField;

public class AdminPermissionStrategy implements PermissionStrategy {
    @Override
    public void configureUI(JButton btnSave, JButton btnDelete, JTextField txtName, JTextField txtCi, JTextField txtGrade) {
        // El administrador tiene acceso completo de lectura y escritura
        btnSave.setEnabled(true);
        btnDelete.setEnabled(true);
        txtName.setEditable(true);
        txtCi.setEditable(true);
        txtGrade.setEditable(true);
    }
}