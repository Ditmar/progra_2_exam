package presentation.dashboard;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.JButton;

import domain.model.User;
import domain.model.Student;
import infrastructure.theme.AppColors;
import infrastructure.theme.AppFonts;
import presentation.dashboard.contract.DashboardViewContract;
import presentation.dashboard.contract.DashboardPresenterContract;
import presentation.components.TextField;


public class DashboardWindow extends JFrame 
    implements DashboardViewContract{
    
    private JPanel panel;
    private JTable table;
    private DefaultTableModel tableModel;
    private TextField txtLastname;
    private TextField txtName;
    private TextField txtCi;
    private TextField txtGrade;

    private JButton btnSave;
    private JButton btnUpdate;
    private JButton btnDelete;

    private final DashboardPresenterContract presenter;
    private final User loggedUser;

    public DashboardWindow(
        DashboardPresenterContract presenter,
        User loggedUser) {
    
    super("Gestión de Estudiantes");

    this.presenter = presenter;
    this.loggedUser = loggedUser;

    presenter.attach(this);

    build();

    configurePermisssions();

    presenter.loadStudents();

}
         
    private void build() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1200, 800);
        this.setLocationRelativeTo(this);
        this.setLayout(null);
        setVisible(true);
        buildContainers();
        buildForm();
        buildTable();
        buildEvents();
    }

    private void buildContainers() {
        panel = new JPanel(null);
        panel.setSize(1000, 730);
        panel.setLocation(100, 70);
        panel.setBackground(AppColors.background);
        this.add(panel);

    }

    private void buildForm(){
        txtLastname = new TextField("Apellidos");
        txtLastname.setBounds(20,610,180,40);
        txtName = new TextField("Nombres");
        txtName.setBounds(220,610,180,40);
        txtCi = new TextField("CI");
        txtCi.setBounds(420,610,160,40);
        txtGrade = new TextField("Nota");
        txtGrade.setBounds(600,610,70,40);

        
        btnSave = new JButton("Guardar");
        btnSave.setBounds(690, 610, 100, 40);
        btnUpdate = new JButton("Actualizar");
        btnUpdate.setBounds(800, 610, 100, 40);
        btnDelete = new JButton("Eliminar");
        btnDelete.setBounds(910, 610, 100, 40);
        
        panel.add(txtLastname);
        panel.add(txtName);
        panel.add(txtCi);
        panel.add(txtGrade);

        panel.add(btnSave);
        panel.add(btnUpdate);
        panel.add(btnDelete);

    }


    private String getFieldValue(TextField field) {
        String text = field.getText().trim();
        return text.equals(field.getPlaceholder()) ? "" : text;
    }

    private Student buildStudent(){
        return new Student(
            getFieldValue(txtLastname), 
            getFieldValue(txtName), 
            getFieldValue(txtCi), 
            getFieldValue(txtGrade));
    }

    private void buildEvents(){
        btnSave.addActionListener(e->{
            presenter.saveStudent(buildStudent());
        });

        btnUpdate.addActionListener(e->{
            presenter.updateStudent(buildStudent());
        });

        btnDelete.addActionListener(e -> {

    int row = table.getSelectedRow();

    if (row == -1) {
        JOptionPane.showMessageDialog(
                this,
                "Debe seleccionar un estudiante.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
        return;
    }    

    String lastname = table.getValueAt(row, 0).toString();
    String name = table.getValueAt(row, 1).toString();
    String ci = table.getValueAt(row, 2).toString();

    int option = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de eliminar este estudiante?\n\n"
                    + "Apellido: " + lastname + "\n"
                    + "Nombre: " + name + "\n"
                    + "CI: " + ci + "\n\n"
                    + "Esta acción no se puede deshacer.",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

    if (option == JOptionPane.YES_OPTION) {
        presenter.deleteStudent(ci);
    }
});
       
        table.getSelectionModel().addListSelectionListener(e->{
            if(e.getValueIsAdjusting())
                return;

            int row = table.getSelectedRow();
            if(row == -1) 
                return;

            txtLastname.setText(table.getValueAt(row,0).toString());
            txtName.setText(table.getValueAt(row, 1).toString());
            txtCi.setText(table.getValueAt(row, 2).toString());
            txtGrade.setText(table.getValueAt(row, 3).toString());
            txtCi.setEditable(false);
        });
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
        table.setSize(new Dimension(1000, 500));
        
        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setBounds(0, 0, 1000, 600);
        panel.add(scrollpane);

    }


    @Override
public void showStudents(List<Student> students) {

    tableModel.setRowCount(0);

    for (Student student : students) {

        tableModel.addRow(new Object[] {
                student.getLastname(),
                student.getName(),
                student.getCi(),
                student.getGrade()
        });

    }

    table.clearSelection();

}

@Override
public void showSuccess(String message) {

    JOptionPane.showMessageDialog(
            this,
            message,
            "Información",
            JOptionPane.INFORMATION_MESSAGE);

}

@Override
public void showError(String message) {

    JOptionPane.showMessageDialog(
            this,
            message,
            "Error",
            JOptionPane.ERROR_MESSAGE);

}

@Override
public void clearForm() {
    txtLastname.setText("");
    txtName.setText("");
    txtCi.setText("");
    txtGrade.setText("");
    table.clearSelection();

}

private void configurePermisssions(){
    boolean canEdit = loggedUser.getRole().canEditStudents();
    
    btnSave.setEnabled(canEdit);
    btnUpdate.setEnabled(canEdit);
    btnDelete.setEnabled(canEdit);

    txtLastname.setEditable(canEdit);
    txtName.setEditable(canEdit);
    txtCi.setEditable(canEdit);
    txtGrade.setEditable(canEdit);
}

}
