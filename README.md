# Banking Application API

## Описание
Это приложение представляет собой банковскую систему, позволяющую создавать учетные записи, вносить, снимать и переводить деньги между счетами, а также просматривать историю транзакций.

## Технологии
•  Java

•  Spring Boot

•  Spring Data JPA

•  PostgreSQL


## Установка и настройка

### Шаг 1: Клонирование репозитория
```bash
git clone https://github.com/xJOHNJAx/BankProject.git
cd BankProject

Шаг 2: Настройка базы данных
Создайте базу данных PostgreSQL и настройте подключение в файле src/main/resources/application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/banking_app
spring.datasource.username=aston
spring.datasource.password=aston
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

Шаг 3: Запуск приложения
mvn spring-boot:run

Использование API
Создание учетной записи
POST /api/accounts

{
"recipientName": "Ivan Ivanov",
"pinCode": "1234"
}

Внесение средств
POST /api/accounts/{id}/deposit

{
"amount": 100.00
}

Снятие средств
POST /api/accounts/{id}/withdraw

{
"amount": 50.00,
"pinCode": "1234"
}

Перевод средств
POST /api/accounts/{id}/transfer

{
"toAccountId": 2,
"amount": 25.00,
"pinCode": "1234"
}

Получение всех счетов
GET /api/accounts/all

Получение счета по ID
GET /api/accounts/{id}

Получение транзакций по ID счета
GET /api/accounts/{id}/transactions

Принятые решения при разработке:
1. Использование Spring Boot и Spring Data JPA: Эти технологии были выбраны для упрощения разработки и управления базой данных.
2. PostgreSQL: Выбор PostgreSQL в качестве базы данных обусловлен его надежностью и широкими возможностями.
3. Валидация PIN-кода: Все операции по списанию средств требуют правильного PIN-кода для обеспечения безопасности.
4. История транзакций: Все изменения баланса сохраняются в истории транзакций для обеспечения прозрачности и отслеживания операций.
5. Обработка ошибок: Включена обработка ошибок и возвращение соответствующих кодов ошибок для всех операций.

Если у вас возникнут вопросы или потребуется дополнительная помощь, пишите: https://join.skype.com/invite/CyTQpYrRyGqg
