
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class WhatsAppTest {

    private static final String qrFolder = "/Users/matiasmiodosky/projects/WhatsAdd/src/main/resources/QR";
    private static final String scFolder = "/Users/matiasmiodosky/projects/WhatsAdd/src/main/resources/ScreenShots";

    private static final WhatsAppScrapper scrapper = new WhatsAppScrapper(qrFolder, scFolder);

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
    void testLogInWhatsApp() {
        try {
            String whatsAppTitleWithMessagesRegex = "((\\([1-9]+\\)) WhatsApp)";
            if (scrapper.isNotLoggedIn())scrapper.logIn();
            String title = scrapper.getCurrentTitle();
            System.out.println(title);
            assertTrue(title.matches(whatsAppTitleWithMessagesRegex));
        } catch (Exception e) {
            System.out.println("Login error screenshot: " + scrapper.getScreenShot());
            throw e;
        }
    }

    @Test
    void testGetFirstChats() {
        try {
            if (scrapper.isNotLoggedIn())scrapper.logIn();
            List<String> names = scrapper.getFirstChatNames();
            System.out.println(names);
            assertFalse(names.isEmpty());
        } catch (Exception e) {
            System.out.println("getFirstChats error screenshot: " + scrapper.getScreenShot());
            throw e;
        }
    }

    @Test
    void testOpenChat() {

        if (scrapper.isNotLoggedIn())scrapper.logIn();
        try {
            String chatName = "yo";
            scrapper.openChat(chatName);
            String gottenChatName = scrapper.getOpenChatName();
            System.out.println(gottenChatName);
            assertEquals(chatName, gottenChatName);
        } catch (Exception e) {
            System.out.println("openChat error screenshot: " + scrapper.getScreenShot());
            throw e;
        }
    }

    @Test
    void sendMessage() {
        try {
            if (scrapper.isNotLoggedIn())scrapper.logIn();
            String chatName = "yo";
            String message = "hola";
            scrapper.openChat(chatName);
            scrapper.sendMessage(message);
            assertEquals(message, scrapper.getLastMessageSent());
        }catch (Exception e){
            System.out.println("sendMessage error screenshot: " + scrapper.getScreenShot());
            throw e;
        }
    }

    @AfterAll
    static void end(){
        scrapper.close();
    }


}