import javax.swing.SwingUtilities;

import data.repository.MockStudentRepository;
import data.repository.MockUserRepository;
import domain.repository.StudentRepository;
import domain.repository.UserRepository;

import domain.usecase.DeleteStudentUseCase;
import domain.usecase.GetStudentsUseCase;
import domain.usecase.LoginUseCase;
import domain.usecase.SaveStudentUseCase;
import domain.usecase.UpdateStudentUseCase;

import infrastructure.config.ResourceLoader;
import presentation.dashboard.HandlerDashboardWindow;
import presentation.dashboard.contract.DashboardPresenterContract;
import presentation.login.HandlerLoginWindow;
import presentation.login.LoginWindow;

public class App {
    public static void main(String[] args) throws Exception {
        ResourceLoader.loadFonts();
        Runnable runable = () -> {
            UserRepository userRepository = new MockUserRepository(); // datos base de datos
            LoginUseCase loginUseCase = new LoginUseCase(userRepository); // negocio todas las acciones del negocio van a
            HandlerLoginWindow loginPresente = new HandlerLoginWindow(loginUseCase); // manejador de UI con logica

            StudentRepository studentRepository = new MockStudentRepository();
            GetStudentsUseCase getStudents = new GetStudentsUseCase(studentRepository);
            SaveStudentUseCase saveStudent = new SaveStudentUseCase(studentRepository);
            UpdateStudentUseCase updateStudent = new UpdateStudentUseCase(studentRepository);
            DeleteStudentUseCase deleteStudent = new DeleteStudentUseCase(studentRepository);
            DashboardPresenterContract dashboardPresenter = new HandlerDashboardWindow(getStudents, saveStudent, updateStudent, deleteStudent);

            LoginWindow window = new LoginWindow(loginPresente, dashboardPresenter); // UI
            loginPresente.attach(window); // manejador de UI con logica de negocio
        };
        SwingUtilities.invokeLater(runable);
    }
}
