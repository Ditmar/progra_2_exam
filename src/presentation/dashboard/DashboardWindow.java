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

    private JDesktopPane desktop;
    private JInternalFrame listFrame;
    private JInternalFrame formFrame;

    private JTable table;
    private DefaultTableModel tableModel;

    private TextField lastNameField;
    private TextField nameField;
    private TextField ciField;
    private TextField gradeField;

    private JButton saveButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton cancelButton;

    public DashboardWindow(DashboardPresenterContract presenter) {
        super("Gestión de Estudiantes - MDI");
        this.presenter = presenter;
        build();
        if (presenter instanceof HandlerDashboardWindow) {
            ((HandlerDashboardWindow) presenter).attach(this);
        }
        setVisible(true);
    }

    private void build() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 750);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        desktop = new JDesktopPane();
        desktop.setBackground(AppColors.background);
        add(desktop, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Acciones");
        JMenuItem newItem = new JMenuItem("Nuevo Estudiante");
        newItem.addActionListener(e -> presenter.showNewForm());
        menu.add(newItem);
        JMenuItem refreshItem = new JMenuItem("Refrescar");
        refreshItem.addActionListener(e -> presenter.loadStudents());
        menu.add(refreshItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        createListFrame();
        createFormFrame();

        listFrame.setVisible(true);
        try {
            listFrame.setMaximum(true);
        } catch (Exception e) { }
    }

    private void createListFrame() {
        listFrame = new JInternalFrame("Lista de Estudiantes", true, true, true, true);
        listFrame.setLayout(new BorderLayout());
        listFrame.setSize(800, 600);
        listFrame.setLocation(20, 20);

        
        tableModel = new DefaultTableModel(new String[]{"ID", "Apellidos", "Nombres", "CI", "Nota"}, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(AppFonts.TITLE_H1);
        table.getTableHeader().setReorderingAllowed(false);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { 
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
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        listFrame.add(scroll, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton newBtn = new JButton("Nuevo");
        newBtn.addActionListener(e -> presenter.showNewForm());
        JButton refreshBtn = new JButton("Refrescar");
        refreshBtn.addActionListener(e -> presenter.loadStudents());
        buttonPanel.add(newBtn);
        buttonPanel.add(refreshBtn);
        listFrame.add(buttonPanel, BorderLayout.SOUTH);

        desktop.add(listFrame);
    }

    private void createFormFrame() {
        formFrame = new JInternalFrame("Formulario de Estudiante", true, true, true, true);
        formFrame.setLayout(new GridBagLayout());
        formFrame.setSize(400, 400);
        formFrame.setLocation(200, 100);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;
        gbc.gridx = 0; gbc.gridy = y;
        formFrame.add(new JLabel("Apellido:"), gbc);
        gbc.gridx = 1;
        lastNameField = new TextField("Apellido");
        lastNameField.setPreferredSize(new Dimension(200, 30));
        formFrame.add(lastNameField, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        formFrame.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        nameField = new TextField("Nombre");
        formFrame.add(nameField, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        formFrame.add(new JLabel("CI:"), gbc);
        gbc.gridx = 1;
        ciField = new TextField("CI");
        formFrame.add(ciField, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        formFrame.add(new JLabel("Nota:"), gbc);
        gbc.gridx = 1;
        gradeField = new TextField("Nota");
        formFrame.add(gradeField, gbc);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        saveButton = new JButton("Guardar");
        saveButton.setBackground(AppColors.primary);
        saveButton.setForeground(Color.WHITE);
        saveButton.setCursor(AppCursors.HAND);
        saveButton.addActionListener(e -> onCreate());

        updateButton = new JButton("Actualizar");
        updateButton.setBackground(AppColors.primary);
        updateButton.setForeground(Color.WHITE);
        updateButton.setCursor(AppCursors.HAND);
        updateButton.addActionListener(e -> onUpdate());

        deleteButton = new JButton("Eliminar");
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setCursor(AppCursors.HAND);
        deleteButton.addActionListener(e -> onDelete());

        cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> presenter.clearForm());

        buttonPanel.add(saveButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);
        y++;
        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2;
        formFrame.add(buttonPanel, gbc);

        desktop.add(formFrame);
        formFrame.setVisible(false); 
    }

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
            showError("Seleccione un estudiante en la lista para eliminar");
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Eliminar estudiante con ID " + id + "?", "Confirmar",
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
        if (lastname.equals("Apellido") || lastname.isBlank())
            throw new IllegalArgumentException("Apellido obligatorio");
        if (name.equals("Nombre") || name.isBlank())
            throw new IllegalArgumentException("Nombre obligatorio");
        if (ci.equals("CI") || ci.isBlank())
            throw new IllegalArgumentException("CI obligatorio");
        if (grade.equals("Nota") || grade.isBlank())
            throw new IllegalArgumentException("Nota obligatoria");
        if (withId) {
            int row = table.getSelectedRow();
            if (row < 0) throw new IllegalArgumentException("Seleccione un estudiante");
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
            tableModel.addRow(new Object[]{s.getId(), s.getLastname(), s.getName(), s.getCi(), s.getGrade()});
        }
        table.clearSelection();
    }

    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void clearError() { }

    @Override
    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void setLoading(boolean loading) {
        setCursor(loading ? AppCursors.WAIT : Cursor.getDefaultCursor());
    }

    @Override
    public void setPermissions(boolean canCreate, boolean canEdit, boolean canDelete) {
        saveButton.setEnabled(canCreate);
        updateButton.setEnabled(canEdit);
        deleteButton.setEnabled(canDelete);
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
    }

    @Override
    public void showForm(boolean show) {
        formFrame.setVisible(show);
        if (show) {
            try {
                formFrame.setSelected(true);
            } catch (Exception e) { }
        }
    }
}