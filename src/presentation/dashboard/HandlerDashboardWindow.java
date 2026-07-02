package presentation.dashboard;

import java.util.ArrayList;

import domain.model.Student;
import domain.usecase.StudentUseCase;
import presentation.dashboard.contract.DashboardPresenterContract;
import presentation.dashboard.contract.DashboardViewContract;


public class HandlerDashboardWindow implements DashboardPresenterContract {

    private final StudentUseCase studentUseCase;
    private DashboardViewContract view;

    public HandlerDashboardWindow(StudentUseCase studentUseCase) {
        this.studentUseCase = studentUseCase;
    }

    public void attach(DashboardViewContract view) {
        this.view = view;
        onLoadStudents(); 
    }

    @Override
    public void onLoadStudents() {
        try {
            ArrayList<Student> students = studentUseCase.getAll();
            view.showStudents(students);
        } catch (Exception e) {
            view.showError("Error al cargar estudiantes: " + e.getMessage());
        }
    }

    @Override
    public void onSaveStudent(String lastname, String name, String ci, String grade) {
        try {
            studentUseCase.create(lastname, name, ci, grade);
            view.showSuccess("Estudiante guardado correctamente.");
            view.clearForm();
            onLoadStudents();
        } catch (IllegalArgumentException e) {
            view.showError(e.getMessage());
        }
    }

    @Override
    public void onUpdateStudent(int id, String lastname, String name, String ci, String grade) {
        try {
            studentUseCase.edit(id, lastname, name, ci, grade);
            view.showSuccess("Estudiante actualizado correctamente.");
            view.clearForm();
            onLoadStudents();
        } catch (IllegalArgumentException e) {
            view.showError(e.getMessage());
        }
    }

    @Override
    public void onDeleteStudent(int id) {
        try {
            studentUseCase.remove(id);
            view.showSuccess("Estudiante eliminado correctamente.");
            view.clearForm();
            onLoadStudents();
        } catch (IllegalArgumentException e) {
            view.showError(e.getMessage());
        }
    }
}
