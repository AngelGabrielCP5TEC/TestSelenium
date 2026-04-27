# TestSelenium - Proyecto de Pruebas Automatizadas

Proyecto de pruebas automatizadas de Selenium con Java y JUnit para validar funcionalidades del portal de noticias Rolling Stone en Español.

## Requisitos Previos

- **Java 22** o superior
- **Maven 3.6+**
- **Git** (opcional, para clonar el repositorio)
- **ChromeDriver** (se descarga automáticamente con WebDriverManager)
- **Chrome** instalado en el sistema

## Instalación Inicial

### 1. Verificar Java instalado

```bash
java -version
```

Si no tienes Java 22 instalado, descárgalo desde: https://www.oracle.com/java/technologies/downloads/

### 2. Verificar Maven instalado

```bash
mvn -version
```

Si no tienes Maven, descárgalo desde: https://maven.apache.org/download.cgi

### 3. Descargar dependencias del proyecto

Navega a la carpeta del proyecto y ejecuta:

```bash
mvn clean install
```

Este comando:
- Limpia archivos compilados anteriores
- Descarga todas las dependencias (Selenium, JUnit, WebDriverManager)
- Compila el proyecto

## Ejecución de Pruebas

### Ejecutar todas las pruebas

```bash
mvn test
```

### Ejecutar una prueba específica por clase

```bash
mvn test -Dtest=HomePageTest
mvn test -Dtest=SearchTest
```

### Ejecutar un test específico dentro de una clase

```bash
mvn test -Dtest=HomePageTest#testLoginPageTitle
mvn test -Dtest=SearchTest#testSearchWithResults
```

## Tests Disponibles

### HomePageTest (Test Case 1 - Página Principal)

Pruebas para verificar la funcionalidad de la página principal del portal.

```bash
mvn test -Dtest=HomePageTest
```

**Tests incluidos:**
- `testLoginPageTitle` - Verifica que la página principal cargue correctamente con el título esperado.
- `testHomePageElementsPresent` - Verifica que el encabezado, pie de página y barra de búsqueda estén presentes.
- `testMainNewsListVisible` - Verifica que la lista de noticias principales sea visible y contenga títulos de artículos (primeros 3).

**Selectores utilizados:**
- Input de búsqueda: `#elementor-search-form-6ef7d4d5`
- Botón de búsqueda: `.elementor-search-form__submit`
- Header: `.attachment-full.size-full.wp-image-25066`
- Footer: `footer`
- Artículos: `article, .post, .entry`
- Títulos de artículos: `.ee-post__title__heading` (con scrollIntoView y espera de texto no vacío)

**URL probada:** `https://es.rollingstone.com/`

---

### SearchTest (Test Case 2 - Búsqueda de Noticias)

Pruebas para la funcionalidad de búsqueda de noticias.

```bash
mvn test -Dtest=SearchTest
```

**Tests incluidos:**
- `testSearchWithResults` - Busca "rock" y verifica que los resultados contengan la palabra clave.
- `testSearchNoResults` - Busca "D4V1D" y verifica el mensaje de "No se encontraron resultados".

**Palabras clave de prueba:**
- Con resultados: "rock"
- Sin resultados: "D4V1D"

**Selectores utilizados:**
- Input de búsqueda: `#elementor-search-form-6ef7d4d5`
- Botón de búsqueda: `.elementor-search-form__submit`
- Mensaje sin resultados: `.ee-posts__nothing-found`
- Títulos de artículos: `.ee-post__title__heading` (con scrollIntoView)

**URL probada:** `https://es.rollingstone.com/`

---

### ArticlePageTest (Test Case 3 - Página de Artículo)

Pruebas para verificar la funcionalidad de la página de detalles de un artículo.

```bash
mvn test -Dtest=ArticlePageTest
```

**Tests incluidos:**
- `testArticleNavigation` - Verifica que al hacer clic en una noticia se redirija a la página del artículo correspondiente.
- `testArticlePageElements` - Verifica que la página del artículo contenga el título, el contenido y la fecha de publicación.

**Funcionalidades probadas:**
- Navegación a página de artículo desde la lista de noticias
- Verificación de título del artículo
- Verificación de contenido del artículo
- Verificación de fecha de publicación

