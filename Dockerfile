FROM maven:3.8.6-openjdk-17

WORKDIR /app

RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

COPY pom.xml .
COPY src ./src

# Скачиваем зависимости (кешируется)
RUN mvn dependency:go-offline -B

ENV BASE_URL=http://localhost:8080
ENV API_PATH=/api

# Скрипт для ожидания приложения и запуска тестов
CMD ["sh", "-c", "\
  echo 'Ожидание запуска приложения на ${BASE_URL}...'; \
  until curl -f ${BASE_URL}/actuator/health > /dev/null 2>&1; do \
    echo 'Приложение еще не запущено, ждем 5 секунд...'; \
    sleep 5; \
  done; \
  echo 'Приложение запущено! Запускаем тесты...'; \
  mvn test -Dbase.url=${BASE_URL} -Dapi.path=${API_PATH}; \
  echo 'Тесты завершены с кодом: $?' \
"]