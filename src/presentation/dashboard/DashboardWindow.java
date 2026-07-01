package presentation.dashboard;

import domain.model.Student;
import infrastructure.theme.AppColors;
import infrastructure.theme.AppCursors;
import infrastructure.theme.AppFonts;
import presentation.components.TextField;
import presentation.dashboard.contract.DashboardPresenterContract;
import presentation.dashboard.contract.DashboardViewContract;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class DashboardWindow extends JFrame implements DashboardViewContract {
    private DashboardPresenterContract presenter;

    private JPanel panel;
    private JTable table;
    private DefaultTableModel tableModel;

    private TextField lastNameField;
    private TextField nameField;
    private TextField ciField;
    private TextField gradeField;

    private JButton createButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton clearButton;

    private JLabel errorLabel;
    private JLabel successLabel;

    public DashboardWindow(DashboardPresenterContract presenter) {
        super("Gestión de Estudiantes");
        this.presenter = presenter;
        build();
        if (presenter instanceof HandlerDashboardWindow) {
            ((HandlerDashboardWindow) presenter).attach(this);
        }
        setVisible(true);
    }

    private void build() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setLayout(null);

        buildContainers();
        buildTable();
        buildForm();
        buildButtons();
        buildLabels();

    }

    private void buildContainers() {
        panel = new JPanel(null);
        panel.setSize(1000, 730);
        panel.setLocation(100, 70);
        panel.setBackground(AppColors.background);
        add(panel);
    }

    private void buildTable() {
        tableModel = new DefaultTableModel(new String[]{"ID", "Apellidos", "Nombres", "CI", "Nota"}, 0) {
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
        table.getTableHeader().setReorderingAllowed(false);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    int id = (int) tableModel.getValueAt(row, 0);
                    String lastname = (String) tableModel.getValueAt(row, 1);
                    String name = (String) tableModel.getValueAt(row, 2);
                    String ci = (String) tableModel.getValueAt(row, 3);
                    String grade = (String) tableModel.getValueAt(row, 4);
                    Student selected = new Student(id, lastname, name, ci, grade);
                    presenter.onStudentSelected(selected);
                }
            }
        });

        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setBounds(0, 0, 680, 600);
        panel.add(scrollpane);
    }

    private void buildForm() {
        lastNameField = new TextField("Apellido");
        lastNameField.setBounds(700, 50, 250, 35);
        panel.add(lastNameField);

        nameField = new TextField("Nombre");
        nameField.setBounds(700, 100, 250, 35);
        panel.add(nameField);

        ciField = new TextField("CI");
        ciField.setBounds(700, 150, 250, 35);
        panel.add(ciField);

        gradeField = new TextField("Nota");
        gradeField.setBounds(700, 200, 250, 35);
        panel.add(gradeField);
    }

    private void buildButtons() {
        createButton = new JButton("Crear");
        createButton.setBounds(700, 260, 120, 40);
        createButton.setBackground(AppColors.primary);
        createButton.setForeground(Color.WHITE);
        createButton.setCursor(AppCursors.HAND);
        createButton.addActionListener(e -> onCreate());

        updateButton = new JButton("Actualizar");
        updateButton.setBounds(830, 260, 120, 40);
        updateButton.setBackground(AppColors.primary);
        updateButton.setForeground(Color.WHITE);
        updateButton.setCursor(AppCursors.HAND);
        updateButton.addActionListener(e -> onUpdate());

        deleteButton = new JButton("Eliminar");
        deleteButton.setBounds(700, 310, 120, 40);
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setCursor(AppCursors.HAND);
        deleteButton.addActionListener(e -> onDelete());

        clearButton = new JButton("Limpiar");
        clearButton.setBounds(830, 310, 120, 40);
        clearButton.setBackground(Color.LIGHT_GRAY);
        clearButton.setCursor(AppCursors.HAND);
        clearButton.addActionListener(e -> presenter.clearForm());

        panel.add(createButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(clearButton);
    }

    private void buildLabels() {
        errorLabel = new JLabel();
        errorLabel.setBounds(700, 370, 250, 30);
        errorLabel.setForeground(Color.RED);
        panel.add(errorLabel);

        successLabel = new JLabel();
        successLabel.setBounds(700, 400, 250, 30);
        successLabel.setForeground(Color.GREEN);
        panel.add(successLabel);
    }

    // ---------- Acciones ----------
    private void onCreate() {
        try {
            Student student = extractStudentFromForm(false);
            presenter.createStudent(student);
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }

    private void onUpdate() {
        try {
            Student student = extractStudentFromForm(true);
            presenter.updateStudent(student);
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }

    private void onDelete() {
        int row = table.getSelectedRow();
        if (row < 0) {
            showError("Seleccione una fila para eliminar");
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Seguro que desea eliminar el estudiante?", "Confirmar",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            presenter.deleteStudent(id);
        }
    }

    private Student extractStudentFromForm(boolean withId) {
        String lastname = lastNameField.getText();
        String name = nameField.getText();
        String ci = ciField.getText();
        String grade = gradeField.getText();
        if (lastname.equals("Apellido") || lastname.isBlank()) {
            throw new IllegalArgumentException("Apellido es obligatorio");
        }
        if (name.equals("Nombre") || name.isBlank()) {
            throw new IllegalArgumentException("Nombre es obligatorio");
        }
        if (ci.equals("CI") || ci.isBlank()) {
            throw new IllegalArgumentException("CI es obligatorio");
        }
        if (grade.equals("Nota") || grade.isBlank()) {
            throw new IllegalArgumentException("Nota es obligatoria");
        }
        if (withId) {
            int row = table.getSelectedRow();
            if (row < 0) {
                throw new IllegalArgumentException("Seleccione un estudiante para actualizar");
            }
            int id = (int) tableModel.getValueAt(row, 0);
            return new Student(id, lastname, name, ci, grade);
        } else {
            return new Student(lastname, name, ci, grade);
        }
    }

    @Override
    public void showStudents(List<Student> students) {
        tableModel.setRowCount(0);
        for (Student s : students) {
            tableModel.addRow(new Object[]{
                    s.getId(), s.getLastname(), s.getName(), s.getCi(), s.getGrade()
            });
        }
        table.clearSelection();
    }

    @Override
    public void showError(String message) {
        errorLabel.setText(message);
        successLabel.setText("");
    }

    @Override
    public void clearError() {
        errorLabel.setText("");
    }

    @Override
    public void showSuccess(String message) {
        successLabel.setText(message);
        errorLabel.setText("");
    }

    @Override
    public void setLoading(boolean loading) {
        if (loading) {
            setCursor(AppCursors.WAIT);
        } else {
            setCursor(Cursor.getDefaultCursor());
        }
    }

    @Override
    public void setPermissions(boolean canCreate, boolean canEdit, boolean canDelete) {
        if (createButton != null) createButton.setEnabled(canCreate);
        if (updateButton != null) updateButton.setEnabled(canEdit);
        if (deleteButton != null) deleteButton.setEnabled(canDelete);
    }

    @Override
    public void setFormData(Student student) {
        lastNameField.setText(student.getLastname());
        nameField.setText(student.getName());
        ciField.setText(student.getCi());
        gradeField.setText(student.getGrade());
    }

    @Override
    public void clearForm() {
        lastNameField.setText("Apellido");
        nameField.setText("Nombre");
        ciField.setText("CI");
        gradeField.setText("Nota");
        table.clearSelection();
        errorLabel.setText("");
        successLabel.setText("");
    }
}