package com.durga.cards.serviceImpl;

import com.durga.cards.constants.CardConstants;
import com.durga.cards.dto.CardDto;
import com.durga.cards.entity.Card;
import com.durga.cards.exception.CardAlreadyExistsException;
import com.durga.cards.exception.ResourceNotFoundException;
import com.durga.cards.mapper.CardMapper;
import com.durga.cards.repository.CardRespository;
import com.durga.cards.serviceInt.ICardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardService implements ICardService {

    private CardRespository cardRespository;

    @Override
    public void createCard(String mobileNumber) {
        Optional<Card> card=cardRespository.findByMobileNumber(mobileNumber);
        if(card.isPresent()){
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber "+mobileNumber);
        }
        cardRespository.save(createNewCard(mobileNumber));
    }

    private Card createNewCard(String mobileNumber) {
        Card card=new Card();

        long randomcardNumber=100000000000L+new Random().nextInt(900000000);
        card.setCardNumber(Long.toString(randomcardNumber));
        card.setMobileNumber(mobileNumber);
        card.setCardType(CardConstants.CREDIT_CARD);
        card.setTotalLimit(CardConstants.NEW_CARD_LIMIT);
        card.setAmountUsed(0);
        card.setAvailableAmount(CardConstants.NEW_CARD_LIMIT);
        return card;
    }

    @Override
    public CardDto fetchCard(String mobileNumber) {
        Card card=cardRespository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "MobileNumber", mobileNumber)
        );
        return CardMapper.mapToCardDto(new CardDto(), card);
    }

    @Override
    public boolean updateCard(CardDto cardDto) {
        Card card=cardRespository.findByCardNumber(cardDto.getCardType()).orElseThrow(
                () -> new ResourceNotFoundException("Card", "CardNumber", cardDto.getCardNumber())
        );
        return true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        Card card=cardRespository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "MobileNumber", mobileNumber)
        );
        cardRespository.deleteById(card.getCardId());
        return true;
    }
}
