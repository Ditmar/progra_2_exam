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
import javax.swing.table.DefaultTableModel;

import domain.model.Student;
import domain.model.User;
import domain.model.Role;
import infrastructure.theme.AppColors;
import infrastructure.theme.AppFonts;
import presentation.components.TextField;

// TODO:
// This code needs to be refactor 
// using the pattern MVP

public class DashboardWindow extends JFrame implements DashboardViewContract {
    private JPanel panel;
    private JTable table;
    private DefaultTableModel tableModel;
    private final HandlerDashboardWindow presenter;
    private final User loggedUser;
    private int selectedStudentId = -1;

    private TextField txtLastname;
    private TextField textName;
    private TextField txtCi;
    private TextField txtGrade;
    private JButton btnSave;
    private JButton btnUpdate;
    private JButton btnDelete;

    public DashboardWindow(HandlerDashboardWindow presenter, User user) {
        super("Gestión de Estudiantes - Panel");
        this.presenter = presenter;
        this.loggedUser = user;
        this.presenter.attach(this);
        build();
        applyRolePermissions();
        this.presenter.onCargarEstudiantesClicked();
    }

    private void build() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1200, 800);
        this.setLocationRelativeTo(this);
        this.setLayout(null);
        
        buildContainers();
        buildForm();
        buildTable();
        
        setVisible(true);
    }

    private void buildContainers() {
        panel = new JPanel(null);
        panel.setSize(1000, 730);
        panel.setLocation(100, 30); // Subimos un poco para dar espacio al formulario
        panel.setBackground(AppColors.background);
        this.add(panel);
    }

    private void buildForm() {
        JLabel lblLastname = new JLabel("Apellidos:");
        lblLastname.setBounds(0, 480, 100, 30);
        lblLastname.setFont(AppFonts.TITLE_H1);
        panel.add(lblLastname);

        txtLastname = new TextField("");
        txtLastname.setBounds(90, 480, 150, 30);
        panel.add(txtLastname);

        JLabel lblName = new JLabel("Nombres:");
        lblName.setBounds(260, 480, 100, 30);
        lblName.setFont(AppFonts.TITLE_H1);
        panel.add(lblName);

        textName = new TextField("");
        textName.setBounds(340, 480, 150, 30);
        panel.add(textName);

        JLabel lblCi = new JLabel("CI:");
        lblCi.setBounds(510, 480, 50, 30);
        lblCi.setFont(AppFonts.TITLE_H1);
        panel.add(lblCi);

        txtCi = new TextField("");
        txtCi.setBounds(550, 480, 120, 30);
        panel.add(txtCi);

        JLabel lblGrade = new JLabel("Nota:");
        lblGrade.setBounds(690, 480, 50, 30);
        lblGrade.setFont(AppFonts.TITLE_H1);
        panel.add(lblGrade);

        txtGrade = new TextField("");
        txtGrade.setBounds(740, 480, 80, 30);
        panel.add(txtGrade);

        btnSave = new JButton("Guardar");
        btnSave.setBounds(150, 540, 150, 40);
        btnSave.setBackground(AppColors.primary);
        btnSave.setFont(AppFonts.TITLE_H1);
        btnSave.addActionListener(e -> presenter.onGuardarClicked(
            txtLastname.getText(), textName.getText(), txtCi.getText(), txtGrade.getText()
        ));
        panel.add(btnSave);

        btnUpdate = new JButton("Actualizar");
        btnUpdate.setBounds(350, 540, 150, 40);
        btnUpdate.setBackground(AppColors.primary);
        btnUpdate.setFont(AppFonts.TITLE_H1);
        btnUpdate.addActionListener(e -> {
            if (selectedStudentId != -1) {
                presenter.onActualizarClicked(
                    selectedStudentId, txtLastname.getText(), textName.getText(), txtCi.getText(), txtGrade.getText()
                );
            } else {
                mostrarError("Por favor, seleccione un estudiante de la tabla para actualizar.");
            }
        });
        panel.add(btnUpdate);

        btnDelete = new JButton("Eliminar");
        btnDelete.setBounds(550, 540, 150, 40);
        btnDelete.setBackground(AppColors.primary);
        btnDelete.setFont(AppFonts.TITLE_H1);
        btnDelete.addActionListener(e -> {
            if (selectedStudentId != -1) {
                int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este estudiante?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    presenter.onEliminarClicked(selectedStudentId);
                }
            } else {
                mostrarError("Por favor, seleccione un estudiante de la tabla para eliminar.");
            }
        });
        panel.add(btnDelete);
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
        table.setSize(new Dimension(1000, 400));

        
        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                selectedStudentId = (int) tableModel.getValueAt(selectedRow, 0);
                txtLastname.setText((String) tableModel.getValueAt(selectedRow, 1));
                textName.setText((String) tableModel.getValueAt(selectedRow, 2));
                txtCi.setText((String) tableModel.getValueAt(selectedRow, 3));
                txtGrade.setText(String.valueOf(tableModel.getValueAt(selectedRow, 4)));
            }
        });

        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setBounds(0, 0, 1000, 450);
        panel.add(scrollpane);
    }

    private void applyRolePermissions() {
        if (loggedUser != null && loggedUser.getRole() == Role.CLIENT) {
            txtLastname.setEnabled(false);
            textName.setEnabled(false);
            txtCi.setEnabled(false);
            txtGrade.setEnabled(false);
            btnSave.setEnabled(false);
            btnUpdate.setEnabled(false);
            btnDelete.setEnabled(false);
        }
    }

    @Override
    public void mostrarEstudiantes(ArrayList<Student> estudiantes) {
        tableModel.setRowCount(0);
        for (Student s : estudiantes) {
            tableModel.addRow(new Object[] {
                s.getId(), // Almacenado en la columna invisible/visble para las acciones
                s.getLastname(),
                s.getName(),
                s.getCi(),
                s.getGrade()
            });
        }
        table.clearSelection();
    }

    @Override
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void limpiarFormulario() {
        txtLastname.setText("");
        textName.setText("");
        txtCi.setText("");
        txtGrade.setText("");
        selectedStudentId = -1;
        table.clearSelection();
    }

   
}
