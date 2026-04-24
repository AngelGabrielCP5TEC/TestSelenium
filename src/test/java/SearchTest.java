/*
Test Case 2 - Pruebas de Búsqueda de Noticias

Tarea: Escribir pruebas para la funcionalidad de búsqueda de noticias.
- Probar la búsqueda de noticias utilizando palabras clave.
- Verificar que los resultados de la búsqueda correspondan a la palabra clave ingresada.
- Probar el caso de búsqueda sin resultados.

Ejemplo de prueba:
- Buscar una noticia específica (ej. "cambio climático").
- Verificar que los resultados incluyan artículos sobre "cambio climático". 
- Buscar una palabra que no exista en el portal y verificar que se muestre un mensaje de "No se encontraron resultados".
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

public class SearchTest {
    
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


    // TEST 2.1 - Probar la búsqueda de noticias utilizando palabras clave y verificar resultados.
    @Test
    public void testSearchWithResults() {
        try {
            // Encontrar la barra de búsqueda
            WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("elementor-search-form-6ef7d4d5")));
            // Ingresar palabra clave (ej. "rock" para resultados)
            searchInput.sendKeys("rock");
            // Enviar la búsqueda (encontrar el form y submit)
            WebElement searchForm = driver.findElement(By.cssSelector("form")); // Asumiendo que hay un form alrededor
            searchForm.submit();
            System.out.println("T-2.1.1. Búsqueda realizada con keyword 'rock'");

            // Esperar que la página de resultados cargue (verificar URL o elementos)
            wait.until(ExpectedConditions.urlContains("rock"));
            System.out.println("T-2.1.2. Página de resultados cargada");

            // Verificar que los resultados incluyan la keyword (buscar en títulos de artículos)
            java.util.List<WebElement> resultArticles = driver.findElements(By.cssSelector("article, .post, .entry"));
            boolean hasResults = false;
            for (WebElement article : resultArticles) {
                try {
                    WebElement title = article.findElement(By.xpath(".//h2 | .//h3 | .//a[contains(@class, 'title')] | .//h1"));
                    String titleText = title.getText().toLowerCase();
                    if (titleText.contains("rock")) {
                        hasResults = true;
                        System.out.println("T-2.1.3. Resultado encontrado: " + titleText);
                        break;
                    }
                } catch (Exception e) {
                    // Ignorar si no hay título
                }
            }
            Assert.assertTrue("T-2.1.4. Debería haber resultados con 'rock'", hasResults);
        } catch (Exception e) {
            System.out.println("T-2.1. FAIL: Error en búsqueda con resultados: " + e.getMessage());
        }
    }

    // TEST 2.2 - Probar el caso de búsqueda sin resultados.
    @Test
    public void testSearchNoResults() {
        try {
            // Encontrar la barra de búsqueda
            WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("elementor-search-form-6ef7d4d5")));
            // Ingresar palabra clave sin resultados (ej. "D4V1D")
            searchInput.sendKeys("D4V1D");
            // Enviar la búsqueda (encontrar el form y submit)
            WebElement searchForm = driver.findElement(By.cssSelector("form")); // Asumiendo que hay un form alrededor
            searchForm.submit();
            System.out.println("T-2.2.1. Búsqueda realizada con keyword 'D4V1D'");

            // Esperar que la página cargue
            wait.until(ExpectedConditions.urlContains("D4V1D"));
            System.out.println("T-2.2.2. Página de resultados cargada");

            // Verificar mensaje de "No se encontraron resultados" usando el div proporcionado
            WebElement noResultsDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ee-posts__nothing-found")));
            String messageText = noResultsDiv.getText();
            Assert.assertTrue("T-2.2.3. Debería mostrar mensaje de no resultados", messageText.contains("El término que buscas no presenta resultados"));
            System.out.println("T-2.2.4. Mensaje de no resultados mostrado: " + messageText);
        } catch (Exception e) {
            System.out.println("T-2.2. FAIL: Error en búsqueda sin resultados: " + e.getMessage());
        }
    }


}
