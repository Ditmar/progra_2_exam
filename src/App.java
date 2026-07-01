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
import presentation.login.HandlerLoginWindow;
import presentation.login.LoginWindow;

public class App {
    public static void main(String[] args) throws Exception {
        ResourceLoader.loadFonts();
        Runnable runable = () -> {
            UserRepository repository = new MockUserRepository(); // datos base de datos
            LoginUseCase loginUseCase = new LoginUseCase(repository); // negocio todas las acciones del negocio van a

            HandlerLoginWindow handlerLoginWindow = new HandlerLoginWindow(loginUseCase); // manejador de UI con logica

            StudentRepository studentRepository = new MockStudentRepository();
            GetStudentsUseCase getStudentsUseCase = new GetStudentsUseCase(studentRepository);
            SaveStudentUseCase saveStudentUseCase = new SaveStudentUseCase(studentRepository);
            UpdateStudentUseCase updateStudentUseCase = new UpdateStudentUseCase(studentRepository);
            DeleteStudentUseCase deleteStudentUseCase = new DeleteStudentUseCase(studentRepository);
            HandlerDashboardWindow handlerDashboardWindow = new HandlerDashboardWindow(getStudentsUseCase, saveStudentUseCase, updateStudentUseCase, deleteStudentUseCase);

            LoginWindow window = new LoginWindow(handlerLoginWindow, handlerDashboardWindow); // UI
            handlerLoginWindow.attach(window); // manejador de UI con logica de negocio
        };
        SwingUtilities.invokeLater(runable);
    }
}
