package presentation.dashboard;

import domain.model.Student;
import domain.model.User;
import domain.usecase.ManageStudentUseCase;
import presentation.dashboard.contract.DashboardPresenterContract;
import presentation.dashboard.contract.DashboardViewContract;

import java.util.List;

public class HandlerDashboardWindow implements DashboardPresenterContract {

    private final ManageStudentUseCase useCase;
    private DashboardViewContract view;
    private final User currentUser;

    public HandlerDashboardWindow(ManageStudentUseCase useCase, User currentUser) {
        this.useCase = useCase;
        this.currentUser = currentUser;
    }

    public void attach(DashboardViewContract view) {
        this.view = view;
        this.view.applyRolePermissions(currentUser);
        loadStudents();
    }

    @Override
    public void loadStudents() {
        List<Student> students = useCase.getStudents();
        view.showStudents(students);
    }

    @Override
    public void onSaveStudentClicked(String lastname, String name, String ci, String grade) {
        view.clearError();
        try {
            boolean exists = useCase.getStudents().stream()
                                    .anyMatch(s -> s.getCi().equals(ci));

            if (exists) {
                useCase.updateStudent(lastname, name, ci, grade);
                view.showSuccess("Estudiante actualizado correctamente.");
            } else {
                useCase.saveStudent(lastname, name, ci, grade);
                view.showSuccess("Estudiante guardado correctamente.");
            }
            
            view.clearForm();
            loadStudents();
        } catch (IllegalArgumentException e) {
            view.showError(e.getMessage());
        }
    }

    @Override
    public void onDeleteStudentClicked(String ci) {
        view.clearError();
        try {
            useCase.deleteStudent(ci);
            view.showSuccess("Estudiante eliminado.");
            view.clearForm();
            loadStudents();
        } catch (IllegalArgumentException e) {
            view.showError(e.getMessage());
        }
    }
}