**Selectores utilizados:**
- Artículos: `.ee-post__media__content`
- Título del artículo: `.elementor-heading-title.elementor-size-default` (CSS selector para evitar compound class names)
- Contenedor de artículo: `.elementor-widget-container`
- Fecha de publicación: XPath `//h2[@class='elementor-heading-title elementor-size-default'][contains(text(), ',') and contains(text(), '202')]`

**Mejoras implementadas:**
- Uso de JavaScript click para bypasear overlays de popups
- Manejo explícito de esperas para cambios de URL

**URL probada:** `https://es.rollingstone.com/`

---

### SectionsTest (Test Case 4 - Acceso a Secciones)

Pruebas para verificar el acceso a las secciones principales del portal.

```bash
mvn test -Dtest=SectionsTest
```

**Tests incluidos:**
- `testSectionNavigation` - Verifica que se pueda acceder correctamente a las secciones del portal.
- `testSectionArticlesList` - Verifica que al hacer clic en una sección se muestre la lista de artículos de esa sección.

**Funcionalidades probadas:**
- Navegación a secciones del portal (Política, Economía, Tecnología, etc.)
- Verificación de cambio de URL después de acceder a una sección
- Verificación de página de categoría
- Verificación de lista de artículos en la sección
- Verificación de títulos de artículos en la sección

**Selectores utilizados:**
- Enlaces de secciones: `li[contains(@class, 'menu-item-object-category')] a[@class='elementor-item']` (XPath)
- Widget de posts: `div.elementor-widget-posts-extra`
- Artículos en sección: `div.elementor-widget-posts-extra article`
- Títulos de artículos: `.ee-post__title__heading` (con scrollIntoView y espera de texto no vacío)
- Body (verificación de página de categoría): `body` (atributo class)

**Mejoras implementadas:**
- `scrollIntoView()` para evitar overlays de popups
- Espera explícita para asegurar que el texto del título está poblado antes de acceder
- Selectores específicos en lugar de genéricos para mayor confiabilidad

**URL probada:** `https://es.rollingstone.com/`

---

## Estructura del Proyecto

```
TestSelenium/
├── pom.xml                           # Configuración de Maven y dependencias
├── README.md                         # Documentación del proyecto
├── src/
│   └── test/
│       └── java/
│           ├── HomePageTest.java     # Tests de página principal (Test Case 1)
│           ├── SearchTest.java       # Tests de búsqueda (Test Case 2)
│           ├── ArticlePageTest.java  # Tests de página de artículo (Test Case 3)
│           └── SectionsTest.java     # Tests de acceso a secciones (Test Case 4)
└── target/                           # Archivos compilados y reportes
    └── surefire-reports/             # Reportes de ejecución
```

## Dependencias Principales

- **Selenium WebDriver 4.21.0** - Automatización de navegadores web
- **JUnit 4.13.2** - Framework de pruebas unitarias
- **WebDriverManager 5.8.0** - Gestión automática de drivers

## Convenciones de Naming

Los tests siguen una convención de naming para facilitar el seguimiento:

- **T-1.x.x** = Test Case 1 (Página Principal)
  - T-1.1.1 = Verificación de título de página
  - T-1.2.1 = Verificación de header
  - T-1.2.2 = Verificación de footer
  - T-1.2.3 = Verificación de barra de búsqueda
  - T-1.3.1 = Verificación de lista de noticias
  - T-1.3.2 = Verificación de cantidad de artículos
  - T-1.3.3 = Verificación de títulos de artículos

- **T-2.x.x** = Test Case 2 (Búsqueda)
  - T-2.1.1 = Búsqueda realizada
  - T-2.1.2 = Página de resultados cargada
  - T-2.1.3 = Resultado encontrado
  - T-2.1.4 = Verificación de resultados
  - T-2.2.1 = Búsqueda sin resultados realizada
  - T-2.2.2 = Página de resultados cargada
  - T-2.2.3 = Verificación de mensaje
  - T-2.2.4 = Mensaje de no resultados mostrado

