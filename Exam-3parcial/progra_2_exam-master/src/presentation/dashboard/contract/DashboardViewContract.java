package presentation.dashboard.contract;

import java.util.List;

import domain.model.Student;

public interface DashboardViewContract { 

    void showStudents(List<Student> students);
    
    void showSuccess(String message);

    void showError(String message);

    void clearForm();
    
}
