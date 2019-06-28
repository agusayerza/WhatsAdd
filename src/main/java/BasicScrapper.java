import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BasicScrapper {

    private static final String CHROME_DRIVER_PATH = "chromedriver";
    private static WebDriver driver;

    public static void startDriver(){
        if (driver == null){
            System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
            driver = new ChromeDriver();
        }
    }

    public static void endDriver(){
        driver.quit();
        driver = null;
    }

    static String getSiteTitle(String link) {
        driver.get(link);
        return driver.getTitle();
    }
}
