## Разработка web-приложения "База данных резюме"

Реализованы разные способы хранения резюме. Проект включает в себя следующее:

- **Технологии:** Java 8, GitHub/Git, JUnit, Logging, GSON, JAXB, SQL, PostgreSQL, Сервлеты, HTML, JSP, JSTL, Tomcat,
  Maven и многое другое
- **Различные способы реализации хранения резюме:**
    - в сортированном и не сортированном массиве
    - в коллекциях (List, Map)
    - в файловой системе:
        - с использованием File и Path API
        - в стандартной и кастомной сериализации Java
        - в формате JSON ([Google Gson](https://en.wikipedia.org/wiki/Gson))
        - в формате XML ([JAXB](https://ru.wikipedia.org/wiki/Java_Architecture_for_XML_Binding))
    - в реляционной базе [PostgreSQL](https://ru.wikipedia.org/wiki/PostgreSQL)
- **Установку (деплой) web-приложения:**
    - в контейнер сервлетов [Tomcat](https://ru.wikipedia.org/wiki/Apache_Tomcat)
    - в облачный сервис [Heroku](https://ru.wikipedia.org/wiki/Heroku)

#### В процессе работы рассмотрены следующие темы:

- Подготовка и настройка рабочего окружения
- Подходы, применяемые при разработке ПО
- Обзор инструментов и технологий, используемых Java-разработчиками
- Введение в язык Java: история создания, JDK, JVM, JRE, JIT-компиляция
- Системы управления версиями. Git
- Типы данных
- Введение в объектно-ориентированное программирование
- Принципы ООП
- Классы и объекты
- Классы-обертки
- Модификаторы доступа
- Конструктор
- Структура памяти java-программы: Heap (куча), Stack (стек)
- Пакеты
- Обзор суперкласса Object
- Связь между equals() и hashCode()
- Статические методы и переменные
- Программирование с помощью интерфейсов
- Абстрактные классы
- Сложность алгоритмов
- Паттерн проектирования Template Method
- Работа со строками: String, StringBuilder, StringBuffer
- String literal pool
- Исключения (Exceptions)
- Ключевые слова: this, super
- Reflection
- Аннотации
- Введение в модульное тестирование. JUnit
- Коллекций. Иерархия классов
- Списки (List)
- Множества (Set)
- Ассоциативные массивы (Map)
- Введение в Iterator
- Autoboxing и Unboxing
- Вложенные классы, внутренние классы, локальные классы, анонимные классы
- Введение в лямбда-выражения
- Функциональный интерфейс
- Дженерики (Generic)
- Введение в логирование. Log4J, Java Logging API
- Паттерн проектирования Singleton
- Перечисления (Enum)
- Объектная модель
- Классы работы с датами: Date, Calendar, TimeZone
- Дата и время в Java 8+
- File API
- Освобождение ресурсов: try-with-resources
- Обзор пакета java.io
- Классы чтения/записи потоков: InputStream и OutputStream
- Паттерн проектирования Decorator
- Классы чтения/записи символов: Reader и Writer
- Сериализация объектов
- Обзор пакета java.nio
- Введение в Java 8+ Stream API
- Паттерн проектирования Strategy
- Работа с XML (JAXB)
- Работа с JSON (GSON)
- Классы чтения/записи примитивных типов: DataInputStream и DataOutputStream
- Многопоточность
- Закон Мура и Амдала
- Потоки. Синхронизация доступа
- Ленивая инициализация
- Java Memory Model
- Deadlock
- Обзор классов java.util.concurrent
- Синхронизаторы
- ThreadLocal-переменные
- Сравнение с обменом (Compare-and-swap)
- Введение в реляционные базы данных
- Язык SQL
- Обзор NoSQL баз данных
- Установка и настройка СУБД PostgreSQL
- Работа с базами данных из IDEA
- Конфигурирование базы данных и каталога хранения
- Подключение базы данных к проекту
- Обзор JDBC-архитектуры
- Операции соединения таблиц. JOIN
- Транзакции
- Требования к транзакциям. ACID
- Уровни изоляции транзакций в SQL
- Установка и настройка контейнера сервлетов Tomcat
- Введение в HTML
- Основы протокола HTTP
- Настройка web.xml
- Деплой web-приложения в Tomcat
- Сервлеты, жизненный цикл сервлета
- Создание динамических страниц. JSP
- Расширенные возможности JSP. JSTL
- Redirect и Forward
- CRUD-операции
- Деплой приложения в облачный сервис Heroku
- Загрузка классов в Java. Classloader