package service;

import domain.Bug;
import domain.Severity;
import domain.Tester;
import domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.Observer;
import services.Service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MasterService implements Service {
    private final UserService userService;
    private final BugService bugService;
    private final static Logger logger = LogManager.getLogger();
    private final List<Observer> observers = new ArrayList<>();

    public MasterService(UserService userService, BugService bugService) {
        this.userService = userService;
        this.bugService = bugService;
    }

    @Override
    public User findUser(String username, String password) {
        logger.traceEntry("Entry Find User {} {}", username, password);
        Optional<User> resultFind = userService.find(username, password);
        User result = resultFind.orElse(null);
        logger.traceExit("Exit Find User result {}", result);
        return result;
    }

    @Override
    public Bug addBug(Bug bug) {
        logger.traceEntry("Entry Add Bug {}", bug);
        Optional<Bug> resultAdd = bugService.add(bug);
        Bug result = resultAdd.orElse(null);
        if (result == null) {  // successfully added
            observers.forEach(observer -> {
                try {
                    observer.addedBug(bug);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    logger.error(e);
                }
            });
        }
        logger.traceExit("Exit Add Bug result {}", result);
        return result;
    }

    @Override
    public List<Bug> findBugsByTester(Tester tester) {
        logger.traceEntry("Entry Find Bugs By Tester {}", tester);
        var result = bugService.findBugsByTester(tester);
        logger.traceExit("Exit Find Bugs By Tester result {}", result);
        return result;
    }

    @Override
    public List<Bug> getAllBugs() {
        logger.traceEntry("Entry Get All Bugs");
        List<Bug> result = bugService.findAll();
        logger.traceExit("Exit Get All Bugs {}", result);
        return result;
    }

    @Override
    public void addObserver(Observer observer) {
        logger.traceEntry("Entry Add Observer {}", observer);
        observers.add(observer);
        logger.traceExit("Exit Add Observer");
    }

    @Override
    public void removeObserver(Observer observer) {
        logger.traceEntry("Entry Remove Observer {}", observer);
        observers.remove(observer);
        logger.traceExit("Exit Remove Observer");
    }

    @Override
    public List<Bug> findBugsBySeverity(Severity severity) {
        logger.traceEntry("Entry Find Bugs By Severity {}", severity);
        List<Bug> result = this.bugService.findBugsBySeverity(severity);
        logger.traceExit("Exit Find Bugs By Severity {}", result);
        return result;
    }

    @Override
    public Bug updateBug(Bug bug) {
        logger.traceEntry("Entry Update Bug {}", bug);
        Optional<Bug> resultUpdate = this.bugService.update(bug);
        if (resultUpdate.isEmpty()) {  // successfully updated
            observers.forEach(observer -> {
                try {
                    observer.updatedBug(bug);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    logger.error(e);
                }
            });
        }
        return resultUpdate.orElse(null);
    }
}