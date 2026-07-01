package domain.model;

public class Student {
    private int id; // Se agrega para la inconsistencia del delete
    private String lastname;
    private String name;
    private String ci;
    private String grade;

    // Actualiza tu constructor para que acepte el ID al inicio
    public Student(int id, String lastname, String name, String ci, String grade) {
        this.id = id;
        this.lastname = lastname;
        this.name = name;
        this.ci = ci;
        this.grade = grade;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getLastname() { return lastname; }
    public String getName() { return name; }
    public String getCi() { return ci; }
    public String getGrade() { return grade; }
}