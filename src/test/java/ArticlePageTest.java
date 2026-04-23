/*
Test Case 3 - Pruebas de Página de Artículo

Tarea: Escribir pruebas para la página de detalles de un artículo.
- Verificar que al hacer clic en una noticia se redirija a la página del artículo correspondiente.
- Asegurarse de que la página del artículo contenga el título, el contenido y la fecha de publicación.

Ejemplo de prueba:
- Hacer clic en una noticia desde la página principal.
- Verificar que la página de detalles cargue correctamente y que el contenido del artículo sea visible.

*/

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

public class ArticlePageTest {
    String URL = "https://es.rollingstone.com/"; 

    WebDriver driver;
    WebDriverWait wait;

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(URL);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        System.out.println("Navegando a la página principal");
    }

    @After
    public void teardown() {
        driver.quit();
        System.out.println("Driver quit");
    }

    // TEST 3.1 - Verificar que al hacer clic en una noticia se redirija a la página del artículo correspondiente.
    @Test
    public void testArticleNavigation() {
        try {
            // Encontrar y dar click en el primer artículo
            java.util.List<WebElement> articles = driver.findElements(By.cssSelector("article, .post, .entry, a[href*='/articulo'], a[href*='/news']"));
            Assert.assertTrue("T-3.1.1. Should have at least one article to click", articles.size() > 0);
            
            String homeURL = driver.getCurrentUrl();
            System.out.println("T-3.1.2. Home page URL: " + homeURL);
            
            WebElement articleLink = articles.get(0);
            String articleTitle = "";
            try {
                WebElement titleElement = articleLink.findElement(By.xpath(".//h2 | .//h3 | .//a[contains(@class, 'title')]"));
                articleTitle = titleElement.getText();
                System.out.println("T-3.1.3. Clicking article: " + articleTitle);
            } catch (Exception e) {
                System.out.println("T-3.1.3. Could not retrieve article title, proceeding with click");
            }
            
            articleLink.click();
            
            // Esperar a que la página cargue y la URL cambie
            wait.until(ExpectedConditions.urlMatches(".*"));
            Thread.sleep(2000); // Tiempo adicional de espera
            
            String currentURL = driver.getCurrentUrl();
            System.out.println("T-3.1.4. Article page URL: " + currentURL);
            Assert.assertNotEquals("T-3.1.5. URL should change after clicking article", homeURL, currentURL);
            System.out.println("T-3.1.5. Successfully navigated to article page");
            
        } catch (Exception e) {
            System.out.println("T-3.1. FAIL: Article navigation failed: " + e.getMessage());
        }
    }

    // TEST 3.2 - Asegurarse de que la página del artículo contenga el título, el contenido y la fecha de publicación.
    @Test
    public void testArticlePageElements() {
        try {
            // Navegar al artículo
            java.util.List<WebElement> articles = driver.findElements(By.cssSelector("article, .post, .entry, a[href*='/articulo'], a[href*='/news']"));
            if (articles.size() > 0) {
                articles.get(0).click();
                wait.until(ExpectedConditions.urlMatches(".*"));
                Thread.sleep(2000);
            }
            
            // Verificar que el título existe
            try {
                WebElement articleTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1 | //h2[1] | //article//h1")));
                Assert.assertTrue("T-3.2.1. Article title should be displayed", articleTitle.isDisplayed());
                String titleText = articleTitle.getText();
                Assert.assertFalse("T-3.2.2. Article title should not be empty", titleText.trim().isEmpty());
                System.out.println("T-3.2.3. Article title: " + titleText);
            } catch (Exception e) {
                System.out.println("T-3.2.1. FAIL: Article title not found: " + e.getMessage());
            }
            
            // Verificar que el contenido existe
            try {
                WebElement articleContent = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//article//div[contains(@class, 'content')] | //article//div[contains(@class, 'post-content')] | //article//p | //div[contains(@class, 'entry-content')]")));
                Assert.assertTrue("T-3.2.4. Article content should be displayed", articleContent.isDisplayed());
                System.out.println("T-3.2.5. Article content is visible");
            } catch (Exception e) {
                System.out.println("T-3.2.4. FAIL: Article content not found: " + e.getMessage());
            }
            
            // Verificar que la fecha existe
            try {
                WebElement publicationDate = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//time | //span[contains(@class, 'date')] | //span[contains(@class, 'published')] | //*[contains(@class, 'post-date')]")));
                Assert.assertTrue("T-3.2.6. Publication date should be displayed", publicationDate.isDisplayed());
                String dateText = publicationDate.getText();
                Assert.assertFalse("T-3.2.7. Publication date should not be empty", dateText.trim().isEmpty());
                System.out.println("T-3.2.8. Publication date: " + dateText);
            } catch (Exception e) {
                System.out.println("T-3.2.6. FAIL: Publication date not found: " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.out.println("T-3.2. FAIL: Article page element verification failed: " + e.getMessage());
        }
    }
}
