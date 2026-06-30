package presentation.dashboard;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import domain.model.Role;
import domain.model.Student;
import domain.model.User;
import infrastructure.theme.AppColors;
import infrastructure.theme.AppCursors;
import infrastructure.theme.AppFonts;
import presentation.dashboard.contract.DashboardPresenterContract;
import presentation.dashboard.contract.DashboardViewContract;

public class DashboardWindow extends JFrame implements DashboardViewContract {
    private final DashboardPresenterContract presenter;
    private final User currentUser;

    private JPanel panel;
    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField fieldLastname, fieldName, fieldCI, fieldGrade;
    private JLabel errorLabel;
    private JButton btnSave, btnUpdate, btnDelete, btnClear;

    private int selectedId = -1;

    public DashboardWindow(DashboardPresenterContract presenter, User currentUser) {
        super("Gestión de Estudiantes" + currentUser.getRole());
        this.presenter = presenter;
        this.currentUser = currentUser;
        build();
    }

    private void build() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1200, 800);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        buildContainers();
        buildForm();
        buildTable();
        buildButtons();
        applyRoleRestricttions();
        this.setVisible(true);
    }

    private void buildContainers() {
        panel = new JPanel(null);
        panel.setSize(1150, 720);
        panel.setLocation(25, 40);
        panel.setBackground(AppColors.background);
        this.add(panel);

    }
    private void buildForm() {
        JLabel lblRole = new JLabel("Rol: "+currentUser.getRole() + " |Usuario: "+ currentUser.getUsername());
        lblRole.setBounds(0, 0, 400, 25);
        lblRole.setFont(AppFonts.TITLE_H1);
        lblRole.setForeground(AppColors.primary);
        panel.add(lblRole);

        int y = 30;
        fieldLastname = buildField("Apellido", 0, y); y+= 45;
        fieldName = buildField("Nombre", 0, y); y += 45;
        fieldCI = buildField("CI", 0, y); y += 45;
        fieldGrade = buildField("Nota (0-100)", 0, y);

        errorLabel = new JLabel();
        errorLabel.setBounds(0, 215, 500, 25);
        errorLabel.setForeground(Color.RED);
        panel.add(errorLabel);
    }

    private JTextField buildField(String hint, int x, int y){
        JLabel lbl = new JLabel(hint + ":");
        lbl.setBounds(x, y, 100, 35);
        panel.add(lbl);
        JTextField tf = new JTextField();
        tf.setBounds(x + 105, y, 250, 35);
        panel.add(tf);
        return tf;
    }

    private void buildTable() {
        tableModel = new DefaultTableModel(new String[] { "ID", "Apellidos", "Nombres", "CI", "Nota" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(35);
        table.setFont(AppFonts.TITLE_H1);
        table.setBackground(AppColors.secondary);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setShowGrid(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int row = table.getSelectedRow();
            if (row < 0 || row >= tableModel.getRowCount()) return;
                selectedId = (int) tableModel.getValueAt(row, 0);
                fieldLastname.setText((String) tableModel.getValueAt(row, 1));
                fieldName.setText((String) tableModel.getValueAt(row, 2));
                fieldCI.setText((String) tableModel.getValueAt(row, 3));
                fieldGrade.setText((String) tableModel.getValueAt(row, 4));

            
        });
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(0, 250, 1150, 460);
        panel.add(scroll);

    }

    private void buildButtons() {
        btnSave = new JButton("Guardar");
        btnSave.setBounds(370, 30, 120, 35);
        btnSave.setBackground(AppColors.primary);
        btnSave.setForeground(AppColors.secondary);
        btnSave.setCursor(AppCursors.HAND);
        btnSave.addActionListener(e -> presenter.onSaveStudent(
            fieldLastname.getText(), fieldName.getText(), fieldCI.getText(), fieldGrade.getText()));
        panel.add(btnSave);

        btnUpdate = new JButton("Actualizar");
        btnUpdate.setBounds(370,80,120,35);
        btnUpdate.setBackground(AppColors.primary);
        btnUpdate.setForeground(AppColors.secondary);
        btnUpdate.setCursor(AppCursors.HAND);
        btnUpdate.addActionListener(e -> presenter.onUpdateStudent(
            selectedId, fieldLastname.getText(), fieldName.getText(), fieldCI.getText(), fieldGrade.getText()));
        panel.add(btnUpdate);

        btnDelete = new JButton("Eliminar");
        btnDelete.setBounds(370, 130, 120, 35);
        btnDelete.setBackground(Color.RED);
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setCursor(AppCursors.HAND);
        btnDelete.addActionListener(e -> presenter.onDeleteStudent(selectedId));
        panel.add(btnDelete);

        btnClear = new JButton("Limpiar");
        btnClear.setBounds(370, 180, 120, 35);
        btnClear.setCursor(AppCursors.HAND);
        btnClear.addActionListener(e -> clearForm());
        panel.add(btnClear);
    }

    private void applyRoleRestricttions() {
        Role role = currentUser.getRole();

        boolean canEdit = role == Role.ADMIN || role == Role.CASHIER;
        boolean canDelete = role == Role.ADMIN;

        btnSave.setEnabled(canEdit);
        btnUpdate.setEnabled(canEdit);
        btnDelete.setEnabled(canDelete);
        setFormEnabled(canEdit);

    }
    @Override
    public void showStudents(ArrayList<Student> students){
        tableModel.setRowCount(0);
        for (Student s : students){
            tableModel.addRow(new Object[]{s.getId(), s.getLastname(), s.getName(), s.getCi(), s.getGrade()});
        }
        table.clearSelection();
    }
    @Override
    public void showError(String message){
        errorLabel.setText(message);
    }
    @Override
    public void clearError(){
        errorLabel.setText("");

    }
    @Override
    public void clearForm(){
        selectedId= -1;
        fieldLastname.setText("");
        fieldName.setText("");
        fieldCI.setText("");
        fieldGrade.setText("");
        clearError();
        table.clearSelection();
        
    }
    @Override
    public void setFormEnabled(boolean enabled){
        fieldLastname.setEnabled(enabled);
        fieldName.setEnabled(enabled);
        fieldCI.setEnabled(enabled);
        fieldGrade.setEnabled(enabled);
    }
}
