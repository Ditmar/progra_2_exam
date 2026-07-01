package domain.model;

public class Student {
    private final String lastname;
    private final String name;
    private final String ci;
    private final String grade;

    public Student(String lastname, String name, String ci, String grade) {
        this.lastname = lastname;
        this.name = name;
        this.ci = ci;
        this.grade = grade;
    }

    public String getLastname() {
        return lastname;
    }

    public String getName() {
        return name;
    }

    public String getCi() {
        return ci;
    }

    public String getGrade() {
        return grade;
    }
}
