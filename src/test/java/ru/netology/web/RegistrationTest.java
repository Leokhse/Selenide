package ru.netology.web;

import org.junit.jupiter.api.Test;


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
        String validDate = getValidDate(3);
        $("input[placeholder='Дата встречи']").setValue(validDate);
        $("input[name='name']").setValue("Петров Василий");
        $("input[name='phone']").setValue("+71234567890");
        $("input[name='agreement']").parent().click();
        $(".button__text").shouldHave(text("Забронировать")).click();
        $("div[data-test-id='spinner']").should(disappear);
        $("div[data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15));
        $("div[data-test-id='notification'] .notification__content")
                .shouldHave(text("Встреча успешно забронирована на " + validDate));
    }

    private String getValidDate(int daysToAdd) {
        // Логика для получения даты, не ранее трех дней с текущей даты
        // В данном примере просто добавляем указанное количество дней к текущей дате
        LocalDate currentDate = LocalDate.now();
        LocalDate validDate = currentDate.plusDays(daysToAdd);
        return validDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
