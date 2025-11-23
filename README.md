# Task SDET API Simbirsoft

<h1>API Automation Testing Project</h1>
<p class="subtitle">Автоматизированное тестирование REST API</p>

<ul class="nav-list">
    <li><a href="#description">Описание</a></li>
    <li><a href="#tech-stack">Технологии</a></li>
    <li><a href="#setup">Установка</a></li>
    <li><a href="#docker">Docker</a></li>
    <li><a href="#ci-cd">CI/CD</a></li>
    <li><a href="#testing">Тестирование</a></li>
    <li><a href="#reports">Отчеты</a></li>
</ul>

<h2>Описание проекта</h2>
<p>Проект автоматизированного тестирования REST API для управления сущностями</p>

<h3>Основные возможности:</h3>
<ul>
    <li>✅ Создание сущностей с различными данными</li>
    <li>✅ Получение списка всех сущностей</li>
    <li>✅ Поиск сущностей по ID</li>
    <li>✅ Обновление данных сущностей</li>
    <li>✅ Удаление сущностей</li>
    <li>✅ Обработка различных статус-кодов</li>
    <li>✅ Allure отчеты с детализацией</li>
</ul>

<h2>Технологический стек</h2>
<div class="tech-item">
    <h4>Java</h4>
    <p><code>17+</code></p>
    <p>Основной язык программирования</p>
</div>
<div class="tech-item">
    <h4>JUnit 5</h4>
    <p><code>5.9.2</code></p>
    <p>Фреймворк для тестирования</p>
</div>
<div class="tech-item">
    <h4>REST Assured</h4>
    <p><code>5.3.0</code></p>
    <p>Библиотека для REST API тестирования</p>
</div>
<div class="tech-item">
    <h4>Maven</h4>
    <p><code>3.9+</code></p>
    <p>Система сборки и управления зависимостями</p>
</div>
<div class="tech-item">
    <h4>Allure</h4>
    <p><code>2.21.0</code></p>
    <p>Генерация отчетов</p>
</div>
<div class="tech-item">
    <h4>Owner</h4>
    <p><code>1.0.12</code></p>
    <p>Управление конфигурацией</p>
</div>

<br><h2>Быстрый старт</h2>

<h3>Шаг 1: Клонирование API приложения</h3>
<div class="code-block">
    <code>git clone https://github.com/bondarenkokate73/simbirsoft_sdet_project.git</code><br>
</div>

<h3>Шаг 2: Запуск API через Docker Compose</h3>
<div class="code-block">
    <code>docker-compose up -d</code><br>
    <code># Проверяем что приложение запустилось</code><br>
    <code>curl http://localhost:8080/actuator/health</code>
</div>

<h3>Шаг 3: Клонирование тестового проекта</h3>
<div class="code-block">
    <code>cd ..</code><br>
    <code>git clone https://github.com/seecret1/Task_SDET_API_Simbirsoft.git</code><br>
</div>

<h3>Шаг 4: Запуск тестов</h3>
<div class="code-block">
    <code>mvn clean test</code><br>
    <code># Или с генерацией отчетов Allure</code><br>
    <code>mvn test allure:report</code>
</div>

<br><h2>Установка и настройка</h2>

<h3>Предварительные требования</h3>
<ul>
    <li><code>Java 17</code> или выше</li>
    <li><code>Maven 3.9+</code></li>
    <li><code>Git</code></li>
    <li><code>Docker</code> и <code>Docker Compose</code></li>
    <li>Запущенный API сервер на <code>localhost:8080</code></li>
</ul>

<h3>Конфигурация приложения</h3>
<p>Файл <code>src/test/resources/config/application.properties</code> содержит настройки:</p>
<div class="code-block">
    <code>base.url=http://localhost:8080</code><br>
    <code>api.path=/api</code>
</div>

<h3>Проверка окружения</h3>
<div class="code-block">
    <code>mvn -v</code><br>
    <code>java -version</code><br>
    <code>docker --version</code><br>
    <code>docker-compose --version</code>
</div>

<br><h2>CI/CD Интеграция</h2>
<ul>
    <li>GitHub Actions, путь: <code>.github/workflows/ci.yml</code></li>
    <li>GitLab CI <code>.gitlab-ci.yml</code> в корне проекта</li>
    <li>Jenkins <code>Jenkinsfile</code> в корне проекта</li>
</ul>

<br><h2>Запуск тестов</h2>

<h3>Все тесты</h3>
<div class="command"><code>mvn test</code></div>

<h3>С детализированным логированием</h3>
<div class="command"><code>mvn test -X</code></div>

<h3>Без кэширования</h3>
<div class="command"><code>mvn clean test</code></div>

<h3>Конкретный тестовый класс</h3>
<div class="command"><code>mvn -Dtest=UserTest test</code></div>

