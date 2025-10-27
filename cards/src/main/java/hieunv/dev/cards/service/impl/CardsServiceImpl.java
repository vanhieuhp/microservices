package hieunv.dev.cards.service.impl;

import hieunv.dev.cards.constants.CardsConstants;
import hieunv.dev.cards.dto.CardsDto;
import hieunv.dev.cards.entity.Cards;
import hieunv.dev.cards.exception.CardAlreadyExistsException;
import hieunv.dev.cards.exception.ResourceNotFoundException;
import hieunv.dev.cards.mapper.CardsMapper;
import hieunv.dev.cards.repository.CardsRepository;
import hieunv.dev.cards.service.ICardsService;
import hieunv.dev.commonlib.dto.MobileNumberUpdate;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {

    private CardsRepository cardsRepository;
    private final Random random = new Random();
    private final StreamBridge streamBridge;
    /**
     * Generates a unique 16-digit card number
     */
    private Long generateCardNumber() {
        Long cardNumber;
        do {
            // Generate a 16-digit card number
            cardNumber = 1000000000000000L + random.nextLong(9000000000000000L);
        } while (cardsRepository.existsById(cardNumber));
        return cardNumber;
    }

    @Override
    public CardsDto createCard(Cards card) {
        Optional<Cards> optionalCard = cardsRepository.findByMobileNumberAndActiveSw(card.getMobileNumber(),
                CardsConstants.ACTIVE_SW);
        if (optionalCard.isPresent()) {
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber " + card.getMobileNumber());
        }

        // Generate unique card number if not provided
        if (card.getCardNumber() == null) {
            card.setCardNumber(generateCardNumber());
        }

        card.setActiveSw(CardsConstants.ACTIVE_SW);
        Cards savedCard = cardsRepository.save(card);
        return CardsMapper.mapToCardsDto(savedCard, new CardsDto());
    }

    @Override
    public CardsDto fetchCard(String mobileNumber) {
        Cards card = cardsRepository.findByMobileNumberAndActiveSw(mobileNumber, CardsConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
                );
        return CardsMapper.mapToCardsDto(card, new CardsDto());
    }

    @Override
    public CardsDto fetchCardById(Long cardNumber) {
        Cards card = cardsRepository.findByCardNumberAndActiveSw(cardNumber, CardsConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "cardNumber", cardNumber.toString()));
        return CardsMapper.mapToCardsDto(card, new CardsDto());
    }

    @Override
    public boolean updateCard(CardsDto cardsDto) {
        Cards card = cardsRepository.findByCardNumberAndActiveSw(cardsDto.getCardNumber(), CardsConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "cardNumber", cardsDto.getCardNumber().toString()));

        CardsMapper.mapToCards(cardsDto, card);
        cardsRepository.save(card);
        return true;
    }


    @Override
    public boolean deleteCard(Long cardNumber) {
        Cards card = cardsRepository.findById(cardNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "cardNumber", cardNumber.toString())
                );
        card.setActiveSw(CardsConstants.IN_ACTIVE_SW);
        cardsRepository.save(card);
        return true;
    }

    @Transactional
    @Override
    public boolean updateMobileNumber(MobileNumberUpdate mobileNumberUpdate) {
        boolean result = false;
        try {
            Cards card = cardsRepository.findByMobileNumberAndActiveSw(mobileNumberUpdate.getCurrentMobileNumber(), CardsConstants.ACTIVE_SW)
                    .orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumberUpdate.getCurrentMobileNumber()));
            card.setMobileNumber(mobileNumberUpdate.getNewMobileNumber());
            updateLoanMobileNumber(mobileNumberUpdate);
            cardsRepository.save(card);
            result = true;
        } catch (Exception e) {
            log.error("Error updating mobile number for Cards: {}", e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            rollbackAccountMobileNumber(mobileNumberUpdate);
        }
        return result;
    }

    @Override
    public boolean rollbackCardMobileNumber(MobileNumberUpdate mobileNumberUpdate) {
        String newMobileNumber = mobileNumberUpdate.getNewMobileNumber();
        Cards account = cardsRepository.findByMobileNumberAndActiveSw(newMobileNumber, CardsConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", newMobileNumber));
        account.setMobileNumber(mobileNumberUpdate.getCurrentMobileNumber());
        cardsRepository.save(account);
        rollbackAccountMobileNumber(mobileNumberUpdate);
        return true;
    }

    public void updateLoanMobileNumber(MobileNumberUpdate mobileNumberUpdate) {
        log.info("Sending updateLoanMobileNumber request for the details: {}", mobileNumberUpdate);
        var result = streamBridge.send("updateLoanMobileNumber-out-0", mobileNumberUpdate);
        log.info("Is the updateLoanMobileNumber request sent successfully? : {}", result);
    }

    public void rollbackAccountMobileNumber(MobileNumberUpdate mobileNumberUpdate) {
        log.info("Sending rollbackAccountMobileNumber request for the details: {}", mobileNumberUpdate);
        var result = streamBridge.send("rollbackAccountMobileNumber-out-0", mobileNumberUpdate);
        log.info("Is the rollbackAccountMobileNumber request sent successfully? : {}", result);
    }
}
