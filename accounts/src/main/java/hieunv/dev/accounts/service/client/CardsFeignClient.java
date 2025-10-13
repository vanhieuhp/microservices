package hieunv.dev.accounts.service.client;

import hieunv.dev.accounts.dto.CardsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

// Option 1: Use Eureka service discovery (recommended)
@FeignClient(name = "cards", fallback = CardsFallback.class)
// Option 2: If you need hardcoded URL, use lowercase and correct hostname
// @FeignClient(name = "cards", url = "http://cards:8002", fallback = CardsFallback.class)
public interface CardsFeignClient {

    @GetMapping(value = "/api/fetch", consumes = "application/json")
    ResponseEntity<CardsDto> fetchCardsDetails(@RequestParam String mobileNumber,
                                               @RequestHeader(name = "easybank-correlation-id") String correlationId);
}
