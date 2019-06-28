import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class WhatsAppTest {

    @BeforeAll
    static void startDriver() {
        WhatsAppScrapper.startDriver();
    }

    @Test
    void whatsAppTitleValidationWithRegex() {
        String wrongTitle1 = "() WhatsApp";
        String wrongTitle2 = "(a) WhatsApp";
        String okTitle1 = "WhatsApp";
        String okTitle2 = "(1) WhatsApp";

        String whatsAppTitleRegex = "((\\([1-9]+\\)) WhatsApp)|WhatsApp";

        assertFalse(wrongTitle1.matches(whatsAppTitleRegex));
        assertFalse(wrongTitle2.matches(whatsAppTitleRegex));
        assertTrue(okTitle1.matches(whatsAppTitleRegex));
        assertTrue(okTitle2.matches(whatsAppTitleRegex));

    }

    /**
     * Passes when there are unread messages
     */

    @Test
    void testLogInWhatsApp(){
        WhatsAppScrapper.openLogIn();
        String whatsAppTitleWithMessagesRegex = "((\\([1-9]+\\)) WhatsApp)";
        assertTrue(WhatsAppScrapper.getCurrentTitle().matches(whatsAppTitleWithMessagesRegex));
    }

    @Test
    void testGetFirstChats(){
        WhatsAppScrapper.openLogIn();
        List<String> names = WhatsAppScrapper.getFirstChatNames();
        assertFalse(names.isEmpty());
    }

    @Test
    void testOpenChat(){
        String chatName = "yo";
        WhatsAppScrapper.openLogIn();
        WhatsAppScrapper.openChat(chatName);
        assertEquals(chatName, WhatsAppScrapper.getOpenChatName());
    }

    @Test
    void sendMessage(){
        String chatName = "yo";
        String message = "hola";
        WhatsAppScrapper.openLogIn();
        WhatsAppScrapper.openChat(chatName);
        WhatsAppScrapper.sendMessage(message);
        assertEquals(message, WhatsAppScrapper.getLastMessageSent());

    }

    @Test
    void getQRImage() {
        String folder = "/Users/matiasmiodosky/sandbox/whatsApp/src/main/resources/QR";
        String fileName = WhatsAppScrapper.getQRImage(folder);
        File[] files = new File(folder).listFiles();
        assert files != null;
        assertTrue(Arrays.stream(files)
                .map(File::getName)
                .collect(Collectors.toList())
                .contains(fileName));

    }




    @AfterAll
    static void endDriver() {
        WhatsAppScrapper.endDriver();
    }

}