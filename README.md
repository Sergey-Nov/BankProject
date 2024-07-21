# Banking Application API

## Описание
Это приложение представляет собой банковскую систему, позволяющую создавать учетные записи, вносить, снимать и переводить деньги между счетами, а также просматривать историю транзакций.

## Технологии
•  Java

•  Spring Boot

•  Spring Data JPA

•  Spring Security

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
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

Шаг 3: Заполнение базы данных исходными данными
Вариант 1: Использование data.sql
Создайте файл src/main/resources/data.sql и добавьте в него SQL-запросы для вставки исходных данных:

INSERT INTO account (id, account_number, balance, recipient_name, pin_code) VALUES
(1, '1234567890', 1000.00, 'Ivan Ivanov', '1234'),
(2, '0987654321', 2000.00, 'Den Kruglov', '5678');

INSERT INTO transaction (id, account_id, amount, type, timestamp) VALUES
(1, 1, 1000.00, 'DEPOSIT', NOW()),
(2, 2, 2000.00, 'DEPOSIT', NOW());

Вариант 2: Использование сервиса инициализации данных
Создайте сервис для инициализации данных при запуске приложения:

package org.example.bankingapp.service;

import org.example.bankingapp.model.Account;
import org.example.bankingapp.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Service
public class DataInitializationService {

@Autowired
private AccountRepository accountRepository;

@PostConstruct
public void init() {
Account account1 = new Account("John Doe", "1234");
account1.setBalance(new BigDecimal("1000.00"));
accountRepository.save(account1);

Account account2 = new Account("Jane Smith", "5678");
account2.setBalance(new BigDecimal("2000.00"));
accountRepository.save(account2);
}
}

Шаг 4: Запуск приложения
mvn spring-boot:run

Использование API
Создание учетной записи
POST /api/accounts

{
"recipientName": "John Doe",
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

Получение всех счетов (только для администраторов)
GET /api/accounts/admin/all

Принятые решения при разработке:
1. Использование Spring Boot и Spring Data JPA: Эти технологии были выбраны для упрощения разработки и управления базой данных.
2. PostgreSQL: Выбор PostgreSQL в качестве базы данных обусловлен его надежностью и широкими возможностями.
3. Spring Security: Добавлен уровень авторизации с разграничением по ролям для обеспечения безопасности.
4. Валидация PIN-кода: Все операции по списанию средств требуют правильного PIN-кода для обеспечения безопасности.
5. История транзакций: Все изменения баланса сохраняются в истории транзакций для обеспечения прозрачности и отслеживания операций.
6. Обработка ошибок: Включена обработка ошибок и возвращение соответствующих кодов ошибок для всех операций.
7. Заполнение базы данных исходными данными: Добавлены сценарии для заполнения базы данных исходными данными при запуске приложения.

Если у вас возникнут вопросы или потребуется дополнительная помощь, пишите: https://join.skype.com/invite/CyTQpYrRyGqg
