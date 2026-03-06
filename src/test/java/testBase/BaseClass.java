package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {
    public static WebDriver driver;
    public Logger logger;
    public Properties p;

    @BeforeClass(groups = {"Sanity","Regression","Master"})
    @Parameters({"browser","appURL"})
    public void setup(String br, String appURL) throws IOException {
        // Load config.properties
        FileReader file = new FileReader("./src/test/resources/config.properties");
        p = new Properties();
        p.load(file);

        logger = LogManager.getLogger(this.getClass());

        // Remote environment (Docker Grid)
        if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {
            String hubUrl = p.getProperty("hub_url"); // e.g. http://localhost:4444/wd/hub

            switch (br.toLowerCase()) {
                case "chrome":
                    driver = new RemoteWebDriver(new URL(hubUrl), new ChromeOptions());
                    logger.info("Launching Chrome browser (remote)");
                    break;
                case "firefox":
                    driver = new RemoteWebDriver(new URL(hubUrl), new FirefoxOptions());
                    logger.info("Launching Firefox browser (remote)");
                    break;
                case "edge":
                    driver = new RemoteWebDriver(new URL(hubUrl), new EdgeOptions());
                    logger.info("Launching Edge browser (remote)");
                    break;
                default:
                    throw new IllegalArgumentException("Invalid browser name: " + br);
            }
        }

        // Local environment
        if (p.getProperty("execution_env").equalsIgnoreCase("local")) {
            switch (br.toLowerCase()) {
                case "chrome":
                    driver = new ChromeDriver();
                    logger.info("Launching Chrome browser (local)");
                    break;
                case "firefox":
                    driver = new FirefoxDriver();
                    logger.info("Launching Firefox browser (local)");
                    break;
                case "edge":
                    driver = new EdgeDriver();
                    logger.info("Launching Edge browser (local)");
                    break;
                default:
                    throw new IllegalArgumentException("Invalid browser name: " + br);
            }
        }

        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        // Dynamic appURL selection
        String url = p.getProperty(appURL);
        driver.get(url);
        logger.info("Application launched: " + url);
    }

    @AfterClass(groups = {"Sanity","Regression","Master"})
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed successfully");
        }
    }

    // Random Data Generators
    public String randomString() {
        return RandomStringUtils.randomAlphabetic(5);
    }

    public String randomNo() {
        return RandomStringUtils.randomNumeric(10);
    }

    public String randomAlphaNumeric() {
        return RandomStringUtils.randomAlphabetic(3) + RandomStringUtils.randomNumeric(3);
    }

    public String captureScreen(String tname) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

        TakesScreenshot takeScreenshot = (TakesScreenshot) driver;
        File sourceFile = takeScreenshot.getScreenshotAs(OutputType.FILE);

        String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";
        File targetFile = new File(targetFilePath);

        sourceFile.renameTo(targetFile);

        return targetFilePath;
    }
}