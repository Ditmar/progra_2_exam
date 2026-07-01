package src;

import data.repository.MockStudentRepository;
import domain.repository.StudentRepository;
import domain.usecase.ManageStudentsUseCase;
import presentation.dashboard.DashboardWindow;
import presentation.dashboard.HandlerDashboardWindow;

public class App {

    // Este es el método MAIN, el punto de partida que ejecuta Visual Studio Code
    public static void main(String[] args) {
        // Criterio de Aceptación 1 y 7: Para probar tu Dashboard directamente, 
        // simulamos que un usuario inicia sesión con el rol de "ADMIN".
        // Si quieres probar cómo se bloquea, puedes cambiarlo a "USER".
        iniciarDashboardParaSesion("ADMIN");
    }

    public static void iniciarDashboardParaSesion(String userRole) {
        // 1. Instanciamos la infraestructura de datos en memoria (Capa Data)
        StudentRepository studentRepository = new MockStudentRepository();

        // 2. Instanciamos la lógica de negocio inyectando el repositorio (Capa Domain)
        ManageStudentsUseCase manageStudentsUseCase = new ManageStudentsUseCase(studentRepository);

        // 3. Instanciamos el presentador inyectando el caso de uso (Capa Presentation)
        HandlerDashboardWindow dashboardPresenter = new HandlerDashboardWindow(manageStudentsUseCase);

        // 4. Construimos la interfaz gráfica en el hilo seguro de Swing (java.awt.EventQueue)
        java.awt.EventQueue.invokeLater(() -> {
            // Pasamos el presentador y el rol al constructor de tu ventana
            DashboardWindow dashboardWindow = new DashboardWindow(dashboardPresenter, userRole);
            
            // El presentador evalúa el rol para ver si restringe o no la pantalla
            dashboardPresenter.evaluateAccessRole(userRole); 
            
            // El presentador solicita la carga inicial de los estudiantes a la tabla
            dashboardPresenter.requestStudentsLoad();
            
            // Finalmente, hacemos visible la aplicación
            dashboardWindow.setVisible(true);
        });
    }
}