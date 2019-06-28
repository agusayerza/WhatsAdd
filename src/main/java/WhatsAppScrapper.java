import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class WhatsAppScrapper {

    private static final String CHROME_DRIVER_PATH = "chromedriver";
    private static final String WHATS_APP_LINK = "https://web.whatsapp.com/";
    private static WebDriver driver;

    //driver utils
    static void startDriver() {
        if (driver == null) {
            System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
            driver = new ChromeDriver();
        }
    }

    static void endDriver() {
        driver.quit();
        driver = null;
    }

    //getters

    static String getCurrentTitle() {
        return driver.getTitle();
    }

    static List<String> getFirstChatNames() {
        List<String> chatNames = driver
                .findElements(
                        By.className(ClassNames.CHAT_NAME.label)
                )
                .stream()
                .map(we -> we.getAttribute("title"))
                .collect(Collectors.toList());


        return chatNames;
    }

    static String getOpenChatName(){
        WebElement openChat = driver.findElement(By.id(IdValues.OPENED_CHAT.label));
        return openChat.findElement(By.className(ClassNames.CHAT_NAME.label)).getAttribute("title");
    }

    static String getLastMessageSent() {
       List<WebElement> chatComponents = driver.findElements(By.className(ClassNames.CHAT_COMPONENT.label));
       WebElement last = chatComponents.get(chatComponents.size() - 1);
       return last.findElements(By.tagName("span")).get(1).getAttribute("innerHTML");
}

    /**
     * Saves de QR on the filename specified: filename
     * @return the filename of the QR file
     */
    static String getQRImage( String folder){
        driver.get(WHATS_APP_LINK);
        String src = driver.findElement(By.className(ClassNames.QR_IMAGE.label)).findElement(By.tagName("img")).getAttribute("src");
        return ImageSaver.saveQR(folder,src);
    }
    //actions
    static void openLogIn() {
        driver.get(WHATS_APP_LINK);
        for (int i = 10; i > 0; i--) {
            System.out.println(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static void openChat(String chatName){
        WebElement searchBar = driver.findElement(By.className(ClassNames.SEARCH_BAR.label));
        searchBar.sendKeys(chatName + "\n");
    }

    static void sendMessage(String message) {
        driver.findElement(By.className(ClassNames.TEXT_INPUT.label))
                .sendKeys(message + "\n");
    }



}
