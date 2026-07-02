package domain.model;

public class Student {
    private int id;
    private String lastname;
    private String name;
    private String ci;
    private String grade;

    private static int nextId = 1;

    public Student(String lastname, String name, String ci, String grade) {
        this.id = nextId++;
        this.lastname = lastname;
        this.name = name;
        this.ci = ci;
        this.grade = grade;
    }

  
    public Student(int id, String lastname, String name, String ci, String grade) {
        this.id = id;
        this.lastname = lastname;
        this.name = name;
        this.ci = ci;
        this.grade = grade;
    }

    public int getId() {
        return id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
