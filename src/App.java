import javax.swing.SwingUtilities;
import data.repository.MockUserRepository;
import data.repository.MockStudentRepository;
import domain.repository.UserRepository;
import domain.repository.StudentRepository;
import domain.usecase.LoginUseCase;
import domain.usecase.ManageStudentUseCase;
import infrastructure.config.ResourceLoader;
import presentation.dashboard.DashboardWindow;
import presentation.dashboard.HandlerDashboardWindow;
import presentation.login.HandlerLoginWindow;
import presentation.login.LoginWindow;

public class App {
    public static void main(String[] args) throws Exception {
        ResourceLoader.loadFonts();
        Runnable runable = () -> {
            UserRepository userRepository = new MockUserRepository(); 
            LoginUseCase loginUseCase = new LoginUseCase(userRepository); 
            HandlerLoginWindow handlerLoginWindow = new HandlerLoginWindow(loginUseCase); 

            StudentRepository studentRepository = new MockStudentRepository(); 
            ManageStudentUseCase studentUseCase = new ManageStudentUseCase(studentRepository); 
            LoginWindow window = new LoginWindow(handlerLoginWindow, currentUser -> {
                HandlerDashboardWindow handlerDashboardWindow = new HandlerDashboardWindow(studentUseCase, currentUser);
                DashboardWindow dashboardWindow = new DashboardWindow(handlerDashboardWindow);
                handlerDashboardWindow.attach(dashboardWindow);
            });

            handlerLoginWindow.attach(window);
        };
        SwingUtilities.invokeLater(runable);
    }
}
