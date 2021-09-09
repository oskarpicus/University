import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = "rest_controllers")
public class RestServer {
    public static void main(String[] args) {
        SpringApplication.run(RestServer.class, args);
    }
}
