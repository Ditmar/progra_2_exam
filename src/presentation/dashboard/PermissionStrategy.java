package presentation.dashboard.security;

import javax.swing.JButton;
import javax.swing.JTextField;

public interface PermissionStrategy {
    void configureUI(JButton btnSave, JButton btnDelete, JTextField txtName, JTextField txtCi, JTextField txtGrade);
}