# UI-tests-playwright

Автотесты UI интернет-магазина пиццерии **pizzeria.skillbox.cc** на Java.
Проект использует **Playwright Java** для управления браузером, **TestNG** для запуска тестов,
**AssertJ** для проверок и **Allure** для отчётности.

## Стек

| Технология | Версия | Назначение |
|---|---|---|
| Java | 21 | Язык разработки |
| Maven | 3.9+ | Система сборки |
| Playwright Java | 1.53.0 | Управление браузером, локаторы, ожидания |
| TestNG | 7.11.0 | Тест-фреймворк, сьюты |
| AssertJ | 3.27.3 | Проверки, `SoftAssertions` |
| Awaitility | 4.3.0 | Ожидания |
| Lombok | 1.18.36 | Геттеры, `@UtilityClass` |
| Logback | 1.5.18 | Логирование |
| Allure | 2.29.1 (плагин 2.12.0) | Отчёты, скриншоты и page source |
| Maven Surefire | 3.2.3 | Запуск тестов |

## Структура проекта

```
src
├── main/java
│   ├── component/              # Переиспользуемые компоненты страниц
│   │   ├── BaseComponent.java
│   │   ├── footer/FooterComponent.java
│   │   └── header/HeaderComponent.java
│   ├── formData/
│   │   └── CheckoutFormData.java    # Данные формы оформления заказа
│   ├── pages/                  # Page Object'ы
│   │   ├── BasePage.java
│   │   ├── account/            # Личный кабинет и заказы
│   │   ├── auth/LoginPage.java
│   │   ├── cart/CartPage.java
│   │   ├── checkout/           # Оформление заказа
│   │   ├── main/MainPage.java
│   │   ├── menu/MenuPage.java
│   │   └── promo/PromoPage.java
│   └── utils/
│       ├── Constants.java
│       └── Keys.java
└── test
    ├── java
    │   ├── testng.xml                 # TestNG-сьют (пакет ui.*)
    │   ├── ui/BaseTest.java           # Жизненный цикл Playwright, скриншоты при падении
    │   ├── ui/account/AccountPageTest.java
    │   ├── ui/cart/CartPageTest.java
    │   ├── ui/menu/MenuPageTest.java
    │   └── utils/                     # Конфигурация, фабрика браузера, тестовые данные
    └── resources
        └── allure.properties
```

## Что покрыто тестами

- **`MenuPageTest`** — сортировка пицц, фильтрация по цене, добавление пиццы в корзину.
- **`CartPageTest`** — изменение количества товара, обновление корзины после изменения
  содержимого, переход к оформлению заказа авторизованным пользователем,
  применение промокода из раздела «Акции».
- **`AccountPageTest`** — смена изображения профиля и проверка, что добавленный заказ
  отображается в истории заказов пользователя.

## Требования

- JDK 21
- Maven 3.9+
- Установленные браузеры Playwright

Установка браузеров:

```bash
mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install"
```

Можно установить только нужный браузер, например `install chromium`.

## Настройка

Параметры запуска задаются системными свойствами в `src/test/java/utils/TestConfig.java`:

| Свойство | По умолчанию | Описание |
|---|---|---|
| `browser` | `chromium` | Браузер: `chromium`, `firefox`, `webkit` |
| `browser.width` | `1920` | Ширина окна |
| `browser.height` | `1080` | Высота окна |
| `headless` | `true` | Запуск без отображения окна браузера |
| `allure.screenshots` | `true` | Прикреплять скриншоты к Allure-отчёту |
| `allure.page.sources` | `true` | Прикреплять page source к Allure-отчёту |

Базовый URL и пути разделов находятся в `src/test/java/utils/TestConstants.java`.

Тестовые данные (логин, регистрация, оформление заказа, бонусы) лежат
в `src/test/java/utils/data/`.

## Запуск тестов

Все тесты (сьют `src/test/java/testng.xml`):

```bash
mvn test
```

Примеры запуска с другими параметрами:

```bash
mvn test -Dbrowser=firefox -Dheadless=false
mvn test -Dbrowser=webkit -Dbrowser.width=1440 -Dbrowser.height=900
```

Запуск отдельного тестового класса:

```bash
mvn test -Dtest=MenuPageTest
```

## Отчёты

- **Surefire**: `target/surefire-reports`
- **Allure**: результаты пишутся в `target/allure-results`

Генерация и просмотр Allure-отчёта:

```bash
mvn allure:report
mvn allure:serve
```

При падении теста `BaseTest` автоматически прикрепляет к отчёту скриншот и HTML-код страницы.

## Особенности реализации

- **Page Object Model**: каждый экран описан отдельным классом в `pages/`,
  общие блоки вынесены в `component/` (header, footer).
- **Изоляция тестов**: браузер создаётся один раз на класс, а `BrowserContext` и `Page` —
  заново перед каждым тестовым методом.
- **Кросс-браузерность**: браузер выбирается через `BrowserFactory` по свойству `browser`.
- **Мягкие проверки**: в тестах используется `SoftAssertions`, поэтому проверяются все
  поля, а не только первое упавшее.
- **Ожидания**: для нестабильных UI-состояний используется Awaitility.
