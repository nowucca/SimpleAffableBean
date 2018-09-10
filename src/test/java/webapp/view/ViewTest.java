
package webapp.view;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import java.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;

public class ViewTest {

    private static final String APP_CONTEXT = "DrABookstoreView";

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass // Run this code before the class starts
    public static void setUpBeforeClass() {
        ChromeDriverManager.getInstance().setup();
    }

    @Before // Run this code before every test method
    public void setUp() {
        // Initialize the driver
        driver = new ChromeDriver();
        // Wait up to 10 seconds for the driver to load
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        // Initialize a web-driver wait object
        // This will be used to explicitly wait for specific elements
        wait = new WebDriverWait(driver, 10);
    }

    @After // Run this code after every test method
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
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("categoryDropdownItem")));

        // Check that there are at least four categories
        assertTrue("At least four categories items in dropdown", 4 <= categoryItems.size());

        // Find the category dropdown element
        WebElement categoryDropdown = driver.findElement(By.id("categoryDropdown"));
        // Click on the category dropdown element
        categoryDropdown.click();

        // Create an actions object that allows you to move the cursor
        Actions actions = new Actions(driver);
        // Hover over the category dropdown with the cursor
        actions.moveToElement(categoryDropdown).build().perform();

        // Click on the 3rd element in the dropdown menu
        categoryItems.get(2).click();

        validatePageUrl("/" + APP_CONTEXT + "/category.jsp");
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

        // Load the homepage of the bookstore
        driver.get("http://localhost:8080/" + APP_CONTEXT + "/category.jsp");

        WebElement logo = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("logo")));

        logo.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("lowerHome")));

        validatePageUrl("/" + APP_CONTEXT + "/index.jsp");

    }

    @Test
    public void selectedCategoryButton() {
        // Load the homepage of the bookstore
        driver.get("http://localhost:8080/" + APP_CONTEXT + "/category.jsp");

        List<WebElement> categoryButtons =
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("categoryButton")));

        int selected = 0;
        for (WebElement categoryButton: categoryButtons) {

            String classNames = categoryButton.getAttribute("className");
            assertNotNull(classNames);
            assertTrue("categoryButton is a required class", classNames.contains("categoryButton"));

            final String categoryButtonId = categoryButton.getAttribute("id");
            if (categoryButtonId != null && categoryButtonId.length()>0) {
                if (selected > 0 && "selectedCategoryButton".equals(categoryButtonId)) {
                    fail("Only one category button should be selected");
                }
                assertEquals("Found a strange category id: "+categoryButtonId, "selectedCategoryButton", categoryButtonId);
                selected++;
            }
        }
        assertEquals("There must be a selected category", 1, selected);

    }

    @Test
    public void cartCountTextHasNumberFrom0To9() {
        // Load the homepage of the bookstore
        driver.get("http://localhost:8080/" + APP_CONTEXT + "/category.jsp");
        WebElement cartCount = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cartCount")));

        try {
            int cartSize = Integer.valueOf(cartCount.getText());
            assertTrue("Unexpected cart size "+cartSize, cartSize >= 0 && cartSize < 10);
        } catch (Exception e) {
            fail("Failed to parse cart count as an integer");
        }

    }

    @Test
    public void categoryPageHasAtLeast4CategoryButtons() {
        // Load the homepage of the bookstore
        driver.get("http://localhost:8080/" + APP_CONTEXT + "/category.jsp");

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
