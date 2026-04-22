# TestSelenium - Proyecto de Pruebas Automatizadas

Proyecto de pruebas automatizadas de Selenium con Java y JUnit para validar funcionalidades de login en aplicaciones web.

## Requisitos Previos

- **Java 22** o superior
- **Maven 3.6+**
- **Git** (opcional, para clonar el repositorio)
- **ChromeDriver** (se descarga automáticamente con WebDriverManager)

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

### Ejecutar una prueba específica

```bash
mvn test -Dtest=LoginTest
```

### Ejecutar un test específico dentro de la clase

```bash
mvn test -Dtest=LoginTest#testLoginSuccess
```

## Estructura del Proyecto

```
TestSelenium/
├── pom.xml                           # Configuración de Maven y dependencias
├── src/
│   └── test/
│       └── java/
│           └── LoginTest.java        # Tests de Selenium
└── target/                           # Archivos compilados y reportes
    └── surefire-reports/             # Reportes de ejecución
```

## Dependencias Principales

- **Selenium WebDriver 4.21.0** - Automatización de navegadores web
- **JUnit 4.13.2** - Framework de pruebas unitarias
- **WebDriverManager 5.8.0** - Gestión automática de drivers

## Reportes de Pruebas

Después de ejecutar las pruebas, los reportes se generan en:

```
target/surefire-reports/
```

Archivos de reporte:
- `LoginTest.txt` - Reporte en texto
- `TEST-LoginTest.xml` - Reporte en XML

## Notas Importantes

- **WebDriverManager** maneja automáticamente la descarga del ChromeDriver compatible con tu versión de Chrome
- Los tests utilizan **WebDriverWait** para manejar esperas explícitas
- El navegador se cierra automáticamente después de cada test

## Solución de Problemas

### Error: "Could not find or load main class..."
- Verifica que Java 22 esté instalado: `java -version`
- Ejecuta `mvn clean install` nuevamente

### Error: "Chrome driver not found"
- WebDriverManager debería descargarlo automáticamente
- Verifica conexión a internet durante `mvn clean install`
- Intenta ejecutar: `mvn clean install -U` (fuerza actualización de dependencias)

### Los tests abren pero se cierran rápido
- Es comportamiento normal, los tests cierran el navegador automáticamente
- Revisa los logs en la consola para ver qué sucedió

## Más Información

- Documentación Selenium: https://www.selenium.dev/documentation/
- JUnit 4: https://junit.org/junit4/
- WebDriverManager: https://bonigarcia.dev/webdrivermanager/
