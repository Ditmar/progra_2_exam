package presentation.dashboard.contract;

import domain.model.Student; 


public interface DashboardPresenterContract {

    void attach(DashboardViewContract view);

    void loadStudents();

    void saveStudent(Student student);

    void updateStudent(Student student);

    void deleteStudent(String ci);

}

    
