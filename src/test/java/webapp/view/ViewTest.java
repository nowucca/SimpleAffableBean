
package webapp.view;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.jupiter.api.Assertions.*;

public class ViewTest {

    private static final String APP_CONTEXT = "SimpleAffableBean";

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeAll
    public static void setUpBeforeClass() {
        ChromeDriverManager.getInstance().setup();
    }

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        // Wait up to 10 seconds for the driver to load
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        // Initialize a web-driver wait object
        // This will be used to explicitly wait for specific elements
        wait = new WebDriverWait(driver, 10);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void viewHomePage() {

        // Load the homepage of the bookstore
        driver.get("http://localhost:8080/" + APP_CONTEXT);

        // Wait for all elements with class "categoryDropdownItem" to load
        // Once they load, store them as a list of web-element objects
        List<WebElement> categoryItems =
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("categoryBox")));


        // Click on the 3rd category
        categoryItems.get(2).click();

        wait.until(ExpectedConditions.urlContains("category"));

        validatePageUrl("/" + APP_CONTEXT + "/category");
    }

    private void validatePageUrl(String expectedPageUrl) {
        try {
            // Get the current URL and store it in a URL object
            URL url = new URL(driver.getCurrentUrl());
            // Check if the path of the URL equals the application context + category.jsp
            assertEquals(expectedPageUrl, url.getPath());
        } catch (MalformedURLException mue) {
            // If the URL returned is not a proper URL string, fail the test
            // Note: It will always be a proper URL string since that is what the getCurrentUrl returns
            fail("Malformed URL");
        }
    }

    @Test
    public void logoLinksToHomepage() {

        driver.get("http://localhost:8080/" + APP_CONTEXT + "/category?2002");

        WebElement logo = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("logo")));

        logo.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("welcomeText")));

        validatePageUrl("/" + APP_CONTEXT + "/");

    }

    @Test
    public void selectedCategoryButton() {
        driver.get("http://localhost:8080/" + APP_CONTEXT + "/category");

        List<WebElement> categoryButtons =
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("categoryButton")));

        int selected = 0;
        for (WebElement categoryButton: categoryButtons) {

            String classNames = categoryButton.getAttribute("className");
            assertNotNull(classNames);
            assertTrue(classNames.contains("categoryButton"), "categoryButton is a required class");

            final String categoryButtonId = categoryButton.getAttribute("id");
            if (categoryButtonId != null && categoryButtonId.length()>0) {
                if (selected > 0 && "selectedCategory".equals(categoryButtonId)) {
                    fail("Only one category button should be selected");
                }
                assertEquals( "selectedCategory", categoryButtonId, "Found a strange category id: "+categoryButtonId);
                selected++;
            }
        }
        assertEquals( 1, selected, "There must be a selected category");

    }


    @Test
    public void categoryPageHasAtLeast4CategoryButtons() {
        // Load the homepage of the bookstore
        driver.get("http://localhost:8080/" + APP_CONTEXT + "/category");

        List<WebElement> categoryButtons = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("categoryButton")));

        assertTrue(categoryButtons.size() >= 4);
    }
    /*

    logoLinksToHomepage - This opens the category page, then click on the logo to ensure that it takes you to the homepage
    selectedCategoryButton is a categoryButton - all category buttons have the class "categoryButton", but only one of those category buttons has the ID selectedCategoryButton. This test ensures that the selectedCategoryButton also has the class categoryButton.
    cartCountTextHasNumberFrom0To9 - Check if the text of the cartCount element is a number from 0 to 9.
    categoryPageHasAtLeast4CategoryButtons - Ensure that the category page has at least 4 category buttons.

     */
}
