package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    String generateDate (int dayToAdd, String pattern) {
        return LocalDate.now().plusDays(dayToAdd).format(DateTimeFormatter.ofPattern(pattern));
    }
    @Test
    public void shouldInterfaceTest() {
        open("http://localhost:9999/");
        Configuration.holdBrowserOpen = true;
        String planingDate = generateDate(4,"dd.MM.yyyy");
        $("[data-test-id='city'] input").setValue("Пенза");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME),Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planingDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[name='phone']").setValue("+79990009999");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planingDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
}
