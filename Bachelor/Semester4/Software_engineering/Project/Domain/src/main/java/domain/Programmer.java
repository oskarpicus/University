package domain;

import java.util.Set;

public class Programmer extends User {
    private Set<Bug> resolvedBugs;

    public Programmer(Long id, String username, String password, Set<Bug> resolvedBugs) {
        super(id, username, password);
        this.resolvedBugs = resolvedBugs;
    }

    public Programmer(){

    }

    /**
     * Method for adding a bug that was resolved by a {@code Programmer}
     * @param bug: {@code Bug}, the resolved {@code Bug}
     */
    public void addBug(Bug bug){
        resolvedBugs.add(bug);
    }

    /**
     * Method for retrieving all the resolved bugs of a {@code Programmer}
     * @return list of all the resolved bugs of a Programmer
     */
    public Set<Bug> getResolvedBugs() {
        return resolvedBugs;
    }

    /**
     * Setter for the collection of resolved bugs of a {@code Programmer}
     * @param resolvedBugs: Set of resolved bugs
     */
    public void setResolvedBugs(Set<Bug> resolvedBugs) {
        this.resolvedBugs = resolvedBugs;
    }

    /**
     * Basic method for adding a resolved bug. Does not keep the ends synchronised
     * @param bug: {@code Bug}, the resolved {@code Bug}
     */
    protected void basicAddBug(Bug bug){
        resolvedBugs.add(bug);
    }

    @Override
    public String toString() {
        return super.toString() + " Programmer";
    }
}
