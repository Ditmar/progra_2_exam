package presentation.dashboard;

import domain.model.Student;
import domain.usecase.ManageStudentsUseCase;
import presentation.dashboard.contract.DashboardViewContract;
import java.util.List;

public class HandlerDashboardWindow implements DashboardViewContract.Presenter {
    private DashboardViewContract.View view;
    private final ManageStudentsUseCase useCase;

    public HandlerDashboardWindow(ManageStudentsUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void attachView(DashboardViewContract.View view) {
        this.view = view;
    }

    @Override
    public void requestStudentsLoad() {
        try {
            List<Student> currentList = useCase.getAllStudents();
            view.showStudentsList(currentList);
        } catch (Exception ex) {
            view.displayErrorMessage("Error al recuperar datos: " + ex.getMessage());
        }
    }

    @Override
    public void addStudent(String lastname, String name, String ci, String grade) {
        try {
            Student s = new Student(0, lastname, name, ci, grade);
            useCase.createStudent(s);
            view.displaySuccessMessage("Estudiante registrado con éxito.");
            view.clearInputFields();
            requestStudentsLoad();
        } catch (IllegalArgumentException ex) {
            view.displayErrorMessage(ex.getMessage());
        }
    }

    @Override
    public void updateStudentInfo(int id, String lastname, String name, String ci, String grade) {
        try {
            Student s = new Student(id, lastname, name, ci, grade);
            useCase.modifyStudent(s);
            view.displaySuccessMessage("Datos actualizados correctamente.");
            view.clearInputFields();
            requestStudentsLoad();
        } catch (IllegalArgumentException ex) {
            view.displayErrorMessage(ex.getMessage());
        }
    }

    @Override
    public void deleteStudentFromSystem(int id) {
        try {
            useCase.removeStudent(id);
            view.displaySuccessMessage("Estudiante eliminado.");
            requestStudentsLoad();
        } catch (Exception ex) {
            view.displayErrorMessage("No se pudo eliminar: " + ex.getMessage());
        }
    }

    @Override
    public void evaluateAccessRole(String role) {
        // Encapsulamos la regla del Rol aquí en lugar de usar if en la vista
        if (role == null || !role.equalsIgnoreCase("ADMIN")) {
            view.restrictEditingPrivileges();
        }
    }
}