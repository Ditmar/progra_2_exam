package presentation.dashboard;

import java.util.List;

import domain.model.Student;
import domain.usecase.DeleteStudentUseCase;
import domain.usecase.GetStudentsUseCase;
import domain.usecase.SaveStudentUseCase;
import domain.usecase.UpdateStudentUseCase;
import presentation.dashboard.contract.DashboardPresenterContract;
import presentation.dashboard.contract.DashboardViewContract;

public class HandlerDashboardWindow implements DashboardPresenterContract {

    private DashboardViewContract view;

    private final GetStudentsUseCase getStudentsUseCase;
    private final SaveStudentUseCase saveStudentUseCase;
    private final UpdateStudentUseCase updateStudentUseCase;
    private final DeleteStudentUseCase deleteStudentUseCase;

    public HandlerDashboardWindow(
            GetStudentsUseCase getStudentsUseCase,
            SaveStudentUseCase saveStudentUseCase,
            UpdateStudentUseCase updateStudentUseCase,
            DeleteStudentUseCase deleteStudentUseCase) {

        this.getStudentsUseCase = getStudentsUseCase;
        this.saveStudentUseCase = saveStudentUseCase;
        this.updateStudentUseCase = updateStudentUseCase;
        this.deleteStudentUseCase = deleteStudentUseCase;
    }

    @Override
    public void attach(DashboardViewContract view) {
        this.view = view;
    }

    @Override
    public void loadStudents() {

        try {

            List<Student> students = getStudentsUseCase.execute();

            view.showStudents(students);

        } catch (Exception e) {

            view.showError(e.getMessage());

        }

    }

    @Override
    public void saveStudent(Student student) {

        try {

            saveStudentUseCase.execute(student);

            view.showSuccess("Estudiante registrado correctamente.");

            loadStudents();

            view.clearForm();

        } catch (Exception e) {

            view.showError(e.getMessage());

        }

    }

    @Override
    public void updateStudent(Student student) {

        try {

            updateStudentUseCase.execute(student);

            view.showSuccess("Estudiante actualizado correctamente.");

            loadStudents();

            view.clearForm();

        } catch (Exception e) {

            view.showError(e.getMessage());

        }

    }

    @Override
    public void deleteStudent(String ci) {

        try {

            deleteStudentUseCase.execute(ci);

            view.showSuccess("Estudiante eliminado correctamente.");

            loadStudents();

            view.clearForm();

        } catch (Exception e) {

            view.showError(e.getMessage());

        }

    }

}