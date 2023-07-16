package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.page.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DBHelper.*;
import static ru.netology.data.DataHelper.*;
import static ru.netology.page.PayPage.*;

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
        String title = "Успешно";
        String content = "Операция одобрена Банком.";
        new MainPage()
                .toPayPage()
                .successPay(getCardInfo(approvedCard(),
                                checkYear(0), checkMonth(0), validOwner(), cvv()),
                        title, content);
        assertEquals(status().getApproved(), getStatusPay());
        assertEquals(getTransactionId(), getPaymentId());
        assertEquals(getAmountInfo(), getAmountPay());
    }

    @Test
    public void payDeclinedCard() {
        String title = "Ошибка";
        String content = "Ошибка! Банк отказал в проведении операции";
        new MainPage()
                .toPayPage()
                .failedPay(getCardInfo(declinedCard(),
                                checkYear(0), checkMonth(0), validOwner(), cvv()),
                        title, content);
        assertEquals(status().getDeclined(), getStatusPay());
        assertEquals(getAmountInfo(), getAmountPay());
    }

    @Test
    public void invalidCardNumberOneLess() {
        int initCount = getCountOrder();
        String msg = "Неверный формат";
        new MainPage()
                .toPayPage()
                .setInvalidValue(getCardInfo(invalidNumber(generateValue("en").numerify("#").repeat(15)),
                                checkYear(0), checkMonth(0), validOwner(), cvv()),
                        msg);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void randomCard() {
        int initCount = getCountOrder();
        String title = "Ошибка";
        String content = "Ошибка! Банк отказал в проведении операции";
        new MainPage()
                .toPayPage()
                .failedPay(getCardInfo(randomNumber(),
                                checkYear(0), checkMonth(0), validOwner(), cvv()),
                        title, content);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void maxLengthNumberField() {
        int length = 19;
        assertEquals(length, new MainPage().toPayPage()
                .lengthNumberField(generateValue("en").numerify("#").repeat(17)));
    }

    @Test
    public void inputSymbolAndLatinInNumberField() {
        int length = 0;
        assertEquals(length, new MainPage().toPayPage()
                .lengthNumberField(symbol()));
    }

    @Test
    public void lastMonth() {
        int initCount = getCountOrder();
        String msg = "Неверно указан срок действия карты";
        new MainPage()
                .toPayPage()
                .setInvalidValue(getCardInfo(approvedCard(),
                                checkYear(0), checkMonth(-1), validOwner(), cvv()),
                        msg);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidMonth00() {
        int initCount = getCountOrder();
        String msg = "Неверно указан срок действия карты";
        new MainPage()
                .toPayPage()
                .setInvalidValue(getCardInfo(approvedCard(),
                                checkYear(0), invalidMonth("00"), validOwner(), cvv()),
                        msg);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidMonth13() {
        int initCount = getCountOrder();
        String msg = "Неверно указан срок действия карты";
        new MainPage()
                .toPayPage()
                .setInvalidValue(getCardInfo(approvedCard(),
                                checkYear(0), invalidMonth("13"), validOwner(), cvv()),
                        msg);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidMonthLengthIsOne() {
        int initCount = getCountOrder();
        String msg = "Неверный формат";
        new MainPage()
                .toPayPage()
                .setInvalidValue(getCardInfo(approvedCard(),
                                checkYear(0), invalidMonth("1"), validOwner(), cvv()),
                        msg);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void lengthMonthField() {
        int length = 2;
        assertEquals(length, new MainPage().toPayPage()
                .lengthMonthField(generateValue("en").numerify("###")));
    }

    @Test
    public void inputSymbolInMonthField() {
        int length = 0;
        assertEquals(length, new MainPage().toPayPage()
                .lengthMonthField(symbol()));
    }

    @Test
    public void lastYear() {
        int initCount = getCountOrder();
        String msg = "Истёк срок действия карты";
        new MainPage()
                .toPayPage()
                .setInvalidValue(getCardInfo(approvedCard(),
                                checkYear(-1), checkMonth(0), validOwner(), cvv()),
                        msg);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void nextSixYear() {
        int initCount = getCountOrder();
        String msg = "Неверно указан срок действия карты";
        new MainPage()
                .toPayPage()
                .setInvalidValue(getCardInfo(approvedCard(),
                                checkYear(6), checkMonth(0), validOwner(), cvv()),
                        msg);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidYearLengthIsOne() {
        int initCount = getCountOrder();
        String msg = "Неверный формат";
        new MainPage()
                .toPayPage()
                .setInvalidValue(getCardInfo(approvedCard(),
                                invalidYear("1"), checkMonth(0), validOwner(), cvv()),
                        msg);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void lengthYearField() {
        int length = 2;
        assertEquals(length, new MainPage().toPayPage()
                .lengthYearField(generateValue("en").numerify("###")));
    }

    @Test
    public void inputSymbolAndLatinInYearField() {
        int length = 0;
        assertEquals(length, new MainPage().toPayPage()
                .lengthYearField(symbol()));
    }

    @Test
    public void invalidOwnerOfRuSymbol() {
        int initCount = getCountOrder();
        String msg = "Неверный формат";
        new MainPage()
                .toPayPage()
                .setInvalidValue(getCardInfo(approvedCard(),
                                checkYear(0), checkMonth(0), ownerRu(), cvv()),
                        msg);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidOwnerOfNumbers() {
        int initCount = getCountOrder();
        String msg = "Неверный формат";
        new MainPage()
                .toPayPage()
                .setInvalidValue(getCardInfo(approvedCard(),
                                checkYear(0), checkMonth(0),
                                invalidOwner(generateValue("ru").numerify("###")), cvv()),
                        msg);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidOwnerOfSymbol() {
        int initCount = getCountOrder();
        String msg = "Неверный формат";
        new MainPage()
                .toPayPage()
                .setInvalidValue(getCardInfo(approvedCard(),
                                checkYear(0), checkMonth(0), invalidOwner(symbol()), cvv()),
                        msg);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidOwnerLengthIs70() {
        int initCount = getCountOrder();
        String msg = "Количество символов не более 70";
        new MainPage()
                .toPayPage()
                .setInvalidValue(getCardInfo(approvedCard(),
                                checkYear(0), checkMonth(0),
                                invalidOwner(generateValue("en").letterify("?").repeat(70)), cvv()),
                        msg);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void invalidCVCLessOne() {
        int initCount = getCountOrder();
        String msg = "Неверный формат";
        new MainPage()
                .toPayPage()
                .setInvalidValue(getCardInfo(approvedCard(),
                                checkYear(0), checkMonth(0), validOwner(),
                                invalidCVV(generateValue("en").numerify("##"))),
                        msg);
        assertEquals(initCount, getCountOrder());
    }

    @Test
    public void lengthCVCOfSymbolAndLatin() {
        int length = 0;
        assertEquals(length, new MainPage().toPayPage()
                .lengthCVCField(symbol()));
    }

    @Test
    public void lengthCVCField() {
        int length = 3;
        assertEquals(length, new MainPage().toPayPage()
                .lengthCVCField(generateValue("en").numerify("####")));
    }

    @Test
    public void emptyFields() {
        String msg = "Поле обязательно для заполнения";
        new MainPage()
                .toPayPage().emptyFields(msg);
    }

    @Test
    public void successMsgClose() {
        new MainPage()
                .toPayPage().
                successNotificationClose(getCardInfo(approvedCard(), checkYear(0), checkMonth(0),
                        validOwner(), cvv()));
    }

    @Test
    public void failedMsgClose() {
        new MainPage()
                .toPayPage().
                failedNotificationClose(getCardInfo(randomNumber(), checkYear(0), checkMonth(0),
                        validOwner(), cvv()));
    }
}
