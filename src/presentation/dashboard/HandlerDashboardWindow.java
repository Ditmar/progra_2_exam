package presentation.dashboard;

import domain.usecase.DeleteStudentUseCase;
import domain.usecase.GetStudentsUseCase;
import domain.usecase.SaveStudentUseCase;
import domain.usecase.UpdateStudentUseCase;
import presentation.dashboard.contract.DashboardPresenterContract;
import presentation.dashboard.contract.DashboardViewContract;

public class HandlerDashboardWindow  implements DashboardPresenterContract{
    private final GetStudentsUseCase getStudentsUseCase;
    private final SaveStudentUseCase saveStudentUseCase;
    private final UpdateStudentUseCase updateStudentUseCase;
    private final DeleteStudentUseCase deleteStudentUseCase;
    private DashboardViewContract view;

    public HandlerDashboardWindow(
        GetStudentsUseCase getStudentsUseCase,
        SaveStudentUseCase saveStudentUseCase, 
        UpdateStudentUseCase updateStudentUseCase,
        DeleteStudentUseCase deleteStudentUseCase){
        this.getStudentsUseCase = getStudentsUseCase;
        this.saveStudentUseCase = saveStudentUseCase;
        this.updateStudentUseCase = updateStudentUseCase;
        this.deleteStudentUseCase = deleteStudentUseCase;
    }

    public void attach(DashboardViewContract view) {
        this.view = view;

    }
    @Override
    public void onLoadStudents() {
        view.showStudents(getStudentsUseCase.execute());
    }
    @Override
    public void onSaveStudent(String lastname, String name, String ci, String grade){
        view.clearError();
        try {
            saveStudentUseCase.execute(lastname, name, ci, grade);
            view.clearForm();
            view.showStudents(getStudentsUseCase.execute());

        } catch ( IllegalArgumentException e) {
            view.showError(e.getMessage());
        } catch (Exception e){
            view.showError("Error inesperado al guardar: "+ e.getMessage());
        }
    }

    @Override
    public void onDeleteStudent(int id){
        view.clearError();
        try {
            deleteStudentUseCase.execute(id);
            view.clearForm();
            view.showStudents(getStudentsUseCase.execute());
        } catch (IllegalArgumentException e){
            view.showError(e.getMessage());
        } catch (Exception e) {
            view.showError("Error inesperado al eliminar: "+ e.getMessage());
        }
    }
    @Override
    public void onUpdateStudent(int id, String lastname, String name, String ci, String grade){
        view.clearError();
        try {
            updateStudentUseCase.execute(id, lastname, name, ci, grade);
            view.clearForm();
            view.showStudents(getStudentsUseCase.execute());
        } catch (IllegalArgumentException e) {
            view.showError(e.getMessage());
        } catch (Exception e){
            view.showError("Error inesperado al eliminar: " +e.getMessage());
        }
    }
    
}
