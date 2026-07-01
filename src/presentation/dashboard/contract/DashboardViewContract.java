package presentation.dashboard.contract;

import domain.model.Student;
import java.util.List;

public interface DashboardViewContract {
    interface View {
        void showStudentsList(List<Student> students);
        void displayErrorMessage(String message);
        void displaySuccessMessage(String message);
        void clearInputFields();
        void restrictEditingPrivileges(); // Control por rol
    }

    interface Presenter {
        void attachView(View view);
        void requestStudentsLoad();
        void addStudent(String lastname, String name, String ci, String grade);
        void updateStudentInfo(int id, String lastname, String name, String ci, String grade);
        void deleteStudentFromSystem(int id);
        void evaluateAccessRole(String role);
    }
}