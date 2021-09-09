package domain;

import java.util.HashSet;
import java.util.Set;

public class Employee {
    private Long id;
    private String name;
    private Set<Skill> skills;

    public Employee() {
        skills = new HashSet<>();
    }

    public Employee(Long id, String name, Set<Skill> skills) {
        this.id = id;
        this.name = name;
        this.skills = skills;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getName() + " " + getSkills();
    }
}
