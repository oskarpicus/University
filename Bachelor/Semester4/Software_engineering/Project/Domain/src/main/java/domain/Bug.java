package domain;

import java.io.Serializable;

public class Bug implements Entity<Long>, Serializable {
    private Long id;
    private String name;
    private String description;
    private Severity severity;
    private Status status;
    private Tester tester;
    private Programmer programmer;

    public Bug(Long id, String name, String description, Severity severity, Status status, Tester tester) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.severity = severity;
        this.status = status;
        this.tester = tester;
    }

    public Bug(){

    }

    /**
     * Getter for the name of a {@code Bug}
     * @return name: String, the name of the current Bug
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name of a {@code Bug}
     * @param name: String, the desired name
     * name of the current {@code Bug} is set to {@code name}
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the description of a {@code Bug}
     * @return description: String, the description of the current Bug
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for the description of a {@code Bug}
     * @param description: String, the desired description
     * description of the current {@code Bug} is set to {@code description}
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for the severity of a {@code Bug}
     * @return severity: Severity, the severity of the current Bug
     */
    public Severity getSeverity() {
        return severity;
    }

    /**
     * Setter for the severity of a {@code Bug}
     * @param severity: Severity, the desired Severity
     * severity of the current {@code Bug} is set to {@code severity}
     */
    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    /**
     * Getter for the status of a {@code Bug}
     * @return status: Status, the status of the current Bug
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Setter for the status of a {@code Bug}
     * @param status: Status, the desired status
     * status of the current {@code Bug} is set to {@code status}
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Getter for the tester that discovered a {@code Bug}
     * @return tester: Tester, the tester of the current Bug
     */
    public Tester getTester() {
        return tester;
    }

    /**
     * Setter for the tester of a {@code Bug}
     * @param tester: Tester, the desired tester
     * tester of the current {@code Bug} is set to {@code tester}
     */
    public void setTester(Tester tester) {
        this.tester = tester;
        tester.basicAddBug(this);
    }

    /**
     * Getter for the programmer that resolved the {@code Bug}
     * @return programmer: Programmer, the programmer of the current Bug
     */
    public Programmer getProgrammer() {
        return programmer;
    }

    /**
     * Setter for the programmer of a {@code Bug}
     * @param programmer: Programmer, the desired programmer
     * programmer of the current {@code Bug} is set to {@code programmer}
     */
    public void setProgrammer(Programmer programmer) {
        this.programmer = programmer;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long aLong) {
        this.id = aLong;
    }

    @Override
    public String toString() {
        return getId()+" "+getName()+" "+getDescription()+" "+getStatus()+" "+getSeverity();
    }
}
