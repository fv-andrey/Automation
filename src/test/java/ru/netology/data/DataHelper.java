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

    public static String getSymbolString() {
        return "@#$%&*volf";
    }

    public static String getApprovedCard() {
        return "1111 2222 3333 4444";
    }

    public static String getDeclinedCard() {
        return "5555 6666 7777 8888";
    }

    public static String getRandomCard() {
        return generateValue("ru").numerify("#### #### #### ####");
    }

    public static String getMonth(int shift) {
        return LocalDate.now().plusMonths(shift).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getYear(int shift) {
        return LocalDate.now().plusYears(shift).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getValidOwner() {
        return generateValue("en").name().firstName() +
                " " + generateValue("en").name().lastName();
    }

    public static String getOwnerOfRu() {
        return generateValue("ru").name().firstName() + " "
                + generateValue("ru").name().lastName();
    }

    public static String getCvv() {
        return generateValue("en").numerify("###");
    }

    public static String setInvalidValue(String value) {
        return value;
    }

    @Value
    public static class CardStatus {
        String approved;
        String declined;
    }

    public static CardStatus initCardStatus() {
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

    public static CardInfo getCardInfo(String number, String year, String month, String owner, String cvv) {
        return new CardInfo (number, year, month, owner, cvv);
    }
}
