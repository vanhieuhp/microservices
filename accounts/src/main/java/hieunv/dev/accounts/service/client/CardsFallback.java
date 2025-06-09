package hieunv.dev.accounts.service.client;

import hieunv.dev.accounts.dto.CardsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFallback implements CardsFeignClient {

    @Override
    public ResponseEntity<CardsDto> fetchCardsDetails(String mobileNumber, String correlationId) {
        return null;
    }
}
