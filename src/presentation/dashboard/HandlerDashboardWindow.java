package presentation.dashboard;

import domain.model.Student;
import domain.service.PermissionManager;
import domain.usecase.*;
import presentation.dashboard.contract.DashboardPresenterContract;
import presentation.dashboard.contract.DashboardViewContract;

import java.util.List;

public class HandlerDashboardWindow implements DashboardPresenterContract {
    private final GetStudentsUseCase getUseCase;
    private final CreateStudentUseCase createUseCase;
    private final UpdateStudentUseCase updateUseCase;
    private final DeleteStudentUseCase deleteUseCase;
    private final PermissionManager permissionManager;

    private DashboardViewContract view;
    private Student selectedStudent;

    public HandlerDashboardWindow(GetStudentsUseCase getUseCase,
                                  CreateStudentUseCase createUseCase,
                                  UpdateStudentUseCase updateUseCase,
                                  DeleteStudentUseCase deleteUseCase,
                                  PermissionManager permissionManager) {
        this.getUseCase = getUseCase;
        this.createUseCase = createUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
        this.permissionManager = permissionManager;
    }

    public void attach(DashboardViewContract view) {
        this.view = view;
        if (view != null) {
            view.setPermissions(
                permissionManager.canCreate(),
                permissionManager.canEdit(),
                permissionManager.canDelete()
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
        if (!permissionManager.canCreate()) {
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
        if (!permissionManager.canEdit()) {
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
        if (!permissionManager.canDelete()) {
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
