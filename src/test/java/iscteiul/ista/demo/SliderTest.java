package iscteiul.ista.demo;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selenide.*;

public class SliderTest {

    SliderPage page = new SliderPage();

    @BeforeEach
    void setup() {
        Configuration.browserSize = "1280x800";
        open("https://the-internet.herokuapp.com/horizontal_slider");
    }

    @Test
    void sliderInteraction() {
        page.setSliderValue("3.5");
        page.verifyValue("3.5");
    }
}
