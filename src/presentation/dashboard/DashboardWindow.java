package presentation.dashboard;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import domain.model.Student;
import infrastructure.theme.AppColors;
import infrastructure.theme.AppFonts;
import presentation.dashboard.contract.DashboardViewContract;

public class DashboardWindow extends JFrame implements DashboardViewContract.View {
    private JPanel panel;
    private JTable table;
    private DefaultTableModel tableModel;
    private final DashboardViewContract.Presenter presenter;

    // Inyección obligatoria del presentador por constructor
    public DashboardWindow(DashboardViewContract.Presenter presenter) {
        super("Gestión de Estudiantes");
        this.presenter = presenter;
        this.presenter.attachView(this);
        build();
    }

    private void build() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1200, 800);
        this.setLocationRelativeTo(this);
        this.setLayout(null);
        
        buildContainers();
        buildTable();
        
        // Carga inicial controlada por el presentador delegando el flujo de datos
        this.presenter.requestStudentsLoad();
        setVisible(true);
    }

    private void buildContainers() {
        panel = new JPanel(null);
        panel.setSize(1000, 730);
        panel.setLocation(100, 70);
        panel.setBackground(AppColors.background);
        this.add(panel);
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
        table.setSize(new Dimension(1000, 500));

        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setBounds(0, 0, 1000, 600);
        panel.add(scrollpane);
    }

    // --- Implementación de los contratos de la Vista MVP ---

    @Override
    public void showStudentsList(List<Student> students) {
        tableModel.setRowCount(0);
        for (Student s : students) {
            tableModel.addRow(new Object[] {
                s.getId(), // Agregado en el modelado
                s.getLastname(),
                s.getName(),
                s.getCi(),
                s.getGrade()
            });
        }
        table.clearSelection();
    }

    @Override
    public void displayErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error de Operación", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void displaySuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void clearInputFields() {
        // Si tuvieras TextFields añadidos (como txtName), aquí harías un .setText("");
    }

    @Override
    public void restrictEditingPrivileges() {
        // Criterio de Aceptación 5: Si no es admin, deshabilitar o esconder componentes de edición
        // Ejemplo: btnGuardar.setEnabled(false);
    }
}