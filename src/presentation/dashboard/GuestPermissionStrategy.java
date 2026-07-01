package presentation.dashboard.security;

import javax.swing.JButton;
import javax.swing.JTextField;

public class GuestPermissionStrategy implements PermissionStrategy {
    @Override
    public void configureUI(JButton btnSave, JButton btnDelete, JTextField txtName, JTextField txtCi, JTextField txtGrade) {
        // El invitado solo puede ver el listado (Criterio de Aceptación 5)
        btnSave.setEnabled(false);
        btnDelete.setEnabled(false);
        txtName.setEditable(false);
        txtCi.setEditable(false);
        txtGrade.setEditable(false);
        
        btnSave.setToolTipText("Acción deshabilitada: Su rol actual es de solo consulta.");
        btnDelete.setToolTipText("Acción deshabilitada: Su rol actual es de solo consulta.");
    }
}