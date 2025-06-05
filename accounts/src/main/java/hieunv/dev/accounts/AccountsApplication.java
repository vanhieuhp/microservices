package hieunv.dev.accounts;

import hieunv.dev.accounts.dto.AccountsContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@OpenAPIDefinition(
        info = @Info(
                title = "Accounts microservices REST API Documentation",
                description = "EazyBank Accounts microservices REST API Documentation",
                version = "v1",
                contact = @Contact(
                        name = "vanhieuhp",
                        email = "vanhieuit10@gmail.com",
                        url = "https://github.com/vanhieuhp"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "EasyBank Accounts microservice REST API Documentation",
                url = "http:8001/swagger-ui.html"
        )
)
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@SpringBootApplication
@ComponentScans({
        @ComponentScan("hieunv.dev.accounts.controller"),
        @ComponentScan("hieunv.dev.accounts.service"),
        @ComponentScan("hieunv.dev.accounts.exception"),
        @ComponentScan("hieunv.dev.accounts.audit"),
        @ComponentScan("hieunv.dev.accounts.config"),
})
@EnableJpaRepositories({"hieunv.dev.accounts.repository"})
@EntityScan({"hieunv.dev.accounts.entity"})
@EnableConfigurationProperties(value={AccountsContactInfoDto.class})
public class AccountsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountsApplication.class, args);
    }

}
