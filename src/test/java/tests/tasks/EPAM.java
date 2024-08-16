package tests.tasks;

import com.deque.axe.AXE;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTestClass;

import java.net.URL;

public class EPAM extends BaseTestClass {
    private static final URL scriptUrl = EPAM.class.getClassLoader().getResource("axe.min.js");

    private WebDriver driver;

        @BeforeMethod
        public void setup() {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.get("https://oxfordsummercourses.com/arts-humanities/cambridge/13-15-years/");
        }

    @Test
    public synchronized void testEPAM() {

        // Run Axe test
        JSONObject responseJSON = new AXE.Builder(driver, scriptUrl).analyze();

        // Show results
        JSONArray violations = responseJSON.getJSONArray("violations");
        if (violations.length() == 0) {
            System.out.println("Problems with accessibility are not found.");
        } else {
            AXE.writeResults("testEPAM", responseJSON);
            Assert.assertTrue(false, AXE.report(violations));


            //Another way of reporting
//            System.out.println("Problems with accessibility are found:");
//            for (int i = 0; i < violations.length(); i++) {
//                JSONObject violation = violations.getJSONObject(i);
//                System.out.println(violation.getString("description"));
//                System.out.println("Impact level: " + violation.getString("impact"));
//                JSONArray nodes = violation.getJSONArray("nodes");
//                for (int j = 0; j < nodes.length(); j++) {
//                    System.out.println("  - Element: " + nodes.getJSONObject(j).getJSONArray("target"));
//                }

        }

    }

    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult testResult) {
        log.debug("[END TEST] ");
        tearDown(driver, testResult);
    }

}
