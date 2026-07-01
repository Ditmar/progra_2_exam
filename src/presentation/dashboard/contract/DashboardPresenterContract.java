package presentation.dashboard.contract;

import domain.model.Student;

public interface DashboardPresenterContract {
    void loadStudents();
    void createStudent(Student student);
    void updateStudent(Student student);
    void deleteStudent(int id);
    void onStudentSelected(Student student);
    void clearForm();
}