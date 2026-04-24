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
│           ├── LoginTest.java        # Tests de login (original)
│           ├── HomePageTest.java     # Tests de página principal (Test Case 1)
│           └── SearchTest.java       # Tests de búsqueda (Test Case 2)
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

## Próximos Test Cases (Pendientes)

- **Test Case 3**: Pruebas de Página de Artículo
- **Test Case 4**: Pruebas de Acceso a Secciones Principales

## Más Información

- Documentación Selenium: https://www.selenium.dev/documentation/
- JUnit 4: https://junit.org/junit4/
- WebDriverManager: https://bonigarcia.dev/webdrivermanager/
