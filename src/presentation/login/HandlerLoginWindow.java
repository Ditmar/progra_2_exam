package presentation.dashboard;

import domain.model.Student;
import domain.usecase.ManageStudentsUseCase;
import presentation.dashboard.contract.DashboardPresenterContract;
import presentation.dashboard.contract.DashboardViewContract;

public class HandlerDashboardWindow implements DashboardPresenterContract {
    private final ManageStudentsUseCase useCase;
    private DashboardViewContract view;

    public HandlerDashboardWindow(ManageStudentsUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void attachView(DashboardViewContract view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void loadAllStudents() {
        if (view != null) {
            view.showStudentsList(useCase.executeList());
        }
    }

    @Override
    public void handleSaveOrUpdateStudent(String idStr, String name, String ci, String gradeStr) {
        try {
            if (gradeStr == null || gradeStr.trim().isEmpty()) {
                throw new IllegalArgumentException("La nota del estudiante es un campo obligatorio.");
            }
            
            double grade;
            try {
                grade = Double.parseDouble(gradeStr.trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("La nota debe ser un número válido.");
            }

            if (idStr == null || idStr.trim().isEmpty()) {
                // Modo creación
                useCase.executeSave(name, ci, grade);
                if (view != null) view.showSuccess("Estudiante registrado satisfactoriamente.");
            } else {
                // Modo edición
                int id = Integer.parseInt(idStr.trim());
                useCase.executeUpdate(id, name, ci, grade);
                if (view != null) view.showSuccess("Los datos del estudiante fueron actualizados.");
            }

            if (view != null) {
                view.clearFormFields();
                loadAllStudents();
            }
        } catch (IllegalArgumentException ex) {
            if (view != null) {
                view.showError(ex.getMessage()); // Captura excepciones específicas de dominio (Criterio 6)
            }
        }
    }

    @Override
    public void handleDeleteStudent(int id) {
        try {
            useCase.executeDelete(id);
            if (view != null) {
                view.showSuccess("Registro de estudiante eliminado.");
                view.clearFormFields();
                loadAllStudents();
            }
        } catch (IllegalArgumentException ex) {
            if (view != null) view.showError(ex.getMessage());
        }
    }

    @Override
    public void handleSelectStudent(int id, String name, String ci, double grade) {
        if (view != null) {
            view.populateFieldsForEdit(id, name, ci, grade);
        }
    }
}