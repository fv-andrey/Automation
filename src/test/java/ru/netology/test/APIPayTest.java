package ru.netology.test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.APIHelper.*;
import static ru.netology.data.DBHelper.*;
import static ru.netology.data.DataHelper.*;

public class APIPayTest {

    @Test
    public void payApprovedCardAPI() {
        String status = getStatus(getCardInfo(approvedCard(),
                        checkYear(0), checkMonth(0), validOwner(), cvv()),
                "/pay");

        assertEquals(status().getApproved(), status);
        assertEquals(status, getStatusPay());
    }

}
/**

    @Test
    public void payDeclinedCardAPI() {
        String status = getStatus(getCardInfo(declinedCard(),
                        checkYear(0), checkMonth(0), validOwner(), cvv()),
                "/pay");

        assertEquals(status().getDeclined(), status);
        assertEquals(status, getStatusPay());
    }

    @Test
    public void invalidCardNumberOneLessAPI() {
        int initCount = getCountOrder();
        int statusCode = 400;
        payRequest(getCardInfo(invalidNumber(generateValue("en").numerify("#### #### #### ###")),
                        checkYear(0), checkMonth(0), validOwner(), cvv()),
                statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidCardNumberOneMoreAPI() {
        int initCount = getCountOrder();
        int statusCode = 400;
        payRequest(getCardInfo(invalidNumber(generateValue("en").numerify("#### #### #### #### #")),
                        checkYear(0), checkMonth(0), validOwner(), cvv()),
                statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void randomCardAPI() {
        int initCount = getCountOrder();
        int statusCode = 400;
        payRequest(getCardInfo(randomNumber(), checkYear(0), checkMonth(0), validOwner(), cvv()),
                statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidCardNumberSymbolAPI() {
        int initCount = getCountOrder();
        int statusCode = 400;
        payRequest(getCardInfo(invalidNumber("@#$%&*volf"), checkYear(0), checkMonth(0), validOwner(), cvv()),
                statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void lastMonthAPI() {
        int initCount = getCountOrder();
        int statusCode = 400;
        payRequest(getCardInfo(approvedCard(), checkYear(0), checkMonth(-1), validOwner(), cvv()),
                statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidMonth00API() {
        int initCount = getCountOrder();
        int statusCode = 400;
        payRequest(getCardInfo(approvedCard(), checkYear(0), invalidMonth("00"), validOwner(), cvv()),
                statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidMonth13API() {
        int initCount = getCountOrder();
        int statusCode = 400;
        payRequest(getCardInfo(approvedCard(), checkYear(0), invalidMonth("13"), validOwner(), cvv()),
                statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidMonthOfOneNumberAPI() {
        int initCount = getCountOrder();
        int statusCode = 400;
        payRequest(getCardInfo(approvedCard(), checkYear(0), invalidMonth("1"), validOwner(), cvv()),
                statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidMonthOfThreeNumber() {
        int initCount = getCountOrder();
        int statusCode = 400;
        payRequest(getCardInfo(approvedCard(), checkYear(0), invalidMonth("123"), validOwner(), cvv()),
                statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidMonthOfSymbolAndLatinAPI() {
        int initCount = getCountOrder();
        int statusCode = 400;
        payRequest(getCardInfo(approvedCard(), checkYear(0), invalidMonth("@#$%&*volf"), validOwner(), cvv()),
                statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void lastYearAPI() {
        int initCount = getCountOrder();
        int statusCode = 400;
        payRequest(getCardInfo(approvedCard(), checkYear(-1), checkMonth(0), validOwner(), cvv()),
                statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void nextSixYearApi() {
        int initCount = getCountOrder();
        int statusCode = 400;
        payRequest(getCardInfo(approvedCard(), checkYear(6), checkMonth(0), validOwner(), cvv()),
                statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidYearOfOneNumber() {
        int initCount = getCountOrder();
        int statusCode = 400;
        payRequest(getCardInfo(approvedCard(), invalidYear("1"), checkMonth(0), validOwner(), cvv()),
                statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidYearOfThreeNumberAPI() {
        int initCount = getCountOrder();
        int statusCode = 400;
        payRequest(getCardInfo(approvedCard(), invalidYear("123"), checkMonth(0), validOwner(), cvv()),
                statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidYearOfSymbolAndLatinAPI() {
        int initCount = getCountOrder();
        int statusCode = 400;
        payRequest(getCardInfo(approvedCard(), invalidYear("@#$%&*volf"), checkMonth(0), validOwner(), cvv()),
                statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidHolderRuAPI() {
        int initCount = getCountOrder();
        int statusCode = 400;
        payRequest(getCardInfo(approvedCard(), checkYear(0), checkMonth(0), ownerRu(), cvv()),
                statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidHolderOfNumberAPI() {
        int initCount = getCountOrder();
        int statusCode = 400;
        payRequest(getCardInfo(approvedCard(), checkYear(0), checkMonth(0),
                        invalidOwner(generateValue("en").numerify("###")), cvv()),
                statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidHolderOfLength70API() {
        int initCount = getCountOrder();
        int statusCode = 400;
        payRequest(getCardInfo(approvedCard(), checkYear(0), checkMonth(0),
                        invalidOwner(generateValue("en").letterify("?").repeat(70)), cvv()),
                statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidCVCLessOneAPI() {
        int initCount = getCountOrder();
        int statusCode = 400;
        payRequest(getCardInfo(approvedCard(), checkYear(0), checkMonth(0), validOwner(),
                        invalidCVV(generateValue("en").numerify("##"))),
                statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidCVCMoreOneAPI() {
        int initCount = getCountOrder();
        int statusCode = 400;
        payRequest(getCardInfo(approvedCard(), checkYear(0), checkMonth(0), validOwner(),
                        invalidCVV(generateValue("en").numerify("####"))),
                statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidCVCOfSymbolAndLatinApi() {
        int initCount = getCountOrder();
        int statusCode = 400;
        payRequest(getCardInfo(approvedCard(), checkYear(0), checkMonth(0), validOwner(),
                        invalidCVV("@#$%&*volf")),
                statusCode);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void emptyValue() {
        int initCount = getCountOrder();
        int statusCode = 400;
        payRequest(getCardInfo(invalidNumber(""), invalidYear(""), invalidMonth(""), invalidOwner(""),
                        invalidCVV("")),
                statusCode);
        assertEquals(initCount, getCountOrder());
    }
}
 */

