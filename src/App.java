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
        Runnable runable = () -> {
            UserRepository repository = new MockUserRepository(); // datos base de datos
            LoginUseCase loginUseCase = new LoginUseCase(repository); // negocio todas las acciones del negocio van a
            HandlerLoginWindow handlerLoginWindow = new HandlerLoginWindow(loginUseCase); // manejador de UI con logica
            LoginWindow window = new LoginWindow(handlerLoginWindow); // UI
            handlerLoginWindow.attach(window); // manejador de UI con logica de negocio
        };
        SwingUtilities.invokeLater(runable);
    }
}
