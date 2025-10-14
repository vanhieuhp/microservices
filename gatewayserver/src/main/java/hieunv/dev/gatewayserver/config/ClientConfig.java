package hieunv.dev.gatewayserver.config;

import hieunv.dev.gatewayserver.service.client.CustomerSummaryClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;

@Configuration
public class ClientConfig {

    @Value("${app.base-url}")
    private String baseUrl;

    @Bean
    CustomerSummaryClient customerClient() {
        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .filter(correlationIdFilter())
                .build();
        WebClientAdapter adapter = WebClientAdapter.create(webClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(CustomerSummaryClient.class);
    }

    private ExchangeFilterFunction correlationIdFilter() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> 
            Mono.deferContextual(contextView -> {
                String correlationId = contextView.getOrDefault("easybank-correlation-id", "default-correlation-id");
                ClientRequest filtered = ClientRequest.from(clientRequest)
                        .header("easybank-correlation-id", correlationId)
                        .build();
                return Mono.just(filtered);
            })
        );
    }
}
