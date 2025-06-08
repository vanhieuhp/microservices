package hieunv.dev.gatewayserver.filter;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

@Log4j2
@Configuration
public class ResponseTraceFilter {

    @Autowired
    private FilterUtility filterUtility;

    @Bean
    public GlobalFilter globalFilter() {
        return (exchange, chain) -> {
            return chain
                    .filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
                        String correlationId = filterUtility.getCorrelationId(requestHeaders);
                        log.debug("Updated the correlation id to the outbound headers: {}", correlationId);
                        exchange.getResponse().getHeaders().add(filterUtility.CORRELATION_ID, correlationId);
                    }));
        };
    }
}
