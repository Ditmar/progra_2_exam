package presentation.dashboard.contract;

import java.util.ArrayList;

import domain.model.Student;

public interface DashboardViewContract {
    void showStudents(ArrayList<Student> students);
    void showError(String message);
    void clearError();
    void clearForm();
    void setFormEnabled(boolean enabled);
}
