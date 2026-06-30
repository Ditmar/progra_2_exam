package presentation.dashboard;

import domain.model.Student;
import domain.usecase.StudentUseCase;
import java.util.ArrayList;
public class HandlerDashboardWindow implements DashboardPresenterContract {

    private final StudentUseCase studentUseCase;
    private DashboardViewContract view;

    // 1. Recibimos el caso de uso por constructor (Igual que el docente)
    public HandlerDashboardWindow(StudentUseCase studentUseCase) {
        this.studentUseCase = studentUseCase;
    }

    // 2. Método para enlazar la vista al presentador
    public void attach(DashboardWindow view) {
        this.view = (DashboardViewContract) view;
    }

    @Override
    public void onCargarEstudiantesClicked() {
        try {
            // Obtenemos la lista desde el dominio
            ArrayList<Student> lista = (ArrayList<Student>) studentUseCase.getAll();
            // Le ordenamos a la vista que los muestre
            this.view.mostrarEstudiantes(lista);
        } catch (Exception e) {
            this.view.mostrarError("Error al cargar estudiantes: " + e.getMessage());
        }
    }

    @Override
    public void onGuardarClicked(String lastname, String name, String ci, String gradeStr) {
        try {
            // Convertimos la nota de texto a entero antes de mandarla al caso de uso
            int grade = Integer.parseInt(gradeStr.trim());
            
            // Ejecutamos la lógica de negocio en el caso de uso
            studentUseCase.create(lastname, name, ci, grade);
            
            this.view.mostrarExito("Estudiante registrado exitosamente.");
            this.view.limpiarFormulario();
            
            // Recargamos la tabla automáticamente
            onCargarEstudiantesClicked();
        } catch (NumberFormatException e) {
            this.view.mostrarError("La nota debe ser un número entero válido.");
        } catch (IllegalArgumentException e) {
            // Atrapamos los errores de validación de negocio (igual que hace el login)
            this.view.mostrarError(e.getMessage());
        }
    }

    @Override
    public void onActualizarClicked(int id, String lastname, String name, String ci, String gradeStr) {
        try {
            int grade = Integer.parseInt(gradeStr.trim());
            
            studentUseCase.update(id, lastname, name, ci, grade);
            
            this.view.mostrarExito("Estudiante actualizado correctamente.");
            this.view.limpiarFormulario();
            onCargarEstudiantesClicked();
        } catch (NumberFormatException e) {
            this.view.mostrarError("La nota debe ser un número entero válido.");
        } catch (IllegalArgumentException e) {
            this.view.mostrarError(e.getMessage());
        }
    }

    @Override
    public void onEliminarClicked(int id) {
        try {
            studentUseCase.delete(id);
            this.view.mostrarExito("Estudiante eliminado correctamente.");
            this.view.limpiarFormulario();
            onCargarEstudiantesClicked();
        } catch (Exception e) {
            this.view.mostrarError("No se pudo eliminar: " + e.getMessage());
        }
    }
}
