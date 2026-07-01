package presentation.dashboard.contract;

import domain.model.Student;

public interface DashboardPresenterContract {
    void loadStudents();
    void onSaveStudentClicked(String lastname, String name, String ci, String grade);
    void onDeleteStudentClicked(String ci);
}