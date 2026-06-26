package presentation.dashboard;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import domain.model.Student;
import infrastructure.theme.AppColors;
import infrastructure.theme.AppFonts;

// TODO:
// This code needs to be refactor 
// using the pattern MVP

public class DashboardWindow extends JFrame {
    private JPanel panel;
    private JTable table;
    private DefaultTableModel tableModel;

    public DashboardWindow() {
        super("Gestión de Estudiantes");
        build();
    }

    private void build() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1200, 800);
        this.setLocationRelativeTo(this);
        this.setLayout(null);
        setVisible(true);
        buildContainers();
        buildTable();
    }

    private void buildContainers() {
        panel = new JPanel(null);
        panel.setSize(1000, 730);
        panel.setLocation(100, 70);
        panel.setBackground(AppColors.background);
        this.add(panel);

    }

    private ArrayList<Student> getData() {
        ArrayList<Student> listOfStudents = new ArrayList<>();
        listOfStudents.add(new Student("Alvarez", "Diego", "543210", "22"));
        listOfStudents.add(new Student("Torres", "Lucia", "987654", "30"));
        listOfStudents.add(new Student("Ramirez", "Carlos", "135791", "45"));
        listOfStudents.add(new Student("Flores", "Gabriela", "246810", "18"));
        listOfStudents.add(new Student("Diaz", "Fernando", "112233", "27"));
        listOfStudents.add(new Student("Vargas", "Valeria", "445566", "35"));
        listOfStudents.add(new Student("Castillo", "Alejandro", "778899", "51"));
        listOfStudents.add(new Student("Morales", "Daniela", "990011", "23"));
        listOfStudents.add(new Student("Herrera", "Javier", "223344", "40"));
        listOfStudents.add(new Student("Castro", "Camila", "556677", "26"));
        listOfStudents.add(new Student("Medina", "Ricardo", "889900", "48"));
        listOfStudents.add(new Student("Aguilar", "Victoria", "121314", "33"));
        listOfStudents.add(new Student("Suarez", "Sebastian", "151617", "20"));
        listOfStudents.add(new Student("Salazar", "Natalia", "181920", "29"));
        listOfStudents.add(new Student("Delgado", "Juan", "212223", "55"));
        listOfStudents.add(new Student("Rios", "Valentina", "242526", "41"));
        listOfStudents.add(new Student("Mendoza", "Gabriel", "272829", "19"));
        listOfStudents.add(new Student("Ortega", "Isabella", "303132", "37"));
        listOfStudents.add(new Student("Rojas", "Nicolas", "333435", "62"));
        listOfStudents.add(new Student("Guerrero", "Paulina", "363738", "25"));
        return listOfStudents;
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
        loadData();
        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setBounds(0, 0, 1000, 600);
        panel.add(scrollpane);

    }

    private void loadData() {
        ArrayList<Student> listStudents = getData();
        tableModel.setRowCount(0);
        for (Student s : listStudents) {
            tableModel.addRow(new Object[] {
                    s.getLastname(),
                    s.getName(),
                    s.getCi(),
                    s.getGrade()
            });
        }
        table.clearSelection();
    }
}
