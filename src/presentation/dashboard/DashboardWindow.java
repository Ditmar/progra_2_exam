package presentation.dashboard;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import domain.model.Role;
import domain.model.Student;
import infrastructure.theme.AppColors;
import infrastructure.theme.AppFonts;
import presentation.components.TextField;
import presentation.dashboard.contract.DashboardPresenterContract;
import presentation.dashboard.contract.DashboardViewContract;


public class DashboardWindow extends JFrame implements DashboardViewContract {

   
    private final DashboardPresenterContract presenter;

   
    private int selectedStudentId = -1;

   
    private JPanel panel;
    private JTable table;
    private DefaultTableModel tableModel;

    private TextField lastnameField;
    private TextField nameField;
    private TextField ciField;
    private TextField gradeField;

    private JButton saveButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton clearButton;

    private JLabel statusLabel;
    private JLabel formTitle;

    public DashboardWindow(DashboardPresenterContract presenter, Role role) {
        super("Gestión de Estudiantes");
        this.presenter = presenter;
        build();
        applyRolePermissions(role);
    }

   

    private void build() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1200, 800);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        buildHeader();
        buildContainers();
        buildTable();
        buildForm();
        buildStatusBar();
        this.setVisible(true);
    }

    private void buildHeader() {
        JLabel title = new JLabel("Panel de Estudiantes");
        title.setBounds(20, 10, 400, 40);
        title.setForeground(AppColors.primary);
        title.setFont(AppFonts.TITLE_H1);
        this.add(title);
    }

    private void buildContainers() {
       
        panel = new JPanel(null);
        panel.setBounds(10, 60, 750, 650);
        panel.setBackground(AppColors.background);
        this.add(panel);

      
        JPanel formPanel = new JPanel(null);
        formPanel.setBounds(780, 60, 400, 650);
        formPanel.setBackground(AppColors.background);
        this.add(formPanel);

       
        this.formPanel = formPanel;
    }

    private JPanel formPanel; 

    private void buildTable() {
        tableModel = new DefaultTableModel(
                new String[]{"ID", "Apellidos", "Nombres", "CI", "Nota"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(AppFonts.TITLE_H1);
        table.setBackground(Color.WHITE);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setShowGrid(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

      
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

       
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    selectedStudentId = (int) tableModel.getValueAt(row, 0);
                    lastnameField.setText((String) tableModel.getValueAt(row, 1));
                    nameField.setText((String) tableModel.getValueAt(row, 2));
                    ciField.setText((String) tableModel.getValueAt(row, 3));
                    gradeField.setText((String) tableModel.getValueAt(row, 4));
                    formTitle.setText("Editar Estudiante");
                    statusLabel.setText("");
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 0, 750, 600);
        panel.add(scrollPane);
    }

    private void buildForm() {
        formTitle = new JLabel("Nuevo Estudiante");
        formTitle.setBounds(10, 10, 300, 30);
        formTitle.setForeground(AppColors.primary);
        formTitle.setFont(AppFonts.TITLE_H2);
        formPanel.add(formTitle);

        // Campos del formulario
        formPanel.add(makeLabel("Apellido:", 50));
        lastnameField = new TextField("Apellido");
        lastnameField.setBounds(10, 80, 370, 40);
        formPanel.add(lastnameField);

        formPanel.add(makeLabel("Nombre:", 130));
        nameField = new TextField("Nombre");
        nameField.setBounds(10, 160, 370, 40);
        formPanel.add(nameField);

        formPanel.add(makeLabel("CI (solo números):", 210));
        ciField = new TextField("CI");
        ciField.setBounds(10, 240, 370, 40);
        formPanel.add(ciField);

        formPanel.add(makeLabel("Nota (0–100):", 290));
        gradeField = new TextField("Nota");
        gradeField.setBounds(10, 320, 370, 40);
        formPanel.add(gradeField);

   
        saveButton = makeButton("Guardar", AppColors.primary, 380);
        saveButton.addActionListener(e -> {
            presenter.onSaveStudent(
                    lastnameField.getText().trim(),
                    nameField.getText().trim(),
                    ciField.getText().trim(),
                    gradeField.getText().trim());
        });
        formPanel.add(saveButton);

        updateButton = makeButton("Actualizar", new Color(34, 139, 34), 430);
        updateButton.addActionListener(e -> {
            if (selectedStudentId < 0) {
                showError("Selecciona un estudiante de la tabla para actualizar.");
                return;
            }
            presenter.onUpdateStudent(
                    selectedStudentId,
                    lastnameField.getText().trim(),
                    nameField.getText().trim(),
                    ciField.getText().trim(),
                    gradeField.getText().trim());
        });
        formPanel.add(updateButton);

        deleteButton = makeButton("Eliminar", new Color(178, 34, 34), 480);
        deleteButton.addActionListener(e -> {
            if (selectedStudentId < 0) {
                showError("Selecciona un estudiante de la tabla para eliminar.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "¿Estás seguro de que deseas eliminar este estudiante?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                presenter.onDeleteStudent(selectedStudentId);
            }
        });
        formPanel.add(deleteButton);

        clearButton = makeButton("Limpiar", AppColors.three, 540);
        clearButton.setForeground(AppColors.primary);
        clearButton.addActionListener(e -> clearForm());
        formPanel.add(clearButton);
    }

    private JLabel makeLabel(String text, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(10, y, 370, 25);
        label.setForeground(AppColors.primary);
        return label;
    }

    private JButton makeButton(String text, Color bg, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(10, y, 370, 38);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusable(false);
        btn.setFont(AppFonts.TITLE_H2);
        return btn;
    }

    private void buildStatusBar() {
        statusLabel = new JLabel("");
        statusLabel.setBounds(10, 720, 1160, 30);
        statusLabel.setForeground(AppColors.primary);
        this.add(statusLabel);
    }

    
    private void applyRolePermissions(Role role) {
        boolean canEdit = hasEditPermission(role);
        setEditingEnabled(canEdit);
        if (!canEdit) {
            statusLabel.setText("Modo solo lectura — " + role + " no tiene permisos de edición.");
            statusLabel.setForeground(new Color(178, 34, 34));
        }
    }

    
    private boolean hasEditPermission(Role role) {
        return role == Role.ADMIN || role == Role.CASHIER;
    }

   

    @Override
    public void showStudents(ArrayList<Student> students) {
        tableModel.setRowCount(0);
        for (Student s : students) {
            tableModel.addRow(new Object[]{
                    s.getId(),
                    s.getLastname(),
                    s.getName(),
                    s.getCi(),
                    s.getGrade()
            });
        }
        table.clearSelection();
    }

    @Override
    public void showError(String message) {
        statusLabel.setForeground(new Color(178, 34, 34));
        statusLabel.setText("⚠ " + message);
    }

    @Override
    public void showSuccess(String message) {
        statusLabel.setForeground(new Color(34, 139, 34));
        statusLabel.setText("✓ " + message);
    }

    @Override
    public void clearForm() {
        lastnameField.setText("Apellido");
        nameField.setText("Nombre");
        ciField.setText("CI");
        gradeField.setText("Nota");
        selectedStudentId = -1;
        formTitle.setText("Nuevo Estudiante");
        table.clearSelection();
        statusLabel.setText("");
    }

    @Override
    public void setEditingEnabled(boolean enabled) {
        saveButton.setEnabled(enabled);
        updateButton.setEnabled(enabled);
        deleteButton.setEnabled(enabled);
        lastnameField.setEnabled(enabled);
        nameField.setEnabled(enabled);
        ciField.setEnabled(enabled);
        gradeField.setEnabled(enabled);
    }
}