package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {

    private DataHelper() {
    }

    public static Faker generateValue(String locale) {
        return new Faker(new Locale(locale));
    }

    public static String symbol() {
        return "@#$%&*volf";
    }

    @Value
    public static class CardNumber {
        String number;
    }

    public static CardNumber approvedCard() {
        return new CardNumber("1111 2222 3333 4444");
    }

    public static CardNumber declinedCard() {
        return new CardNumber("5555 6666 7777 8888");
    }

    public static CardNumber invalidNumber(String value) {
        return new CardNumber(value);
    }

    public static CardNumber randomNumber() {
        return new CardNumber(generateValue("ru").numerify("#### #### #### ####"));
    }

    @Value
    public static class Month {
        String month;
    }

    public static Month checkMonth(int shift) {
        return new Month(LocalDate.now().plusMonths(shift).format(DateTimeFormatter.ofPattern("MM")));
    }

    public static Month invalidMonth(String value) {
        return new Month(value);
    }

    @Value
    public static class Year {
        String year;
    }

    public static Year checkYear(int shift) {
        return new Year(LocalDate.now().plusYears(shift).format(DateTimeFormatter.ofPattern("yy")));
    }

    public static Year invalidYear(String value) {
        return new Year(value);
    }

    @Value
    public static class CardOwner {
        String owner;
    }

    public static CardOwner validOwner() {
        return new CardOwner(generateValue("en").name().firstName() +
                " " + generateValue("en").name().lastName());
    }

    public static CardOwner ownerRu() {
        return new CardOwner(generateValue("ru").name().firstName() + " "
                + generateValue("ru").name().lastName());
    }

    public static CardOwner invalidOwner(String value) {
        return new CardOwner(value);
    }

    @Value
    public static class CVC {
        String cvc;
    }

    public static CVC cvv() {
        return new CVC(generateValue("en").numerify("###"));
    }

    public static CVC invalidCVV(String value) {
        return new CVC(value);
    }

    @Value
    public static class CardStatus {
        String approved;
        String declined;
    }

    public static CardStatus status() {
        return new CardStatus("APPROVED", "DECLINED");
    }

    @Value
    public static class CardInfo {
        String number;
        String year;
        String month;
        String holder;
        String cvc;
    }

    public static CardInfo getCardInfo(CardNumber number, Year year, Month month, CardOwner owner, CVC cvv) {
        return new CardInfo(number.getNumber(), year.getYear(), month.getMonth(), owner.getOwner(), cvv.getCvc());
    }
}
