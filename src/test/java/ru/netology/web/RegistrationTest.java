package ru.netology.web;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

class RegistrationTest {
    @Test
    void shouldRegisterByAccountNumberDOMModification() {

        open("http://localhost:9999/");

        $("[data-test-id='city'] input").setValue("Киров");
        String validDate = getValidDate();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(validDate);
        $("input[name='name']").setValue("Петров Василий");
        $("input[name='phone']").setValue("+71234567890");
        $("input[name='agreement']").parent().click();
        $(".button__text").shouldHave(text("Забронировать")).click();
        $("div[data-test-id='spinner']").should(disappear);
        $("div[data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15));
        $("div[data-test-id='notification'] .notification__content")
                .shouldHave(text("Встреча успешно забронирована на " + validDate));
    }

    private String getValidDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate validDate = currentDate.plusDays(3);
        return validDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}

