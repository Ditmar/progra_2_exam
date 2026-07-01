import data.repository.MockStudentRepository;
import data.repository.MockUserRepository;
import domain.repository.StudentRepository;
import domain.repository.UserRepository;
import domain.service.PermissionStrategy;
import domain.service.PermissionStrategyFactory;
import domain.usecase.*;
import infrastructure.config.ResourceLoader;
import presentation.dashboard.DashboardWindow;
import presentation.dashboard.HandlerDashboardWindow;
import presentation.login.HandlerLoginWindow;
import presentation.login.LoginWindow;

import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        ResourceLoader.loadFonts();
        SwingUtilities.invokeLater(() -> {
            
            UserRepository userRepository = new MockUserRepository();
            StudentRepository studentRepository = new MockStudentRepository();

            
            LoginUseCase loginUseCase = new LoginUseCase(userRepository);
            HandlerLoginWindow loginPresenter = new HandlerLoginWindow(loginUseCase);
            LoginWindow loginView = new LoginWindow(loginPresenter);
            loginPresenter.attach(loginView);

            GetStudentsUseCase getUseCase = new GetStudentsUseCase(studentRepository);
            CreateStudentUseCase createUseCase = new CreateStudentUseCase(studentRepository);
            UpdateStudentUseCase updateUseCase = new UpdateStudentUseCase(studentRepository);
            DeleteStudentUseCase deleteUseCase = new DeleteStudentUseCase(studentRepository);


            loginView.setOnLoginSuccess(user -> {
                PermissionStrategy strategy = PermissionStrategyFactory.getStrategy(user.getRole());
                HandlerDashboardWindow dashboardPresenter = new HandlerDashboardWindow(
                        getUseCase, createUseCase, updateUseCase, deleteUseCase, strategy
                );
                new DashboardWindow(dashboardPresenter);
                loginView.dispose();
            });
        });
    }
}