- **T-3.x.x** = Test Case 3 (Página de Artículo)
  - T-3.1.1 = Verificación de existencia de artículos
  - T-3.1.2 = Obtención de URL de página principal
  - T-3.1.3 = Obtención de título del artículo
  - T-3.1.4 = Obtención de URL de página de artículo
  - T-3.1.5 = Verificación de cambio de URL
  - T-3.2.1 = Verificación de existencia de título
  - T-3.2.2 = Verificación de contenido no vacío del título
  - T-3.2.3 = Obtención del texto del título
  - T-3.2.4 = Verificación de existencia de contenido
  - T-3.2.5 = Verificación de contenido visible
  - T-3.2.6 = Verificación de existencia de fecha
  - T-3.2.7 = Verificación de contenido no vacío de fecha
  - T-3.2.8 = Obtención del texto de la fecha

- **T-4.x.x** = Test Case 4 (Acceso a Secciones)
  - T-4.1.1 = Verificación de existencia de enlaces de sección
  - T-4.1.2 = Conteo de enlaces de sección
  - T-4.1.3 = Verificación de validez del enlace de sección
  - T-4.1.4 = Obtención del nombre de la sección
  - T-4.1.5 = Verificación de cambio de URL
  - T-4.1.6 = Confirmación de navegación a sección
  - T-4.2.1 = Verificación de existencia de enlaces de sección
  - T-4.2.2 = Verificación de validez del enlace de sección
  - T-4.2.3 = Obtención del nombre de la sección
  - T-4.2.4 = Confirmación de navegación
  - T-4.2.5 = Verificación de página de categoría
  - T-4.2.6 = Confirmación en página de categoría
  - T-4.2.7 = Verificación de existencia de widget de posts
  - T-4.2.8 = Confirmación de widget de posts
  - T-4.2.9 = Verificación de existencia de artículos
  - T-4.2.10 = Conteo de artículos
  - T-4.2.11 = Verificación de existencia de título de artículo
  - T-4.2.12 = Obtención del texto del título
  - T-4.2.13 = Verificación de contenido no vacío del título

## Reportes de Pruebas

Después de ejecutar las pruebas, los reportes se generan en:

```
target/surefire-reports/
```

Archivos de reporte por clase:
- `HomePageTest.txt` - Reporte en texto
- `TEST-HomePageTest.xml` - Reporte en XML
- `SearchTest.txt` - Reporte en texto
- `TEST-SearchTest.xml` - Reporte en XML
- `ArticlePageTest.txt` - Reporte en texto
- `TEST-ArticlePageTest.xml` - Reporte en XML
- `SectionsTest.txt` - Reporte en texto
- `TEST-SectionsTest.xml` - Reporte en XML

## Notas Importantes

- **WebDriverManager** maneja automáticamente la descarga del ChromeDriver compatible con tu versión de Chrome
- Los tests utilizan **WebDriverWait** para manejar esperas explícitas (timeout de 10 segundos)
- El navegador se cierra automáticamente después de cada test (@After)
- Todos los tests están envueltos en try-catch para robustez
- Los selectores están adaptados para el sitio de Rolling Stone en Español

## Solución de Problemas

### Error: "Could not find or load main class..."
- Verifica que Java 22 esté instalado: `java -version`
- Ejecuta `mvn clean install` nuevamente

### Error: "Chrome driver not found"
- WebDriverManager debería descargarlo automáticamente
- Verifica conexión a internet durante `mvn clean install`
- Intenta ejecutar: `mvn clean install -U` (fuerza actualización de dependencias)

### Error: "Timeout waiting for element"
- El selector puede haber cambiado en el sitio web
- Inspecciona el sitio con DevTools (F12) para encontrar selectores actualizados
- Los tests tienen try-catch que imprimen mensajes de error para depuración

### Los tests abren pero se cierran rápido
- Es comportamiento normal, los tests cierran el navegador automáticamente
- Revisa los logs en la consola para ver qué sucedió

## Test Cases Implementados

- **Test Case 1**: Pruebas de Página Principal ✓
- **Test Case 2**: Pruebas de Búsqueda de Noticias ✓
- **Test Case 3**: Pruebas de Página de Artículo ✓
- **Test Case 4**: Pruebas de Acceso a Secciones Principales ✓

## Más Información

- Documentación Selenium: https://www.selenium.dev/documentation/
- JUnit 4: https://junit.org/junit4/
- WebDriverManager: https://bonigarcia.dev/webdrivermanager/
