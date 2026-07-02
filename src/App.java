import javax.swing.SwingUtilities;

import data.repository.MockUserRepository;
import domain.repository.UserRepository;
import domain.usecase.LoginUseCase;
import infrastructure.config.ResourceLoader;
import presentation.login.HandlerLoginWindow;
import presentation.login.LoginWindow;


public class App {
    public static void main(String[] args) throws Exception {
        ResourceLoader.loadFonts();
        SwingUtilities.invokeLater(() -> {
           
            UserRepository userRepository = new MockUserRepository();
            LoginUseCase loginUseCase = new LoginUseCase(userRepository);
            HandlerLoginWindow handlerLoginWindow = new HandlerLoginWindow(loginUseCase);
            LoginWindow window = new LoginWindow(handlerLoginWindow);
            handlerLoginWindow.attach(window);

            
        });
    }
}