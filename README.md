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
    <h4>Lombok</h4>
    <p><code>1.18.26</code></p>
    <p>Упрощение кода через аннотации</p>
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
    <code>cd simbirsoft_sdet_project</code>
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
    <code>git clone &lt;ваш-репозиторий-с-тестами&gt;</code><br>
    <code>cd &lt;ваш-тестовый-проект&gt;</code>
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

<br><h2>Запуск через Docker</h2>

<h3>Docker Compose для полного запуска</h3>
<p>Создайте <code>docker-compose.yml</code> в корне тестового проекта:</p>

<div class="code-block">
<code>version: '3.8'</code><br>
<code>services:</code><br>
<code>  api:</code><br>
<code>    image: bondarenkokate73/simbirsoft-api:latest</code><br>
<code>    ports:</code><br>
<code>      - "8080:8080"</code><br>
<code>    environment:</code><br>
<code>      - SPRING_PROFILES_ACTIVE=docker</code><br>
<code>  tests:</code><br>
<code>    build: .</code><br>
<code>    depends_on:</code><br>
<code>      - api</code><br>
<code>    environment:</code><br>
<code>      - BASE_URL=http://api:8080</code>
</div>

<h3>Команды Docker</h3>
<div class="command"><code># Запуск всей системы</code></div>
<div class="command"><code>docker-compose up --build</code></div>

<div class="command"><code># Только API</code></div>
<div class="command"><code>docker-compose up api -d</code></div>

<div class="command"><code># Только тесты</code></div>
<div class="command"><code>docker-compose run tests</code></div>

<br><h2>CI/CD Интеграция</h2>

<h3>GitHub Actions</h3>
<p>Создайте файл <code>.github/workflows/ci.yml</code>:</p>

<div class="code-block">
<code>name: Java CI</code><br>
<code>on: [push, pull_request]</code><br>
<code>jobs:</code><br>
<code>  test:</code><br>
<code>    runs-on: ubuntu-latest</code><br>
<code>    services:</code><br>
<code>      api:</code><br>
<code>        image: bondarenkokate73/simbirsoft-api:latest</code><br>
<code>        ports:</code><br>
<code>          - 8080:8080</code><br>
<code>    steps:</code><br>
<code>    - uses: actions/checkout@v4</code><br>
<code>    - name: Set up JDK 17</code><br>
<code>      uses: actions/setup-java@v4</code><br>
<code>      with:</code><br>
<code>        java-version: '17'</code><br>
<code>        distribution: 'temurin'</code><br>
<code>        cache: maven</code><br>
<code>    - name: Wait for API</code><br>
<code>      run: sleep 30</code><br>
<code>    - name: Run tests</code><br>
<code>      run: mvn test</code><br>
<code>    - name: Generate Allure report</code><br>
<code>      run: mvn allure:report</code>
</div>

<h3>GitLab CI</h3>
<p>Создайте файл <code>.gitlab-ci.yml</code>:</p>

<div class="code-block">
<code>stages:</code><br>
<code>  - test</code><br>
<code>api-test:</code><br>
<code>  stage: test</code><br>
<code>  image: maven:3.8.6-openjdk-17</code><br>
<code>  services:</code><br>
<code>    - name: bondarenkokate73/simbirsoft-api:latest</code><br>
<code>      alias: api</code><br>
<code>  variables:</code><br>
<code>    BASE_URL: http://api:8080</code><br>
<code>  script:</code><br>
<code>    - apt-get update && apt-get install -y curl</code><br>
<code>    - echo "Waiting for API..."</code><br>
<code>    - sleep 30</code><br>
<code>    - mvn test</code><br>
<code>  artifacts:</code><br>
<code>    when: always</code><br>
<code>    paths:</code><br>
<code>      - target/surefire-reports/</code><br>
<code>    reports:</code><br>
<code>      junit: target/surefire-reports/*.xml</code>
</div>

<h3>Jenkins</h3>
<p>Создайте <code>Jenkinsfile</code> в корне проекта:</p>

<div class="code-block">
<code>pipeline {</code><br>
<code>    agent any</code><br>
<code>    tools {</code><br>
<code>        maven 'Maven-3.8.6'</code><br>
<code>        jdk 'OpenJDK-17'</code><br>
<code>    }</code><br>
<code>    stages {</code><br>
<code>        stage('Start API') {</code><br>
<code>            steps {</code><br>
<code>                sh 'docker run -d -p 8080:8080 bondarenkokate73/simbirsoft-api:latest'</code><br>
<code>                sh 'sleep 30'</code><br>
<code>            }</code><br>
<code>        }</code><br>
<code>        stage('Run Tests') {</code><br>
<code>            steps {</code><br>
<code>                sh 'mvn clean test'</code><br>
<code>            }</code><br>
<code>            post {</code><br>
<code>                always {</code><br>
<code>                    junit 'target/surefire-reports/*.xml'</code><br>
<code>                }</code><br>
<code>            }</code><br>
<code>        }</code><br>
<code>    }</code><br>
<code>}</code>
</div>

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

---

<p class="footer">Проект разработан для автоматизации тестирования REST API © 2024</p>
