package presentation.dashboard.contract;

import domain.model.Student;
import java.util.List;

public interface DashboardViewContract {
    void showStudents(List<Student> students);
    void showError(String message);
    void clearError();
    void showSuccess(String message);
    void setLoading(boolean loading);
    void setPermissions(boolean canCreate, boolean canEdit, boolean canDelete);
    void setFormData(Student student);
    void clearForm();
}