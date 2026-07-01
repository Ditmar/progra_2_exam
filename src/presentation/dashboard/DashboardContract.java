package presentation.dashboard.contract;

import domain.model.Student;
import java.util.List;

public interface DashboardContract {

    interface View {
        void showStudents(List<Student> students);
        void showError(String message);
        void showSuccess(String message);
        void clearForm();
        void disableEditingActions(); // Requerido para el control de roles (punto 6)
    }

    interface Presenter {
        void attach(View view);
        void loadStudents();
        void saveStudent(String ci, String name);
        void updateStudent(int id, String ci, String name);
        void deleteStudent(int id);
        void checkPermissions(String userRole);
    }
}