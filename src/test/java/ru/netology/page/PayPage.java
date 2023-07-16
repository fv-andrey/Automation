package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PayPage {

    SelenideElement heading = $$("h3.heading").findBy(exactText("Оплата по карте"));
    SelenideElement cardNumber = $("fieldset input[placeholder='0000 0000 0000 0000']");
    SelenideElement month = $$("fieldset [tabindex='-1'] input").get(0);
    SelenideElement year = $$("fieldset [tabindex='-1'] input").get(1);
    SelenideElement owner = $$("fieldset [tabindex='-1'] input").get(2);
    SelenideElement cvc = $$("fieldset [tabindex='-1'] input").get(3);
    ElementsCollection invalidInput = $$(".input_invalid");
    SelenideElement successNotificationTitle = $$(".notification__title").get(0);
    SelenideElement successNotificationContent = $$(".notification__content").get(0);
    SelenideElement failedNotificationTitle = $$(".notification__title").get(1);
    SelenideElement failedNotificationContent = $$(".notification__content").get(1);
    SelenideElement continueButton = $$("[role='button']").find(exactText("Продолжить"));
    SelenideElement successNotificationCloser = $$("[type='button'].notification__closer").get(0);
    SelenideElement failedNotificationCloser = $$("[type='button'].notification__closer").get(1);
    static SelenideElement infoAmount = $$(".list__item").findBy(text("руб.!"));
    static String balanceStart = "Всего ";
    static String balanceFinish = " 000 руб.!";
    static String balanceStart1 = "Всего 45 ";
    static String balanceFinish1 = " руб.!";

    public PayPage() {
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

    public void successPay(DataHelper.CardInfo cardInfo, String title, String content) {
        setValue(cardInfo);
        successNotificationTitle.shouldBe(visible, Duration.ofSeconds(14));
        successNotificationTitle.shouldBe(text(title));
        successNotificationContent.shouldBe(text(content));
    }

    public void failedPay(DataHelper.CardInfo cardInfo, String title, String content) {
        setValue(cardInfo);
        failedNotificationTitle.shouldBe(visible, Duration.ofSeconds(14));
        failedNotificationTitle.shouldBe(text(title));
        failedNotificationContent.shouldBe(text(content));
    }

    public int lengthNumberField(String value) {
        cardNumber.setValue(value);
        return cardNumber.getAttribute("value").length();
    }

    public void setInvalidValue(DataHelper.CardInfo cardInfo, String msg) {
        setValue(cardInfo);
        invalidInput.get(0).shouldBe(visible, text(msg));
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

    public static int extractAmount(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        var start1 = text.indexOf(balanceStart1);
        var finish1 = text.indexOf(balanceFinish1);
        var value1 = text.substring(start1 + balanceStart1.length(), finish1);
        var finishValue = value + value1;
        return Integer.parseInt(finishValue);
    }

    public static int getAmountInfo() {
        var text = infoAmount.text();
        return extractAmount(text);
    }
}

