package domain;

import java.util.Set;

public class Tester extends User {
    private Set<Bug> foundBugs;

    public Tester(Long id, String username, String password, Set<Bug> foundBugs) {
        super(id, username, password);
        this.foundBugs = foundBugs;
    }

    public Tester(){

    }

    /**
     * Method for adding a bug to the collection of foundBugs
     * @param bug: {@code Bug}, the bug that was discovered
     */
    public void addBug(Bug bug){
        foundBugs.add(bug);
    }

    /**
     * Method for retrieving all the bugs that were found by a {@code Tester}
     * @return collection of the found bugs of the current {@code Tester}
     */
    public Set<Bug> getFoundBugs() {
        return foundBugs;
    }

    /**
     * Setter for the collection of found bugs by a {@code Tester}
     * @param foundBugs: Set of found bugs
     */
    public void setFoundBugs(Set<Bug> foundBugs) {
        this.foundBugs = foundBugs;
    }

    /**
     * Basic method for adding a bug to the collection of foundBugs. Does not keep the ends synchronized
     * @param bug: {@code Bug}, the bug that was discovered
     */
    protected void basicAddBug(Bug bug){
        foundBugs.add(bug);
    }

    @Override
    public String toString() {
        return super.toString() + " Tester";
    }
}
