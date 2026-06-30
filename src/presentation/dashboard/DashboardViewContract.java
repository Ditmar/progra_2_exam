package presentation.dashboard;

import java.util.ArrayList;

import domain.model.Student;

public interface DashboardViewContract {
    void mostrarEstudiantes(ArrayList<Student> estudiantes);
    void mostrarError(String mensaje);
    void mostrarExito(String mensaje);
    void limpiarFormulario();
}
