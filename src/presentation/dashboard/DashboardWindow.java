package presentation.dashboard;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import domain.model.Student;
import domain.model.User;
import infrastructure.theme.AppColors;
import infrastructure.theme.AppCursors;
import infrastructure.theme.AppFonts;
import presentation.components.TextField;
import presentation.dashboard.contract.DashboardPresenterContract;
import presentation.dashboard.contract.DashboardViewContract;

public class DashboardWindow extends JFrame implements DashboardViewContract {
    
    private JPanel panel;
    private JTable table;
    private DefaultTableModel tableModel;
    private final DashboardPresenterContract presenter;

    private TextField txtLastname;
    private TextField txtName;
    private TextField txtCi;
    private TextField txtGrade;
    private JButton btnSave;
    private JButton btnDelete;

    private JLabel lblRoleIndicator;

    public DashboardWindow(DashboardPresenterContract presenter) {
        super("Gestión de Estudiantes");
        this.presenter = presenter;
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
    }

    private void buildContainers() {
        panel = new JPanel(null);
        panel.setSize(1100, 730);
        panel.setLocation(50, 20);
        panel.setBackground(AppColors.background);
        this.add(panel);

        lblRoleIndicator = new JLabel("");
        lblRoleIndicator.setBounds(10, 10, 300, 30);
        lblRoleIndicator.setFont(AppFonts.TITLE_H2);
        panel.add(lblRoleIndicator);
    }

    private void buildForm() {
        txtLastname = new TextField("Apellidos");
        txtLastname.setLocation(10, 50);
        panel.add(txtLastname);

        txtName = new TextField("Nombres");
        txtName.setLocation(280, 50);
        panel.add(txtName);

        txtCi = new TextField("CI");
        txtCi.setLocation(550, 50);
        panel.add(txtCi);

        txtGrade = new TextField("Nota");
        txtGrade.setLocation(820, 50);
        txtGrade.setSize(100, 40); // Más pequeño para la nota
        panel.add(txtGrade);

        btnSave = new JButton("Guardar / Actualizar");
        btnSave.setBounds(10, 100, 260, 40);
        btnSave.setBackground(AppColors.primary);
        btnSave.setForeground(Color.WHITE);
        btnSave.setCursor(AppCursors.HAND);
        btnSave.addActionListener(e -> presenter.onSaveStudentClicked(
                txtLastname.getValue(), txtName.getValue(), txtCi.getValue(), txtGrade.getValue()
        ));
        panel.add(btnSave);

        btnDelete = new JButton("Eliminar Seleccionado");
        btnDelete.setBounds(280, 100, 260, 40);
        btnDelete.setBackground(Color.RED);
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setCursor(AppCursors.HAND);
        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String ci = tableModel.getValueAt(selectedRow, 2).toString();
                presenter.onDeleteStudentClicked(ci);
            } else {
                showError("Seleccione un estudiante de la tabla para eliminar.");
            }
        });
        panel.add(btnDelete);
    }

    private void buildTable() {
        tableModel = new DefaultTableModel(new String[] { "Apellidos", "Nombres", "CI", "Nota" }, 0) {
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
        
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                txtLastname.setText(tableModel.getValueAt(row, 0).toString());
                txtName.setText(tableModel.getValueAt(row, 1).toString());
                txtCi.setText(tableModel.getValueAt(row, 2).toString());
                txtGrade.setText(tableModel.getValueAt(row, 3).toString());
            }
        });

        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setBounds(10, 160, 1080, 550);
        panel.add(scrollpane);
    }

    @Override
    public void showStudents(List<Student> students) {
        tableModel.setRowCount(0);
        for (Student s : students) {
            tableModel.addRow(new Object[] {
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
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void clearError() {
    }

    @Override
    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void clearForm() {
        txtLastname.setText("Apellidos");
        txtName.setText("Nombres");
        txtCi.setText("CI");
        txtGrade.setText("Nota");
        table.clearSelection();
    }

    @Override
    public void applyRolePermissions(User currentUser) {
        lblRoleIndicator.setText("Sesión iniciada como: " + currentUser.getRole().toString());
        
        boolean canEdit = currentUser.getRole().canEdit(); 
        
        txtLastname.setVisible(canEdit);
        txtName.setVisible(canEdit);
        txtCi.setVisible(canEdit);
        txtGrade.setVisible(canEdit);
        btnSave.setVisible(canEdit);
        btnDelete.setVisible(canEdit);
        
        this.setVisible(true);
    }
}