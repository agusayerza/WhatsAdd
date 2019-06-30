import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class WhatsAppScrapper {

    private static final String CHROME_DRIVER_PATH = "chromedriver";
    private static final String WHATS_APP_LINK = "https://web.whatsapp.com/";
    private WebDriver driver;
    private boolean loggedIn = false;
    private boolean active;
    private String qrFolder;
    private String scFolder;

    WhatsAppScrapper(String qrFolder, String scFolder) {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("--proxy-server='direct://'");
        options.addArguments("--proxy-bypass-list=*");
        options.addArguments("--start-maximized");
        options.addArguments("--headless");
        options.addArguments("user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.90 Safari/537.36");
        driver = new ChromeDriver(options);
        this.qrFolder = qrFolder;
        this.scFolder = scFolder;
    }

    //driver utils
    void close() {
        driver.quit();
        this.active = false;
    }

    String getUserAgent(){
        return (String) ((JavascriptExecutor)driver).executeScript("return navigator.userAgent");
    }

    //getters


    public boolean isLoggedIn() {
        return loggedIn;
    }

    String getCurrentTitle() {
        return driver.getTitle();
    }

    List<String> getFirstChatNames() {
        List<String> chatNames = driver
                .findElements(
                        By.className(ClassNames.CHAT_NAME.label)
                )
                .stream()
                .map(we -> we.getAttribute("title"))
                .collect(Collectors.toList());


        return chatNames;
    }

    String getOpenChatName() {
        WebElement openChat = driver.findElement(By.id(IdValues.OPENED_CHAT.label));
        return openChat.findElement(By.className(ClassNames.CHAT_NAME.label)).getAttribute("title");
    }

    String getLastMessageSent() {
        List<WebElement> chatComponents = driver.findElements(By.className(ClassNames.CHAT_COMPONENT.label));
        WebElement last = chatComponents.get(chatComponents.size() - 1);
        return last.findElements(By.tagName("span")).get(1).getAttribute("innerHTML");
    }

    /**
     * Saves de QR on the filename specified: filename
     *
     * @return the filename of the QR file
     */
    String getQRImage() {
        String src = driver.findElement(By.className(ClassNames.QR_IMAGE.label)).findElement(By.tagName("img")).getAttribute("src");
        return ImageSaver.saveFile(qrFolder,"QR_", src);
    }

    String getScreenShot() {
        String screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
        return "SCREENSHOT_" + ImageSaver.saveFile(scFolder, "SCREENSHOT_", "data:image/jpeg;base64," + screenshot);
    }


    //simple actions

    private void openLogIn() {
        driver.get(WHATS_APP_LINK);
    }

    void openChat(String chatName) {
        WebElement searchBar = driver.findElement(By.className(ClassNames.SEARCH_BAR.label));
        searchBar.sendKeys(chatName + "\n");
    }

    void sendMessage(String message) {
        driver.findElement(By.className(ClassNames.TEXT_INPUT.label))
                .sendKeys(message + "\n");
    }

    void waitScan() {
        while (true) {
            try {
                driver.findElement(By.className(ClassNames.QR_IMAGE.label));
            } catch (NoSuchElementException e) {
                return;
            }
        }

    }

    private void waitLoader(){
        while (true) {
            try {
                driver.findElement(By.className(ClassNames.LOADER.label));
            } catch (NoSuchElementException e) {
                return;
            }
        }
    }



    //protocoles
    void logIn(){
        try {
            openLogIn();
            waitLoader();
            String qr = getQRImage();
            ImageDisplayer imageDisplayer = new ImageDisplayer();
            imageDisplayer.showImage(qrFolder + "/" + qr);
            waitScan();
            waitLoader();
            imageDisplayer.close();
            loggedIn = true;
        }catch (WebDriverException e){
            e.printStackTrace();
            getScreenShot();
            throw e;
        }

    }
}

