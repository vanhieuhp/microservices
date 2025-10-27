package hieunv.dev.cards.function;

import hieunv.dev.cards.service.ICardsService;
import hieunv.dev.commonlib.dto.MobileNumberUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class CardFunction {

    @Bean
    public Consumer<MobileNumberUpdate> updateCardMobileNumber(ICardsService cardsService) {
        return (mobileNumberUpdate) -> {
            log.info("Received updateCardMobileNumber event: {}", mobileNumberUpdate);
            cardsService.updateMobileNumber(mobileNumberUpdate);
        };
    }

    @Bean
    public Consumer<MobileNumberUpdate> rollbackCardMobileNumber(ICardsService cardsService) {
        return (mobileNumberUpdate) -> {
            log.info("Received rollbackCardMobileNumber event: {}", mobileNumberUpdate);
            cardsService.rollbackCardMobileNumber(mobileNumberUpdate);
        };
    }


}