<h3>Конкретный тестовый метод</h3>
<div class="command"><code>mvn -Dtest=UserTest#createEntity test</code></div>

<h3>Тест-кейсы</h3>
<div class="test-case success">
    <h4><code>getAllEntities</code></h4>
    <p>Получение списка всех сущностей</p>
    <p><strong>Проверка:</strong> Наличие заголовков сущностей</p>
</div>
<div class="test-case success">
    <h4><code>getAllEntities_Code500</code></h4>
    <p>Проверка обработки ошибок сервера</p>
    <p><strong>Ожидание:</strong> Статус код 500</p>
</div>
<div class="test-case success">
    <h4><code>createEntity</code></h4>
    <p>Создание новой сущности</p>
    <p><strong>Данные:</strong> Случайные значения</p>
</div>
<div class="test-case success">
    <h4><code>createEntityAndCheck</code></h4>
    <p>Создание и проверка сущности</p>
    <p><strong>Проверка:</strong> Корректность созданных данных</p>
</div>
<div class="test-case success">
    <h4><code>updateEntity</code></h4>
    <p>Обновление данных сущности</p>
    <p><strong>Метод:</strong> PATCH запрос</p>
</div>
<div class="test-case success">
    <h4><code>deleteEntity</code></h4>
    <p>Удаление сущности</p>
    <p><strong>Проверка:</strong> Отсутствие после удаления</p>
</div>

<br><h2>Структура проекта</h2>

<h3>Основные пакеты</h3>
<div class="code-block">
<code>src/test/java/api/tests/</code> - тестовые классы<br>
<code>src/test/java/api/dto/</code> - Data Transfer Objects<br>
<code>src/test/java/api/util/</code> - утилиты и конфигурация<br>
<code>src/test/resources/config/</code> - файлы конфигурации
</div>

<h3>Ключевые классы</h3>
<ul>
    <li><strong>UserTest</strong> - основные тесты API</li>
    <li><strong>BaseTest</strong> - базовый класс для тестов</li>
    <li><strong>Entity</strong> - модель сущности</li>
    <li><strong>Addition</strong> - дополнительная информация</li>
    <li><strong>Specifications</strong> - спецификации REST Assured</li>
    <li><strong>Configuration</strong> - управление конфигурацией</li>
</ul>

<br><h2>Генерация отчетов</h2>

<h3>Allure отчеты</h3>
<p>Генерация HTML отчета</p>
<div class="command"><code>mvn allure:report</code></div>
<p>Запуск веб-сервера с отчетами</p>
<div class="command"><code>mvn allure:serve</code></div>

<h3>Просмотр результатов</h3>
<ul>
    <li>Отчеты сохраняются в <code>target/allure-results</code></li>
    <li>HTML отчеты генерируются в <code>target/site/allure-maven-plugin</code></li>
    <li>Детализация по каждому тест-кейсу</li>
    <li>Временные метки выполнения</li>
    <li>Логи запросов и ответов</li>
</ul>

<h3>Требования к API серверу</h3>
<ul>
    <li><strong>URL:</strong> <code>http://localhost:8080</code></li>
    <li><strong>Base path:</strong> <code>/api</code></li>
    <li><strong>Методы:</strong> GET, POST, PATCH, DELETE</li>
    <li><strong>Формат данных:</strong> JSON</li>
</ul>

<h2>Разработка и расширение</h2>

<h3>Добавление новых тестов</h3>
<p>Для добавления новых тестов создайте класс в пакете <code>api.tests</code> и унаследуйте от <code>BaseTest</code></p>

<h3>Конфигурация</h3>
<p>Настройки добавляются в <code>application.properties</code> и обрабатываются через интерфейс <code>Configuration</code></p>

<br><h2>Устранение неполадок</h2>

<h3>Частые проблемы</h3>
<div class="troubleshooting">
    <h4>❌ API сервер недоступен</h4>
    <p><strong>Решение:</strong> Проверьте запущен ли сервер на localhost:8080</p>
    <p><strong>Проверка:</strong> <code>curl http://localhost:8080/actuator/health</code></p>
</div>
<div class="troubleshooting">
    <h4>❌ Ошибки компиляции</h4>
    <p><strong>Решение:</strong> Проверьте версию Java (должна быть 17+)</p>
</div>
<div class="troubleshooting">
    <h4>❌ Тесты падают с 500 ошибкой</h4>
    <p><strong>Решение:</strong> Проверьте корректность данных и формат запросов</p>
</div>
<div class="troubleshooting">
    <h4>❌ Docker образ не найден</h4>
    <p><strong>Решение:</strong> Убедитесь что образ bondarenkokate73/simbirsoft-api существует</p>
</div>

<h3>Логирование</h3>
<p>Для детального логирования используйте флаг <code>-X</code>:</p>
<div class="command"><code>mvn test -X</code></div>
