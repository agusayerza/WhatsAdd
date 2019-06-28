import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BasicTest {

    @BeforeAll
    static void startDriver() {
        BasicScrapper.startDriver();
    }

    @Test
    void getSiteTitle() throws IOException {

        String googleUrl = "https://www.google.com";
        String googleTitle = "Google";
        assertEquals(googleTitle, BasicScrapper.getSiteTitle(googleUrl));

        String whatsAppUrl = "https://web.whatsapp.com";
        String whatsAppTitleRegex = "((\\([1-9]+\\)) WhatsApp)|WhatsApp";
        assertTrue(BasicScrapper.getSiteTitle(whatsAppUrl).matches(whatsAppTitleRegex));

    }

    @AfterAll
    static void endDriver() {
        BasicScrapper.endDriver();
    }

}