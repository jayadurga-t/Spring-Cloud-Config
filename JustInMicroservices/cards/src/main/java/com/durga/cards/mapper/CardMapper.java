package com.durga.cards.mapper;

import com.durga.cards.dto.CardDto;
import com.durga.cards.entity.Card;

public class CardMapper {

    public static Card mapToCard(Card card, CardDto cardDto){
        card.setMobileNumber(cardDto.getMobileNumber());
        card.setCardNumber(cardDto.getCardNumber());
        card.setCardType(cardDto.getCardType());
        card.setTotalLimit(cardDto.getTotalLimit());
        card.setAmountUsed(cardDto.getAmountUsed());
        card.setAvailableAmount(cardDto.getAvailableAmount());
        return card;
    }

    public static CardDto mapToCardDto(CardDto cardDto, Card card){
        cardDto.setMobileNumber(card.getMobileNumber());
        cardDto.setCardNumber(card.getCardNumber());
        cardDto.setCardType(card.getCardType());
        cardDto.setTotalLimit(card.getTotalLimit());
        cardDto.setAmountUsed(card.getAmountUsed());
        cardDto.setAvailableAmount(card.getAvailableAmount());
        return cardDto;
    }
}
