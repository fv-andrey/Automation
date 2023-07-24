package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CreditPayPage {
    private SelenideElement heading = $$("h3.heading").findBy(exactText("Кредит по данным карты"));
    private SelenideElement cardNumber = $("fieldset input[placeholder='0000 0000 0000 0000']");
    private SelenideElement month = $$("fieldset [tabindex='-1'] input").get(0);
    private SelenideElement year = $$("fieldset [tabindex='-1'] input").get(1);
    public SelenideElement owner = $$("fieldset [tabindex='-1'] input").get(2);
    private SelenideElement cvc = $$("fieldset [tabindex='-1'] input").get(3);
    private ElementsCollection invalidInput = $$(".input_invalid");
    private SelenideElement successNotificationTitle = $$(".notification__title").get(0);
    private SelenideElement successNotificationContent = $$(".notification__content").get(0);
    private SelenideElement failedNotificationTitle = $$(".notification__title").get(1);
    public SelenideElement failedNotificationContent = $$(".notification__content").get(1);
    public SelenideElement continueButton = $$("[role='button']").find(exactText("Продолжить"));
    private SelenideElement successNotificationCloser = $$("[type='button'].notification__closer").get(0);
    private SelenideElement failedNotificationCloser = $$("[type='button'].notification__closer").get(1);
    private String successTitle = "Успешно";
    private String successContent = "Операция одобрена Банком.";
    private String failedTitle = "Ошибка";
    private String failedContent = "Ошибка! Банк отказал в проведении операции";
    private String wrongFormat = "Неверный формат";
    private String invalidExpirationDate = "Неверно указан срок действия карты";
    private String cardExpired = "Истёк срок действия карты";
    private String maxSymbol = "Количество символов не более 70";
    private String emptyField = "Поле обязательно для заполнения";

    public CreditPayPage() {
        heading.shouldBe(visible);
    }

    public void setValue(DataHelper.CardInfo cardInfo) {
        cardNumber.setValue(cardInfo.getNumber());
        month.setValue(cardInfo.getMonth());
        year.setValue(cardInfo.getYear());
        owner.setValue(cardInfo.getHolder());
        cvc.setValue(cardInfo.getCvc());
        continueButton.click();
    }

    public void shouldByMsgSuccessPay(DataHelper.CardInfo cardInfo) {
        setValue(cardInfo);
        successNotificationTitle.shouldBe(visible, Duration.ofSeconds(14));
        successNotificationTitle.shouldBe(text(successTitle));
        successNotificationContent.shouldBe(text(successContent));
    }

    public void shouldByMsgFailedPay(DataHelper.CardInfo cardInfo) {
        setValue(cardInfo);
        failedNotificationTitle.shouldBe(visible, Duration.ofSeconds(14));
        failedNotificationTitle.shouldBe(text(failedTitle));
        failedNotificationContent.shouldBe(text(failedContent));
    }


    public void shouldBeMsgWrongFormat(DataHelper.CardInfo cardInfo) {
        setValue(cardInfo);
        invalidInput.get(0).shouldBe(visible, text(wrongFormat));
    }

    public void shouldBeMsgInvalidExpirationDate(DataHelper.CardInfo cardInfo) {
        setValue(cardInfo);
        invalidInput.get(0).shouldBe(visible, text(invalidExpirationDate));
    }

    public void shouldBeMsgCardExpired(DataHelper.CardInfo cardInfo) {
        setValue(cardInfo);
        invalidInput.get(0).shouldBe(visible, text(cardExpired));
    }

    public void shouldBeMsgMaxSymbol(DataHelper.CardInfo cardInfo) {
        setValue(cardInfo);
        invalidInput.get(0).shouldBe(visible, text(maxSymbol));
    }

    public int lengthNumberField(String value) {
        cardNumber.setValue(value);
        return cardNumber.getAttribute("value").length();
    }

    public int lengthMonthField(String value) {
        month.setValue(value);
        return month.getAttribute("value").length();
    }

    public int lengthYearField(String value) {
        year.setValue(value);
        return year.getAttribute("value").length();
    }

    public int lengthCVCField(String value) {
        cvc.setValue(value);
        return cvc.getAttribute("value").length();
    }

    public void successNotificationClose(DataHelper.CardInfo cardInfo) {
        setValue(cardInfo);
        successNotificationTitle.shouldBe(visible, Duration.ofSeconds(14));
        successNotificationCloser.click();
        successNotificationTitle.shouldBe(hidden);
        failedNotificationTitle.shouldBe(hidden);
    }

    public void failedNotificationClose(DataHelper.CardInfo cardInfo) {
        setValue(cardInfo);
        failedNotificationTitle.shouldBe(visible, Duration.ofSeconds(14));
        failedNotificationCloser.click();
        failedNotificationTitle.shouldBe(hidden);
        successNotificationTitle.shouldBe(hidden);
    }

    public void emptyFields(String msg) {
        continueButton.click();
        invalidInput.get(0).shouldBe(visible, text(msg));
        invalidInput.get(1).shouldBe(visible, text(msg));
        invalidInput.get(2).shouldBe(visible, text(msg));
        invalidInput.get(3).shouldBe(visible, text(msg));
        invalidInput.get(4).shouldBe(visible, text(msg));
    }
}

