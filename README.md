# cucumber-selenium-clean-test-architecture
Cucumber and Selenium Clean Test Automation Architecture 
# 🧪 Test Automation Framework

This project is a **Selenium-based Test Automation Framework** built using **Cucumber (BDD)** and **TestNG**, with support for **Allure Reporting**, **Owner for configuration management**, and **Docker-based WebDriver containers**.

---

## 📦 Tech Stack

- **Selenium WebDriver** – UI automation
- **Cucumber (Gherkin)** – BDD-style test definitions
- **TestNG** – Test execution framework
- **Allure** – Test reporting
- **Owner** – Config management
- **TestContainers** – Containerized test runs
- **WebDriverManager** – Driver binaries handling
- **Gradle** – Build & dependency management

---

## 📁 Project Structure

```
.
├── build.gradle
├── src
│   ├── main
│   │   └── java
│   │       └── framework            # Core framework classes
│   └── test
│       ├── java
│       │   ├── runners             # Test runners
│       │   ├── pages               # Page Object Models
│       │   └── step_definitions    # Cucumber step implementations + hooks
│       └── resources
│           └── features            # Gherkin feature files
│           └── suites              # TestNG XML suite files
└── README.md
```

---

## 🧪 Running Tests

You can execute specific test scopes using **Gradle tasks**, defined in `build.gradle`.

### 🏷️ Available Test Tasks

Example from `build.gradle`:

```groovy
def testRunner = '**/WebTestRunner*'
def webTestFeatureFilesPath = 'src/test/resources/features/'

tasks.register("sanity", Test) {
    systemProperty("cucumber.features", webTestFeatureFilesPath + "login")
    systemProperty("cucumber.filter.tags", "@sanity")
    include testRunner
}
```

You can define similar tasks for `@smoke`, `@regression`, etc.

### ▶️ Run Command

```bash
./gradlew sanity
```

Replace `sanity` with any registered task name.

---

## ⚙️ Configuration Management

Using the **Owner** library:

```java
@Config.Sources({"system:properties", "classpath:ProjectConfig.properties",}) 
@Config.LoadPolicy(Config.LoadType.MERGE)
public interface ProjectConfig extends Config {
    String url();
    @DefaultValue("chrome") String browser();
    @DefaultValue("local") String target();
    @DefaultValue("5") Integer loadingTimeout();
    @DefaultValue("build") String outputFolder();
}
```

**We can access following `properties` in our code:**

```
browser=chrome
target=local
loadingTimeout=5
outputFolder=build
```

---

## 🧠 Driver Initialization

Driver is managed centrally via `DriverManager`. It either starts a local WebDriver or launches a container session via **TestContainers**:

```java
if (Target.valueOf(target) == Target.CONTAINERS) {
    webDriver = BrowserType.valueOf(browserName).getContainerDriver();
} else {
    webDriver = BrowserType.valueOf(browserName).getLocalDriver();
}
```
**Note:** Check out `BrowserType` for driver resolution

## 🥒 Cucumber + TestNG Runner

Configured runner using `AbstractTestNGCucumberTests`:

```java
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "step_definitions",
    plugin = {
        "pretty",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
    }
)
public class WebTestRunner extends AbstractTestNGCucumberTests {}
```

---

## ✅ Clean Cucumber Structure

---

## 🧱 Base Page Abstraction

The framework uses an abstract generic `BasePage<T>` class to enforce consistency and provide common functionality across all Page Objects.

### 🔧 Purpose

- Centralizes access to `WebDriver` and `Waiter`.
- Provides reusable utilities like:
    - `navigateTo(String url)`
    - `waitForPageToLoad()`
    - `elementLocatedBy(By locator)`
- Enables **fluent chaining** using generic typing (`T extends BasePage<T>`).

---

## 🪝 Hooks & Test Context Management

To ensure proper setup and teardown of WebDriver across scenarios, the framework uses Cucumber Hooks combined with a shared `TestContext` object.

---

### 🔄 Hooks.java

```java
public class Hooks {
    TestContext testContext;

    public Hooks(TestContext testContext) {
        this.testContext = testContext;
    }

    @Before
    public void setUp() {
        testContext.setDriver();
    }

    @After
    public void captureScreenShotOnFailure(Scenario scenario) {
        if (scenario.isFailed()) {
            WebDriver driver = testContext.getDriver();
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(
                String.format("%s_%s", LocalDateTime.now(), driver.getTitle()),
                new ByteArrayInputStream(screenshot)
            );
        }
    }
}
```

- **@Before**: Initializes WebDriver before each scenario.
- **@After**: Captures a screenshot on failure and attaches it to the Allure report.

---

### 🧹 CleanupSteps.java

```java
public class CleanupSteps {
    TestContext testContext;

    public CleanupSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @After
    public void tearDown() {
        testContext.quitDriver();
    }
}
```

- Ensures browser is properly closed after each scenario.

---

### 🧠 TestContext.java

```java
public class TestContext {
    private WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver() {
        driver = DriverManager.getDriver();
    }

    public void quitDriver() {
        DriverManager.quitDriver();
    }
}
```

- Acts as a shared state holder for WebDriver.
- Passed to step definitions using Cucumber's dependency injection.


---

## 📊 Allure Reports

Allure reports are automatically generated after each test run.

To execute and generate report:

```bash
./gradlew sanity
```

Reports are saved in:

```
build/reports/allure-report/[timestamp]/
```

To clean old reports:

```bash
./gradlew deleteReports
```

---

## 🐳 Docker Integration

If `target=containers` is specified in the config, the tests are run using **WebDriver containers**:

```java
WebDriver webDriver = BrowserType.valueOf(browserName).getContainerDriver();
```

Useful for CI/CD pipelines and headless executions.

---

## 🚀 Future Enhancements

- Parallel test execution
- CI integration (GitHub Actions, Jenkins)
- Cloud testing support (BrowserStack, SauceLabs)

---

> Happy Testing! 🚀
