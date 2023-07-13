package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {

    private SelenideElement heading = $("h2.heading");
    private SelenideElement payButton = $$("[type='button']").find(exactText("Купить"));
    private SelenideElement creditButton = $$("[type='button']").find(exactText("Купить в кредит"));

    public MainPage() {
        heading.shouldBe(visible, text("Путешествие дня"));
    }

    public PayPage toPayPage() {
        payButton.click();
        return new PayPage();
    }

    public CreditPayPage toCreditPage() {
        creditButton.click();
        return new CreditPayPage();
    }
}
