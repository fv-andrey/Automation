package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.page.MainPage;
import ru.netology.page.PayPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DBHelper.*;
import static ru.netology.data.DataHelper.*;

public class PayTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
        open("http://localhost:8080");
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    public void payApprovedCard() {
        new MainPage()
                .toPayPage()
                .shouldByMsgSuccessPay(getCardInfo(getApprovedCard(),
                                getYear(0), getMonth(0), getValidOwner(), getCvv()));
        assertEquals(initCardStatus().getApproved(), getPaymentInfo().getStatus());
        assertEquals(getPaymentInfo().getTransaction_id(), getOrderInfo().getPayment_id());
        assertEquals(new PayPage().getAmountInfo(), getPaymentInfo().getAmount());
    }

    @Test
    public void payDeclinedCard() {
        new MainPage()
                .toPayPage()
                .shouldByMsgFailedPay(getCardInfo(getDeclinedCard(),
                                getYear(0), getMonth(0), getValidOwner(), getCvv()));
        assertEquals(initCardStatus().getDeclined(), getPaymentInfo().getStatus());
        assertEquals(new PayPage().getAmountInfo(), getPaymentInfo().getAmount());
    }

    @Test
    public void invalidCardNumberOneLess() {
        int initCount = getCountOrder();
        new MainPage()
                .toPayPage()
                .shouldBeMsgWrongFormat(getCardInfo(setInvalidValue(generateValue("en").numerify("#").repeat(15)),
                                getYear(0), getMonth(0), getValidOwner(), getCvv()));
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void randomCard() {
        int initCount = getCountOrder();
        new MainPage()
                .toPayPage()
                .shouldByMsgFailedPay(getCardInfo(getRandomCard(),
                                getYear(0), getMonth(0), getValidOwner(), getCvv()));
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void maxLengthNumberField() {
        int length = 19;
        assertEquals(length, new MainPage().toPayPage()
                .getLengthNumberField(generateValue("en").numerify("#").repeat(17)));
    }

    @Test
    public void inputSymbolAndLatinInNumberField() {
        int length = 0;
        assertEquals(length, new MainPage().toPayPage()
                .getLengthNumberField(getSymbolString()));
    }

    @Test
    public void lastMonth() {
        int initCount = getCountOrder();
        new MainPage()
                .toPayPage()
                .shouldBeMsgInvalidExpirationDate(getCardInfo(getApprovedCard(),
                                getYear(0), getMonth(-1), getValidOwner(), getCvv()));
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidMonth00() {
        int initCount = getCountOrder();
        new MainPage()
                .toPayPage()
                .shouldBeMsgInvalidExpirationDate(getCardInfo(getApprovedCard(),
                                getYear(0), setInvalidValue("00"), getValidOwner(), getCvv()));
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidMonth13() {
        int initCount = getCountOrder();
        new MainPage()
                .toPayPage()
                .shouldBeMsgInvalidExpirationDate(getCardInfo(getApprovedCard(),
                                getYear(0), setInvalidValue("13"), getValidOwner(), getCvv()));
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidMonthLengthIsOne() {
        int initCount = getCountOrder();
        new MainPage()
                .toPayPage()
                .shouldBeMsgWrongFormat(getCardInfo(getApprovedCard(),
                                getYear(0), setInvalidValue("1"), getValidOwner(), getCvv()));
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void lengthMonthField() {
        int length = 2;
        assertEquals(length, new MainPage().toPayPage()
                .getLengthMonthField(generateValue("en").numerify("###")));
    }

    @Test
    public void inputSymbolInMonthField() {
        int length = 0;
        assertEquals(length, new MainPage().toPayPage()
                .getLengthMonthField(getSymbolString()));
    }

    @Test
    public void lastYear() {
        int initCount = getCountOrder();
        new MainPage()
                .toPayPage()
                .shouldBeMsgCardExpired(getCardInfo(getApprovedCard(),
                                getYear(-1), getMonth(0), getValidOwner(), getCvv()));
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void nextSixYear() {
        int initCount = getCountOrder();
        new MainPage()
                .toPayPage()
                .shouldBeMsgInvalidExpirationDate(getCardInfo(getApprovedCard(),
                                getYear(6), getMonth(0), getValidOwner(), getCvv()));
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidYearLengthIsOne() {
        int initCount = getCountOrder();
        new MainPage()
                .toPayPage()
                .shouldBeMsgWrongFormat(getCardInfo(getApprovedCard(),
                                setInvalidValue("1"), getMonth(0), getValidOwner(), getCvv()));
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void lengthYearField() {
        int length = 2;
        assertEquals(length, new MainPage().toPayPage()
                .getLengthYearField(generateValue("en").numerify("###")));
    }

    @Test
    public void inputSymbolAndLatinInYearField() {
        int length = 0;
        assertEquals(length, new MainPage().toPayPage()
                .getLengthYearField(getSymbolString()));
    }

    @Test
    public void invalidOwnerOfRuSymbol() {
        int initCount = getCountOrder();
        new MainPage()
                .toPayPage()
                .shouldBeMsgWrongFormat(getCardInfo(getApprovedCard(),
                                getYear(0), getMonth(0), getOwnerOfRu(), getCvv()));
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidOwnerOfNumbers() {
        int initCount = getCountOrder();
        new MainPage()
                .toPayPage()
                .shouldBeMsgWrongFormat(getCardInfo(getApprovedCard(),
                                getYear(0), getMonth(0),
                                setInvalidValue(generateValue("ru").numerify("###")), getCvv()));
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidOwnerOfSymbol() {
        int initCount = getCountOrder();
        new MainPage()
                .toPayPage()
                .shouldBeMsgWrongFormat(getCardInfo(getApprovedCard(),
                                getYear(0), getMonth(0), setInvalidValue(getSymbolString()), getCvv()));
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidOwnerLengthIs70() {
        int initCount = getCountOrder();
        new MainPage()
                .toPayPage()
                .shouldBeMsgMaxSymbol(getCardInfo(getApprovedCard(),
                                getYear(0), getMonth(0),
                                setInvalidValue(generateValue("en").letterify("?").repeat(70)), getCvv()));
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidCVCLessOne() {
        int initCount = getCountOrder();
        new MainPage()
                .toPayPage()
                .shouldBeMsgWrongFormat(getCardInfo(getApprovedCard(),
                                getYear(0), getMonth(0), getValidOwner(),
                                setInvalidValue(generateValue("en").numerify("##"))));
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void lengthCVCOfSymbolAndLatin() {
        int length = 0;
        assertEquals(length, new MainPage().toPayPage()
                .getLengthCVCField(getSymbolString()));
    }

    @Test
    public void lengthCVCField() {
        int length = 3;
        assertEquals(length, new MainPage().toPayPage()
                .getLengthCVCField(generateValue("en").numerify("####")));
    }

    @Test
    public void emptyFields() {
        new MainPage()
                .toPayPage().shouldBeMsgEmptyFields();
    }

    @Test
    public void successMsgClose() {
        new MainPage()
                .toPayPage().
                successNotificationClose(getCardInfo(getApprovedCard(), getYear(0), getMonth(0),
                        getValidOwner(), getCvv()));
    }

    @Test
    public void failedMsgClose() {
        new MainPage()
                .toPayPage().
                failedNotificationClose(getCardInfo(getRandomCard(), getYear(0), getMonth(0),
                        getValidOwner(), getCvv()));
    }
}
