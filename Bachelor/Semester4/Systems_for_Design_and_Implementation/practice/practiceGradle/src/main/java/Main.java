import domain.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class Main {
    private static Logger logger = LogManager.getLogger();
    private static SessionFactory sessionFactory;

    static {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try{
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            System.out.println("Successfully created session factory");
        }catch (Exception e){
            System.out.println("Failed to create session factory");
            System.out.println(e.getMessage());
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:Spring.xml");
        logger.trace("kk");
//        Properties properties = new Properties();
//        try {
//            properties.load(Main.class.getClassLoader().getResourceAsStream("database.properties"));
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            logger.error(e);
//        }
        Properties properties = (Properties)context.getBean("properties");
        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM employees;")) {
                statement.execute();
                try (ResultSet resultSet = statement.getResultSet()) {
                    while (resultSet.next()) {
                        System.out.println(resultSet.getString("name"));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error(e);
        }

        Test test = context.getBean(Test.class);
        test.print();
        Employee employee = testH();
        System.out.println(employee);
    }

    private static Employee testH() {
//        domain.Employee employee = new domain.Employee();
//        employee.setId(1L);
//        employee.setName("Ion Pop");
//        domain.Skill skill = new domain.Skill();
//        skill.setId(1L);
//        skill.setName("adaptability");
//        Set<domain.Skill> skills = new HashSet<>(); skills.add(skill);
//        Set<domain.Employee> employees = new HashSet<>(); employees.add(employee);
//        employee.setSkills(skills);
//        skill.setEmployees(employees);
//
//        skill = new domain.Skill();
//        skill.setId(2L);
//        skill.setName("hard-working");
//
//        skills.add(skill);
//
//        try (Session session = sessionFactory.openSession()) {
//            Transaction transaction = session.beginTransaction();
//            //session.save(skill);
//            session.update(employee);
//            transaction.commit();
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
        Employee employee = null;
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            employee = (Employee) session.createQuery("from Employee where id=:id")
                    .setParameter("id", 1L)
                    .setMaxResults(1)
                    .uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employee;
    }
}
