package presentation.dashboard;

import domain.model.Student;
import infrastructure.theme.AppColors;
import infrastructure.theme.AppCursors;
import infrastructure.theme.AppFonts;
import presentation.components.TextField;
import presentation.dashboard.contract.DashboardPresenterContract;
import presentation.dashboard.contract.DashboardViewContract;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class DashboardWindow extends JFrame implements DashboardViewContract {
    private DashboardPresenterContract presenter;

    private JSplitPane splitPane;
    private JPanel leftPanel;
    private JPanel rightPanel;

    private JTable table;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTextField searchField;

    private TextField lastNameField;
    private TextField nameField;
    private TextField ciField;
    private TextField gradeField;

    private JButton createButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton clearButton;
    private JButton newButton;

    private JLabel statusLabel;

    private Border defaultBorder;
    private Border errorBorder = BorderFactory.createLineBorder(Color.RED, 2);
    private Border okBorder = BorderFactory.createLineBorder(Color.GREEN, 2);

    public DashboardWindow(DashboardPresenterContract presenter) {
        super("Gestión de Estudiantes - Panel Dividido");
        this.presenter = presenter;
        build();
        if (presenter instanceof HandlerDashboardWindow) {
            ((HandlerDashboardWindow) presenter).attach(this);
        }
        setVisible(true);
    }

    private void build() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(650);
        splitPane.setResizeWeight(0.6);

        buildLeftPanel();
        buildRightPanel();

        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);
        add(splitPane, BorderLayout.CENTER);

        statusLabel = new JLabel("Listo");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        add(statusLabel, BorderLayout.SOUTH);

        rightPanel.setVisible(false);
    }

    private void buildLeftPanel() {
        leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(AppColors.background);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Buscar:"));
        searchField = new JTextField(20);
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { filter(); }
            @Override public void removeUpdate(DocumentEvent e) { filter(); }
            @Override public void changedUpdate(DocumentEvent e) { filter(); }
        });
        searchPanel.add(searchField);

        newButton = new JButton("Nuevo Estudiante");
        newButton.setBackground(AppColors.primary);
        newButton.setForeground(Color.WHITE);
        newButton.setCursor(AppCursors.HAND);
        newButton.addActionListener(e -> presenter.showNewForm());
        searchPanel.add(newButton);

        leftPanel.add(searchPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Apellidos", "Nombres", "CI", "Nota"}, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(AppFonts.TITLE_H1);
        table.getTableHeader().setReorderingAllowed(false);

        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    int modelRow = table.convertRowIndexToModel(row);
                    int id = (int) tableModel.getValueAt(modelRow, 0);
                    String lastname = (String) tableModel.getValueAt(modelRow, 1);
                    String name = (String) tableModel.getValueAt(modelRow, 2);
                    String ci = (String) tableModel.getValueAt(modelRow, 3);
                    String grade = (String) tableModel.getValueAt(modelRow, 4);
                    Student selected = new Student(id, lastname, name, ci, grade);
                    presenter.onStudentSelected(selected);
                }
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        leftPanel.add(scroll, BorderLayout.CENTER);
    }

    private void buildRightPanel() {
        rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(AppColors.secondary);
        rightPanel.setBorder(BorderFactory.createTitledBorder("Datos del Estudiante"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        int y = 0;
        gbc.gridx = 0; gbc.gridy = y;
        rightPanel.add(new JLabel("Apellido:"), gbc);
        gbc.gridx = 1;
        lastNameField = new TextField("Apellido");
        lastNameField.setPreferredSize(new Dimension(200, 30));
        addValidation(lastNameField);
        rightPanel.add(lastNameField, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        rightPanel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        nameField = new TextField("Nombre");
        addValidation(nameField);
        rightPanel.add(nameField, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        rightPanel.add(new JLabel("CI:"), gbc);
        gbc.gridx = 1;
        ciField = new TextField("CI");
        addValidation(ciField);
        rightPanel.add(ciField, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        rightPanel.add(new JLabel("Nota:"), gbc);
        gbc.gridx = 1;
        gradeField = new TextField("Nota");
        addValidation(gradeField);
        rightPanel.add(gradeField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        createButton = new JButton("Crear");
        createButton.setBackground(AppColors.primary);
        createButton.setForeground(Color.WHITE);
        createButton.setCursor(AppCursors.HAND);
        createButton.addActionListener(e -> onCreate());

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

        clearButton = new JButton("Limpiar");
        clearButton.setCursor(AppCursors.HAND);
        clearButton.addActionListener(e -> presenter.clearForm());

        buttonPanel.add(createButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        gbc.gridwidth = 2;
        rightPanel.add(buttonPanel, gbc);
    }

    private void addValidation(JTextField field) {
        defaultBorder = field.getBorder();
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { validateField(field); }
            @Override public void removeUpdate(DocumentEvent e) { validateField(field); }
            @Override public void changedUpdate(DocumentEvent e) { validateField(field); }
        });
    }

    private void validateField(JTextField field) {
        String text = field.getText().trim();
        if (field == ciField) {
            if (!text.matches("\\d*")) {
                field.setBorder(errorBorder);
            } else {
                field.setBorder(okBorder);
            }
        } else if (field == gradeField) {
            try {
                if (!text.isEmpty()) {
                    double val = Double.parseDouble(text);
                    if (val < 0 || val > 100) field.setBorder(errorBorder);
                    else field.setBorder(okBorder);
                } else {
                    field.setBorder(defaultBorder);
                }
            } catch (NumberFormatException e) {
                field.setBorder(errorBorder);
            }
        } else {
            if (text.isEmpty() || text.equals("Apellido") || text.equals("Nombre") ||
                text.equals("CI") || text.equals("Nota")) {
                field.setBorder(errorBorder);
            } else {
                field.setBorder(okBorder);
            }
        }
    }

    private void filter() {
        String query = searchField.getText().toLowerCase().trim();
        if (query.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query, 1, 2)); // busca en apellido (col 1) y nombre (col 2)
        }
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
            showError("Seleccione un estudiante en la tabla");
            return;
        }
        int modelRow = table.convertRowIndexToModel(row);
        int id = (int) tableModel.getValueAt(modelRow, 0);
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
            int modelRow = table.convertRowIndexToModel(row);
            int id = (int) tableModel.getValueAt(modelRow, 0);
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
        filter();
    }

    @Override
    public void showError(String message) {
        statusLabel.setText("ERROR: " + message);
        statusLabel.setForeground(Color.RED);
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void clearError() {
        statusLabel.setText("Listo");
        statusLabel.setForeground(Color.BLACK);
    }

    @Override
    public void showSuccess(String message) {
        statusLabel.setText("Éxito: " + message);
        statusLabel.setForeground(Color.GREEN);
        JOptionPane.showMessageDialog(this, message, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void setLoading(boolean loading) {
        setCursor(loading ? AppCursors.WAIT : Cursor.getDefaultCursor());
        createButton.setEnabled(!loading);
        updateButton.setEnabled(!loading);
        deleteButton.setEnabled(!loading);
        newButton.setEnabled(!loading);
    }

    @Override
    public void setPermissions(boolean canCreate, boolean canEdit, boolean canDelete) {
        createButton.setEnabled(canCreate);
        updateButton.setEnabled(canEdit);
        deleteButton.setEnabled(canDelete);
        newButton.setEnabled(canCreate);
    }

    @Override
    public void setFormData(Student student) {
        lastNameField.setText(student.getLastname());
        nameField.setText(student.getName());
        ciField.setText(student.getCi());
        gradeField.setText(student.getGrade());
        rightPanel.setVisible(true);
        splitPane.setDividerLocation(0.6);
        lastNameField.setBorder(okBorder);
        nameField.setBorder(okBorder);
        ciField.setBorder(okBorder);
        gradeField.setBorder(okBorder);
    }

    @Override
    public void clearForm() {
        lastNameField.setText("Apellido");
        nameField.setText("Nombre");
        ciField.setText("CI");
        gradeField.setText("Nota");
        lastNameField.setBorder(defaultBorder);
        nameField.setBorder(defaultBorder);
        ciField.setBorder(defaultBorder);
        gradeField.setBorder(defaultBorder);
        table.clearSelection();
        statusLabel.setText("Formulario limpiado");
        rightPanel.setVisible(false);
    }

    @Override
    public void showForm(boolean show) {
        rightPanel.setVisible(show);
        if (show) {
            splitPane.setDividerLocation(0.6);
        }
    }
}