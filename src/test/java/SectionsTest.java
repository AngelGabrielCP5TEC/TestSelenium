/*
Test Case 4 - Prueba de Acceso a Secciones Principales

Tarea: Escribir prueba para verificar el acceso a las secciones principales del portal.
- Verificar que se pueda acceder correctamente a las secciones del portal (por ejemplo, Política, Economía, Tecnología, etc.).
- Comprobar que al hacer clic en una sección se muestre la lista de artículos de esa sección.

Ejemplo de prueba:
- Hacer clic en el enlace de la sección "Tecnología".
- Verificar que se muestre una lista de artículos relacionados con "Tecnología".
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
import org.openqa.selenium.JavascriptExecutor;
import org.junit.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

public class SectionsTest {
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

    // TEST 4.1 - Verificar que se pueda acceder correctamente a las secciones del portal.
    @Test
    public void testSectionNavigation() {
        try {
            // Encontrar secciones del menú de navegación
            java.util.List<WebElement> sections = driver.findElements(By.xpath("//li[contains(@class, 'menu-item-object-category')]//a[@class='elementor-item']"));
            Assert.assertTrue("T-4.1.1. Should have at least one section link", sections.size() > 0);
            System.out.println("T-4.1.2. Found " + sections.size() + " section links");

            // Encontrar una sección en la que se pueda dar click (que no sea home page)
            WebElement sectionLink = null;
            String sectionName = "";
            for (WebElement section : sections) {
                String href = section.getAttribute("href");
                String text = section.getText();
                // Ignora links vacíos o de home
                if (href != null && !href.isEmpty() && !text.trim().isEmpty() && !href.equals(URL) && !text.equals("Home")) {
                    sectionLink = section;
                    sectionName = text;
                    break;
                }
            }

            Assert.assertNotNull("T-4.1.3. Should find a valid section link", sectionLink);
            System.out.println("T-4.1.4. Clicking section: " + sectionName);

            String homeURL = driver.getCurrentUrl();
            sectionLink.click();

            // Esperar a que la página cargue y la URL cambie
            wait.until(ExpectedConditions.urlMatches(".*"));
            Thread.sleep(2000);

            String currentURL = driver.getCurrentUrl();
            Assert.assertNotEquals("T-4.1.5. URL should change after clicking section", homeURL, currentURL);
            System.out.println("T-4.1.6. Successfully navigated to section: " + sectionName);

        } catch (Exception e) {
            System.out.println("T-4.1. FAIL: Section navigation failed: " + e.getMessage());
        }
    }

    // TEST 4.2 - Comprobar que al hacer clic en una sección se muestre la lista de artículos de esa sección.
    @Test
    public void testSectionArticlesList() {
        try {
            // Encuentra y haz click en una sección usando el menú de categorías
            java.util.List<WebElement> sections = driver.findElements(By.xpath("//li[contains(@class, 'menu-item-object-category')]//a[@class='elementor-item']"));
            Assert.assertTrue("T-4.2.1. Should have at least one section link", sections.size() > 0);

            WebElement sectionLink = null;
            String sectionName = "";
            for (WebElement section : sections) {
                String href = section.getAttribute("href");
                String text = section.getText();
                if (href != null && !href.isEmpty() && !text.trim().isEmpty() && !href.equals(URL)) {
                    sectionLink = section;
                    sectionName = text;
                    break;
                }
            }

            Assert.assertNotNull("T-4.2.2. Should find a valid section link", sectionLink);
            System.out.println("T-4.2.3. Clicking section: " + sectionName);
            sectionLink.click();
            wait.until(ExpectedConditions.urlMatches(".*"));
            Thread.sleep(2000);
            System.out.println("T-4.2.4. Navigated to section: " + sectionName);

            // Verifica que estemos en una página de categoría
            WebElement body = driver.findElement(By.tagName("body"));
            String bodyClass = body.getAttribute("class");
            Assert.assertTrue("T-4.2.5. Should be on category page", bodyClass.contains("category"));
            System.out.println("T-4.2.6. Confirmed on category page");

            // Verifica que el widget de posts exista
            WebElement postsWidget = driver.findElement(By.cssSelector("div.elementor-widget-posts-extra"));
            Assert.assertNotNull("T-4.2.7. Posts widget should exist", postsWidget);
            System.out.println("T-4.2.8. Posts widget found");

            // Verifica que haya artículos dentro del widget de posts
            java.util.List<WebElement> articles = driver.findElements(By.cssSelector("div.elementor-widget-posts-extra article"));
            Assert.assertTrue("T-4.2.9. Section should display at least one article", articles.size() > 0);
            System.out.println("T-4.2.10. Section contains " + articles.size() + " articles");

            // Verifica que los títulos de los artículos existan
            for (int i = 0; i < Math.min(3, articles.size()); i++) {
                WebElement article = articles.get(i);
                try {
                    // Encuentra el título del artículo usando su nombre de clase
                    WebElement titleElement = article.findElement(By.className("ee-post__title__heading"));
                    String titleText = titleElement.getText();
                    if (!titleText.trim().isEmpty()) {
                        System.out.println("T-4.2.11. Article " + (i + 1) + " title: " + titleText);
                    } else {
                        System.out.println("T-4.2.11. WARNING: Article " + (i + 1) + " has no title text found");
                    }
                } catch (Exception e) {
                    System.out.println("T-4.2.11. WARNING: Error processing article " + (i + 1) + ": " + e.getMessage());
                }
            }

        } catch (Exception e) {
            System.out.println("T-4.2. FAIL: Section article listing verification failed: " + e.getMessage());
        }
    }
}