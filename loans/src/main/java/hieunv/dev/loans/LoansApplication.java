package hieunv.dev.loans;

import hieunv.dev.loans.dto.LoansContactInfoDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {LoansContactInfoDto.class})
@EnableDiscoveryClient
@EntityScan({
        "hieunv.dev.loans.*",
        "org.axonframework.eventhandling.tokenstore.jpa",  // Axon JPA Token Store entities
        "org.axonframework.modelling.saga.repository.jpa"  // Axon JPA Saga Store entities
})
public class LoansApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoansApplication.class, args);
    }
}
