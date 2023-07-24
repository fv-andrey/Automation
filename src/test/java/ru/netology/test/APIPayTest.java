package ru.netology.test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.APIHelper.*;
import static ru.netology.data.DBHelper.*;
import static ru.netology.data.DataHelper.*;

public class APIPayTest {

    String path = "/pay";

    @Test
    public void payApprovedCardAPI() {
        int statusCode = 200;
        String key = "status";
        String response = response(getCardInfo(getApprovedCard(),
                        getYear(0), getMonth(0), getValidOwner(), getCvv()),
                path, statusCode, key);

        assertEquals(initCardStatus().getApproved(), response);
        assertEquals(response, getPaymentInfo().getStatus());
        assertEquals(getPaymentInfo().getTransaction_id(), getOrderInfo().getPayment_id());
    }

    @Test
    public void payDeclinedCardAPI() {
        int statusCode = 200;
        String key = "status";
        String response = response(getCardInfo(getDeclinedCard(),
                        getYear(0), getMonth(0), getValidOwner(), getCvv()),
                path, statusCode, key);

        assertEquals(initCardStatus().getDeclined(), response);
        assertEquals(response, getPaymentInfo().getStatus());
    }

    @Test
    public void invalidCardNumberOneLessAPI() {
        int initCount = getCountOrder();
        int statusCode = 400;
        String key = "error";
        String error = "Invalid card number";
        String response = response(getCardInfo(setInvalidValue(generateValue("en").numerify("#### #### #### ###")),
                        getYear(0), getMonth(0), getValidOwner(), getCvv()),
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
        String response = response(getCardInfo(setInvalidValue(generateValue("en").numerify("#### #### #### #### #")),
                        getYear(0), getMonth(0), getValidOwner(), getCvv()),
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
        String response = response(getCardInfo(getRandomCard(),
                        getYear(0), getMonth(0), getValidOwner(), getCvv()),
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
        String response = response(getCardInfo(setInvalidValue(getSymbolString()),
                        getYear(0), getMonth(0), getValidOwner(), getCvv()),
                path, statusCode, key);

        assertEquals(error, response);
        assertEquals(initCount, getCountOrder());
    }
}


