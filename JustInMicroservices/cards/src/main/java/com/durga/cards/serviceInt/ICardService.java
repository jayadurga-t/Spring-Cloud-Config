package com.durga.cards.serviceInt;

import com.durga.cards.dto.CardDto;

public interface ICardService {

    void createCard(String mobileNumber);

    CardDto fetchCard(String mobileNumber);

    boolean updateCard(CardDto cardDto);

    boolean deleteCard(String mobileNumber);
}
