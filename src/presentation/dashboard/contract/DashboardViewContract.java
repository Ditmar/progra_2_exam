package presentation.dashboard.contract;

import java.util.List;
import domain.model.Student;
import domain.model.User;

public interface DashboardViewContract {
    void showStudents(List<Student> students);
    void showError(String message);
    void clearError();
    void showSuccess(String message);
    void clearForm();
    void applyRolePermissions(User currentUser);
}