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
        String status = getStatus(getCardInfo(approvedCard(),
                        checkYear(0), checkMonth(0), validOwner(), cvv()),
                path);

        assertEquals(status().getApproved(), status);
        assertEquals(status, getStatusCredit());
    }

    @Test
    public void payDeclinedCardAPI() {
        String status = getStatus(getCardInfo(declinedCard(),
                        checkYear(0), checkMonth(0), validOwner(), cvv()),
                path);

        assertEquals(status().getDeclined(), status);
        assertEquals(status, getStatusCredit());
    }

    @Test
    public void invalidCardNumberOneLessAPI() {
        int initCount = getCountOrder();
        int statusCode = 400;
        request(getCardInfo(invalidNumber(generateValue("en").numerify("#### #### #### ###")),
                        checkYear(0), checkMonth(0), validOwner(), cvv()), path,
                statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidCardNumberOneMoreAPI() {
        int initCount = getCountOrder();
        int statusCode = 400;
        request(getCardInfo(invalidNumber(generateValue("en").numerify("#### #### #### #### #")),
                        checkYear(0), checkMonth(0), validOwner(), cvv()), path,
                statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void randomCardAPI() {
        int initCount = getCountOrder();
        int statusCode = 400;
        request(getCardInfo(randomNumber(), checkYear(0), checkMonth(0), validOwner(), cvv()),
                path, statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidCardNumberSymbolAPI() {
        int initCount = getCountOrder();
        int statusCode = 400;
        request(getCardInfo(invalidNumber("@#$%&*volf"), checkYear(0), checkMonth(0), validOwner(), cvv()),
                path, statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void lastMonthAPI() {
        int initCount = getCountOrder();
        int statusCode = 400;
        request(getCardInfo(approvedCard(), checkYear(0), checkMonth(-1), validOwner(), cvv()),
                path, statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidMonth00API() {
        int initCount = getCountOrder();
        int statusCode = 400;
        request(getCardInfo(approvedCard(), checkYear(0), invalidMonth("00"), validOwner(), cvv()),
                path, statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidMonth13API() {
        int initCount = getCountOrder();
        int statusCode = 400;
        request(getCardInfo(approvedCard(), checkYear(0), invalidMonth("13"), validOwner(), cvv()),
                path, statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidMonthOfOneNumberAPI() {
        int initCount = getCountOrder();
        int statusCode = 400;
        request(getCardInfo(approvedCard(), checkYear(0), invalidMonth("1"), validOwner(), cvv()),
                path, statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidMonthOfThreeNumber() {
        int initCount = getCountOrder();
        int statusCode = 400;
        request(getCardInfo(approvedCard(), checkYear(0), invalidMonth("123"), validOwner(), cvv()),
                path, statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidMonthOfSymbolAndLatinAPI() {
        int initCount = getCountOrder();
        int statusCode = 400;
        request(getCardInfo(approvedCard(), checkYear(0), invalidMonth("@#$%&*volf"), validOwner(), cvv()),
                path, statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void lastYearAPI() {
        int initCount = getCountOrder();
        int statusCode = 400;
        request(getCardInfo(approvedCard(), checkYear(-1), checkMonth(0), validOwner(), cvv()),
                path, statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void nextSixYearApi() {
        int initCount = getCountOrder();
        int statusCode = 400;
        request(getCardInfo(approvedCard(), checkYear(6), checkMonth(0), validOwner(), cvv()),
                path, statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidYearOfOneNumber() {
        int initCount = getCountOrder();
        int statusCode = 400;
        request(getCardInfo(approvedCard(), invalidYear("1"), checkMonth(0), validOwner(), cvv()),
                path, statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidYearOfThreeNumberAPI() {
        int initCount = getCountOrder();
        int statusCode = 400;
        request(getCardInfo(approvedCard(), invalidYear("123"), checkMonth(0), validOwner(), cvv()),
                path, statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidYearOfSymbolAndLatinAPI() {
        int initCount = getCountOrder();
        int statusCode = 400;
        request(getCardInfo(approvedCard(), invalidYear("@#$%&*volf"), checkMonth(0), validOwner(), cvv()),
                path, statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidHolderRuAPI() {
        int initCount = getCountOrder();
        int statusCode = 400;
        request(getCardInfo(approvedCard(), checkYear(0), checkMonth(0), ownerRu(), cvv()),
                path, statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidHolderOfNumberAPI() {
        int initCount = getCountOrder();
        int statusCode = 400;
        request(getCardInfo(approvedCard(), checkYear(0), checkMonth(0),
                        invalidOwner(generateValue("en").numerify("###")), cvv()),
                path, statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidHolderOfLength70API() {
        int initCount = getCountOrder();
        int statusCode = 400;
        request(getCardInfo(approvedCard(), checkYear(0), checkMonth(0),
                        invalidOwner(generateValue("en").letterify("?").repeat(70)), cvv()),
                path, statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidCVCLessOneAPI() {
        int initCount = getCountOrder();
        int statusCode = 400;
        request(getCardInfo(approvedCard(), checkYear(0), checkMonth(0), validOwner(),
                        invalidCVV(generateValue("en").numerify("##"))),
                path, statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidCVCMoreOneAPI() {
        int initCount = getCountOrder();
        int statusCode = 400;
        request(getCardInfo(approvedCard(), checkYear(0), checkMonth(0), validOwner(),
                        invalidCVV(generateValue("en").numerify("####"))),
                path, statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidCVCOfSymbolAndLatinApi() {
        int initCount = getCountOrder();
        int statusCode = 400;
        request(getCardInfo(approvedCard(), checkYear(0), checkMonth(0), validOwner(),
                        invalidCVV("@#$%&*volf")),
                path, statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void emptyValue() {
        int initCount = getCountOrder();
        int statusCode = 400;
        request(getCardInfo(invalidNumber(""), invalidYear(""), invalidMonth(""), invalidOwner(""),
                        invalidCVV("")),
                path, statusCode);
        assertEquals(initCount, getCountOrder());
    }
}


