package domain.model;

public class Student {
    private String lastname;
    private String name;
    private String ci;
    private String grade;

    public Student(String lastname, String name, String ci, String grade) {
        this.lastname = lastname;
        this.name = name;
        this.ci = ci;
        this.grade = grade;
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
