package presentation.dashboard;

import domain.model.Role;
import domain.model.Student;
import domain.service.AccessControl;
import domain.usecase.CreateStudentUseCase;
import domain.usecase.DeleteStudentUseCase;
import domain.usecase.GetStudentsUseCase;
import domain.usecase.UpdateStudentUseCase;
import presentation.dashboard.contract.DashboardPresenterContract;
import presentation.dashboard.contract.DashboardViewContract;

import java.util.List;

public class HandlerDashboardWindow implements DashboardPresenterContract {
    private final GetStudentsUseCase getUseCase;
    private final CreateStudentUseCase createUseCase;
    private final UpdateStudentUseCase updateUseCase;
    private final DeleteStudentUseCase deleteUseCase;
    private final Role userRole;

    private DashboardViewContract view;
    private Student selectedStudent;

    public HandlerDashboardWindow(GetStudentsUseCase getUseCase,
                                  CreateStudentUseCase createUseCase,
                                  UpdateStudentUseCase updateUseCase,
                                  DeleteStudentUseCase deleteUseCase,
                                  Role userRole) {
        this.getUseCase = getUseCase;
        this.createUseCase = createUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
        this.userRole = userRole;
    }

    public void attach(DashboardViewContract view) {
        this.view = view;
        if (view != null) {
            boolean canCreate = AccessControl.canCreate(userRole);
            boolean canEdit = AccessControl.canEdit(userRole);
            boolean canDelete = AccessControl.canDelete(userRole);
            view.setPermissions(canCreate, canEdit, canDelete);
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
        view.setLoading(true);
        view.clearError();
        try {
            Student created = createUseCase.execute(student);
            view.showSuccess("Estudiante creado correctamente");
            loadStudents();
            view.clearForm();
        } catch (IllegalArgumentException e) {
            view.showError(e.getMessage());
        } finally {
            view.setLoading(false);
        }
    }

    @Override
    public void updateStudent(Student student) {
        if (view == null) return;
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
        if (id <= 0) {
            view.showError("Seleccione un estudiante para eliminar");
            return;
        }
        view.setLoading(true);
        view.clearError();
        try {
            deleteUseCase.execute(id);
            view.showSuccess("Estudiante eliminado correctamente");
            loadStudents();
            view.clearForm();
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
        }
    }

    @Override
    public void clearForm() {
        if (view != null) {
            view.clearForm();
        }
        selectedStudent = null;
    }
}