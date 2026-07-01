package presentation.dashboard.contract;

public interface DashboardPresenterContract {
    void onLoadStudents();
    void onSaveStudent(String lastname, String name, String ci, String grade);
    void onUpdateStudent(int id, String lastname, String name, String ci, String grade);
    void onDeleteStudent(int id);
}
