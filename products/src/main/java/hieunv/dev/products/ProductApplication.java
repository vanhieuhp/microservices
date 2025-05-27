package hieunv.dev.products;

import hieunv.dev.products.annotation.DeveloperInfo;
import hieunv.dev.products.annotation.Project;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ProductApplication {

    public static void main(String[] args) {

        Project project = new Project();

        System.out.println("HELLLO");

        for (var method: project.getClass().getDeclaredMethods()) {
            DeveloperInfo devInfo = method.getAnnotation(DeveloperInfo.class);
            System.out.println("Method: " + method.getName());
            System.out.println("Developer: " + devInfo.name());
            System.out.println("Date: " + devInfo.date());
            System.out.println("Description: " + devInfo.description());
            System.out.println("--------------------------");
        }

        project.implementBusinessLogic();
        SpringApplication.run(ProductApplication.class, args);

    }

}
