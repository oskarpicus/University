package domain;

import java.util.Set;

public class Skill {
    private Long id;
    private String name;
    private Set<Employee> employees;

    public Skill() {
    }

    public Skill(Long id, String name, Set<Employee> employees) {
        this.id = id;
        this.name = name;
        this.employees = employees;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
