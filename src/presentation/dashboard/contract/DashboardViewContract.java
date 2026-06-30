package presentation.dashboard.contract;

import java.util.ArrayList;
import domain.model.Student;


public interface DashboardViewContract {
    void showStudents(ArrayList<Student> students);
    void showError(String message);
    void showSuccess(String message);
    void clearForm();
    void setEditingEnabled(boolean enabled);
}
