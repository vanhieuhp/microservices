package hieunv.dev.cards.service;

import hieunv.dev.cards.dto.CardsDto;
import hieunv.dev.cards.entity.Cards;
import hieunv.dev.commonlib.dto.MobileNumberUpdate;

public interface ICardsService {

    CardsDto createCard(Cards card);

    CardsDto fetchCard(String mobileNumber);

    CardsDto fetchCardById(Long cardNumber);

    boolean updateCard(CardsDto cardsDto);

    boolean deleteCard(Long cardNumber);

    boolean updateMobileNumber(MobileNumberUpdate mobileNumberUpdate);

    boolean rollbackCardMobileNumber(MobileNumberUpdate mobileNumberUpdate);
}
