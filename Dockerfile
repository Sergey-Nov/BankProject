# Используем официальный образ OpenJDK
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файл сборки Maven и исходный код
COPY pom.xml .
COPY src ./src

# Скачиваем зависимости и собираем проект
RUN mvn dependency:go-offline -B
RUN mvn package -DskipTests

# Указываем команду для запуска приложения
CMD ["java", "-jar", "target/bankingapp-0.0.1-SNAPSHOT.jar"]