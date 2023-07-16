package ru.netology.test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.APIHelper.*;
import static ru.netology.data.DBHelper.*;
import static ru.netology.data.DataHelper.*;

public class APICreditTest {

    String path = "/credit";

    @Test
    public void payApprovedCardAPI() {
        int statusCode = 200;
        String key = "status";
        String response = response(getCardInfo(approvedCard(),
                        checkYear(0), checkMonth(0), validOwner(), cvv()),
                path, statusCode, key);

        assertEquals(status().getApproved(), response);
        assertEquals(response, getStatusCredit());
        assertEquals(getBankId(), getCreditId());
    }

    @Test
    public void payDeclinedCardAPI() {
        int statusCode = 200;
        String key = "status";
        String response = response(getCardInfo(declinedCard(),
                        checkYear(0), checkMonth(0), validOwner(), cvv()),
                path, statusCode, key);

        assertEquals(status().getDeclined(), response);
        assertEquals(response, getStatusCredit());
    }

    @Test
    public void invalidCardNumberOneLessAPI() {
        int initCount = getCountOrder();
        int statusCode = 400;
        String key = "error";
        String error = "Invalid card number";
        String response = response(getCardInfo(invalidNumber(generateValue("en").numerify("#### #### #### ###")),
                        checkYear(0), checkMonth(0), validOwner(), cvv()),
                path, statusCode, key);

        assertEquals(error, response);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidCardNumberOneMoreAPI() {
        int initCount = getCountOrder();
        int statusCode = 400;
        String key = "error";
        String error = "Invalid card number";
        String response = response(getCardInfo(invalidNumber(generateValue("en").numerify("#### #### #### #### #")),
                        checkYear(0), checkMonth(0), validOwner(), cvv()),
                path, statusCode, key);

        assertEquals(error, response);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void randomCardAPI() {
        int initCount = getCountOrder();
        int statusCode = 400;
        String key = "error";
        String error = "Invalid card number";
        String response = response(getCardInfo(randomNumber(),
                        checkYear(0), checkMonth(0), validOwner(), cvv()),
                path, statusCode, key);

        assertEquals(error, response);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidCardNumberSymbolAPI() {
        int initCount = getCountOrder();
        int statusCode = 400;
        String key = "error";
        String error = "Invalid card number";
        String response = response(getCardInfo(invalidNumber(symbol()),
                        checkYear(0), checkMonth(0), validOwner(), cvv()),
                path, statusCode, key);

        assertEquals(error, response);
        assertEquals(initCount, getCountOrder());
    }
}


