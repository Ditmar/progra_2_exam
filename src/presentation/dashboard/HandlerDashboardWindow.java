package presentation.dashboard;

import domain.model.Student;
import domain.service.PermissionStrategy;
import domain.usecase.*;
import presentation.dashboard.contract.DashboardPresenterContract;
import presentation.dashboard.contract.DashboardViewContract;

import java.util.List;

public class HandlerDashboardWindow implements DashboardPresenterContract {
    private final GetStudentsUseCase getUseCase;
    private final CreateStudentUseCase createUseCase;
    private final UpdateStudentUseCase updateUseCase;
    private final DeleteStudentUseCase deleteUseCase;
    private final PermissionStrategy permissionStrategy;

    private DashboardViewContract view;
    private Student selectedStudent;

    public HandlerDashboardWindow(GetStudentsUseCase getUseCase,
                                  CreateStudentUseCase createUseCase,
                                  UpdateStudentUseCase updateUseCase,
                                  DeleteStudentUseCase deleteUseCase,
                                  PermissionStrategy permissionStrategy) {
        this.getUseCase = getUseCase;
        this.createUseCase = createUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
        this.permissionStrategy = permissionStrategy;
    }

    public void attach(DashboardViewContract view) {
        this.view = view;
        if (view != null) {
            view.setPermissions(
                permissionStrategy.canCreate(),
                permissionStrategy.canEdit(),
                permissionStrategy.canDelete()
            );
            loadStudents();
        }
    }

    @Override
    public void loadStudents() {
        if (view == null) return;
        view.setLoading(true);
        view.clearError();
        try {
            List<Student> students = getUseCase.execute();
            view.showStudents(students);
        } catch (Exception e) {
            view.showError(e.getMessage());
        } finally {
            view.setLoading(false);
        }
    }

    @Override
    public void createStudent(Student student) {
        if (view == null) return;
        if (!permissionStrategy.canCreate()) {
            view.showError("No tiene permisos para crear");
            return;
        }
        view.setLoading(true);
        view.clearError();
        try {
            createUseCase.execute(student);
            view.showSuccess("Estudiante creado correctamente");
            loadStudents();
            view.clearForm();
            view.showForm(false);
        } catch (IllegalArgumentException e) {
            view.showError(e.getMessage());
        } finally {
            view.setLoading(false);
        }
    }

    @Override
    public void updateStudent(Student student) {
        if (view == null) return;
        if (!permissionStrategy.canEdit()) {
            view.showError("No tiene permisos para editar");
            return;
        }
        if (selectedStudent == null) {
            view.showError("Seleccione un estudiante para actualizar");
            return;
        }
        student.setId(selectedStudent.getId());
        view.setLoading(true);
        view.clearError();
        try {
            updateUseCase.execute(student);
            view.showSuccess("Estudiante actualizado correctamente");
            loadStudents();
            view.clearForm();
            view.showForm(false);
            selectedStudent = null;
        } catch (IllegalArgumentException e) {
            view.showError(e.getMessage());
        } finally {
            view.setLoading(false);
        }
    }

    @Override
    public void deleteStudent(int id) {
        if (view == null) return;
        if (!permissionStrategy.canDelete()) {
            view.showError("No tiene permisos para eliminar");
            return;
        }
        view.setLoading(true);
        view.clearError();
        try {
            deleteUseCase.execute(id);
            view.showSuccess("Estudiante eliminado correctamente");
            loadStudents();
            view.clearForm();
            view.showForm(false);
            selectedStudent = null;
        } catch (IllegalArgumentException e) {
            view.showError(e.getMessage());
        } finally {
            view.setLoading(false);
        }
    }

    @Override
    public void onStudentSelected(Student student) {
        this.selectedStudent = student;
        if (view != null) {
            view.setFormData(student);
            view.showForm(true);
        }
    }

    @Override
    public void clearForm() {
        if (view != null) {
            view.clearForm();
            view.showForm(false);
        }
        selectedStudent = null;
    }

    @Override
    public void showNewForm() {
        if (view != null) {
            view.clearForm();
            view.showForm(true);
        }
    }
}