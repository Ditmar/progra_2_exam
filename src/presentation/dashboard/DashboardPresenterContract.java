package presentation.dashboard;

public interface DashboardPresenterContract {
    void onCargarEstudiantesClicked();
    void onGuardarClicked(String lastname, String name, String ci, String gradeStr);
    void onActualizarClicked(int id, String lastname, String name, String ci, String gradeStr);
    void onEliminarClicked(int id);
}
