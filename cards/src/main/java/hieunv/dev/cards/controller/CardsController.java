package hieunv.dev.cards.controller;

import hieunv.dev.cards.constants.CardsConstants;
import hieunv.dev.cards.dto.CardsContactInfoDto;
import hieunv.dev.cards.dto.CardsDto;
import hieunv.dev.cards.dto.ResponseDto;
import hieunv.dev.cards.entity.Cards;
import hieunv.dev.cards.service.ICardsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, value = "/cards")
@AllArgsConstructor
@Validated
@Log4j2
public class CardsController {

    @Autowired
    private CardsContactInfoDto cardsContactInfoDto;
    
    private final ICardsService cardsService;

    @GetMapping("/contact-info")
    public ResponseEntity<CardsContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardsContactInfoDto);
    }

    @PostMapping("/create")
    public ResponseEntity<CardsDto> createCard(@Valid @RequestBody CardsDto cardsDto) {
        Cards card = new Cards();
        card.setCardNumber(cardsDto.getCardNumber()); // Will be generated if null
        card.setCardType(cardsDto.getCardType());
        card.setMobileNumber(cardsDto.getMobileNumber());
        card.setTotalLimit(cardsDto.getTotalLimit());
        card.setAmountUsed(cardsDto.getAmountUsed());
        card.setAvailableAmount(cardsDto.getAvailableAmount());
        
        CardsDto createdCard = cardsService.createCard(card);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdCard);
    }

    @GetMapping("/fetch")
    public ResponseEntity<CardsDto> fetchCard(@RequestParam
                                             @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                             String mobileNumber) {
        CardsDto cardsDto = cardsService.fetchCard(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardsDto);
    }

    @GetMapping("/fetch/{cardNumber}")
    public ResponseEntity<CardsDto> fetchCardById(@PathVariable Long cardNumber) {
        CardsDto cardsDto = cardsService.fetchCardById(cardNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardsDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCard(@Valid @RequestBody CardsDto cardsDto) {
        boolean isUpdated = cardsService.updateCard(cardsDto);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_UPDATE));
        }
    }

    @DeleteMapping("/delete/{cardNumber}")
    public ResponseEntity<ResponseDto> deleteCard(@PathVariable Long cardNumber) {
        boolean isDeleted = cardsService.deleteCard(cardNumber);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_DELETE));
        }
    }

}
