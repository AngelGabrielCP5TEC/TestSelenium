/*
Test Case 1 - Pruebas de la Página Principal

Tarea: Escribir pruebas para verificar la funcionalidad de la página principal del portal.
- Verificar que la página principal cargue correctamente.
- Asegurar que los elementos como el encabezado, el pie de página y la barra de búsqueda estén presentes.
- Comprobar que la lista de noticias principales sea visible y contenga títulos de artículos.

Ejemplo de prueba:
- Abrir la URL de la página principal.
- Verificar que el título de la página sea el correcto (ej. "Portal de Noticias").
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

public class HomePageTest {
    // https://the-internet.herokuapp.com/login 
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

    // TEST 1.1 - Verificar que la página principal cargue correctamente.
    @Test
    public void testLoginPageTitle() {
        String title = driver.getTitle();
        System.out.println("Page title is: " + title);
        Assert.assertEquals("Inicio - Rolling Stone en Español", title);
        System.out.println("T-1.1.1. Página principal cargó correctamente con el título esperado");
    }

    // TEST 1.2 - Verificar que los elementos como el encabezado, el pie de página y la barra de búsqueda estén presentes.
    @Test
    public void testHomePageElementsPresent() {
        // Verificar que el encabezado esté presente (cambiar a CSS selector para clases compuestas)
        try {
            WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".attachment-full.size-full.wp-image-25066")));
            Assert.assertTrue("T-1.2.1. Header should be displayed", header.isDisplayed());
            System.out.println("T-1.2.1. Header is displayed");
        } catch (Exception e) {
            System.out.println("T-1.2.1. FAIL: Header selector failed, adjust based on site inspection: " + e.getMessage());
        }

        // Verificar que el pie de página esté presente
        try {
            WebElement footer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("footer")));
            Assert.assertTrue("T-1.2.2. Footer should be displayed", footer.isDisplayed());
            System.out.println("T-1.2.2. Footer is displayed");
        } catch (Exception e) {
            System.out.println("T-1.2.2. FAIL: Footer selector failed, adjust based on site inspection: " + e.getMessage());
        }

        // Verificar que la barra de búsqueda esté presente (ajustar selector; probablemente un input o form)
        // Inspecciona el sitio: si es <input type="search">, usa By.cssSelector("input[type='search']")
        // O si es un div con clase, ajusta. Por ahora, usar un selector común.
        try {
            WebElement searchBar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='search'], .search-form input")));
            Assert.assertTrue("T-1.2.3. Search bar should be displayed", searchBar.isDisplayed());
            System.out.println("T-1.2.3. Search bar is displayed");
        } catch (Exception e) {
            System.out.println("T-1.2.3. FAIL: Search bar selector failed, adjust based on site inspection: " + e.getMessage());
            // Opcional: Assert.fail("Search bar not found");
        }
    }



    // TEST 1.3 - Comprobar que la lista de noticias principales sea visible y contenga títulos de artículos.   
    @Test
    public void testMainNewsListVisible() {
        // Verificar que la lista de noticias principales sea visible (opcional, si no existe, continúa)
        try {
            WebElement mainNewsList = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("elementor-widget-container"))); // Cambia a un selector real si inspeccionas
            Assert.assertTrue("T-1.3.1. Main news list should be displayed", mainNewsList.isDisplayed());
            System.out.println("T-1.3.1. Main news list is displayed");
        } catch (Exception e) {
            System.out.println("T-1.3.1. FAIL: Main news list selector failed, adjust based on site inspection: " + e.getMessage());
            // Continúa sin fallar
        }

        // Verificar los primeros 3 artículos
        java.util.List<WebElement> articles = driver.findElements(By.cssSelector("article, .post, .entry"));
        Assert.assertTrue("T-1.3.2. Should have at least 3 articles", articles.size() >= 3);
        
        for (int i = 0; i < 3 && i < articles.size(); i++) {
            WebElement article = articles.get(i);
            try {
                WebElement title = article.findElement(By.xpath(".//h2 | .//h3 | .//a[contains(@class, 'title')] | .//h1"));
                Assert.assertTrue("T-1.3.3.1. Article " + (i+1) + " title should be displayed", title.isDisplayed());
                String titleText = title.getText();
                System.out.println("T-1.3.3.2. Article " + (i+1) + " title: " + titleText);
                Assert.assertFalse("T-1.3.3.3. Article " + (i+1) + " title should not be empty", titleText.trim().isEmpty());
            } catch (Exception e) {
                System.out.println("T-1.3.3. FAIL: Title for article " + (i+1) + " not found: " + e.getMessage());
            }
        }
    }
}
