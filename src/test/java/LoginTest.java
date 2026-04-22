import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.junit.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

public class LoginTest {
    WebDriver driver;
    WebDriverWait wait;

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/login");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        System.out.println("Navigated to login page");
    }

    @After
    public void teardown() {
        driver.quit();
        System.out.println("Driver quit");
    }

    @Test
    public void testLoginPageTitle() {
        String title = driver.getTitle();
        System.out.println("Page title is: " + title);
        Assert.assertEquals("The Internet", title);
    }

    @Test
    public void testLoginSuccess() {
        System.out.println("Starting testLoginSuccess...");
        
        // Encuentra el campo de usuario e ingresa credenciales válidas
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("tomsmith");
        System.out.println("Username entered: tomsmith");
        
        // Encuentra el campo de contraseña e ingresa la contraseña
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("SuperSecretPassword!");
        System.out.println("Password entered: SuperSecretPassword!");
        
        // Encuentra y hace clic en el botón de login
        WebElement loginButton = driver.findElement(By.cssSelector("button"));
        loginButton.click();
        System.out.println("Login button clicked");
        
        // Espera explícita: espera hasta que el mensaje de éxito sea visible
        WebElement successMessage = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".flash.success"))
        );
        System.out.println("Success message found: " + successMessage.getText());
        
        // Verifica que el mensaje de éxito contiene el texto esperado
        Assert.assertTrue("Success message should contain 'You logged into a secure area!'", 
                          successMessage.getText().contains("You logged into a secure area!"));
        System.out.println("testLoginSuccess PASSED");
    }

    @Test
    public void testLoginError() {
        System.out.println("Starting testLoginError...");
        
        // Ingresa credenciales inválidas
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("invaliduser");
        System.out.println("Invalid username entered: invaliduser");
        
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("wrongpassword");
        System.out.println("Invalid password entered: wrongpassword");
        
        // Hace clic en el botón de login
        WebElement loginButton = driver.findElement(By.cssSelector("button"));
        loginButton.click();
        System.out.println("Login button clicked");
        
        // Espera explícita: espera hasta que el mensaje de error sea visible
        WebElement errorMessage = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".flash.error"))
        );
        System.out.println("Error message found: " + errorMessage.getText());
        
        // Verifica que el mensaje de error contiene el texto esperado
        Assert.assertTrue("Error message should contain 'Your username is invalid!'", 
                          errorMessage.getText().contains("Your username is invalid!"));
        System.out.println("testLoginError PASSED");
    }
}
