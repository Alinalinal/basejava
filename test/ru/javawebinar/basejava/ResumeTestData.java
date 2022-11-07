package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.Map;
import java.util.UUID;

public class ResumeTestData {

    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();

    public static final Resume R1;
    public static final Resume R2;
    public static final Resume R3;
    public static final Resume R4;

    static {
        R1 = getCompletedResume(UUID_1, "Name One");
        R2 = getCompletedResume(UUID_2, "Name Two");
        R3 = getCompletedResume(UUID_3, "Name Three");
        R4 = getCompletedResume(UUID_4, "Name Four");
    }

    public static Resume getCompletedResume(String uuid, String fullName) {
        Resume resume;
        if (uuid == null) {
            resume = new Resume(fullName);
        } else {
            resume = new Resume(uuid, fullName);
        }

        // Контакты
        if (fullName.equals("Name 4")) {
            resume.addContact(ContactType.PHONE_NUMBER, "44444");
            resume.addContact(ContactType.SKYPE, "Skype");
        } else {
            resume.addContact(ContactType.PHONE_NUMBER, "+7(921) 855-0482");
            resume.addContact(ContactType.SKYPE, "skype:grigory.kislin");
            resume.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
            resume.addContact(ContactType.LINKED_IN, "https://www.linkedin.com/in/gkislin");
            resume.addContact(ContactType.GIT_HUB, "https://github.com/gkislin");
            resume.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
            resume.addContact(ContactType.HOME_PAGE, "http://gkislin.ru/");
        }

        // Позиция
        resume.addSection(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));

        // Личные качества
        resume.addSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        // Достижения
        resume.addSection(SectionType.ACHIEVEMENT, new ListSection("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.\n",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.\n",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.\n",
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.\n",
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).\n",
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.\n"));

        // Квалификация
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2\n",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce\n",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle\n",
                "MySQL, SQLite, MS SQL, HSQLDB\n",
                "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy\n",
                "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts\n",
                "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).\n",
                "Python: Django.\n",
                "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js\n",
                "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka\n",
                "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.\n",
                "Инструменты: Maven + plugin development, Gradle, настройка Ngnix\n",
                "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.\n",
                "Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования\n",
                "Родной русский, английский \"upper intermediate\"\n"));

        // Опыт работы
        resume.addSection(SectionType.EXPERIENCE, new OrganizationSection(
                new Organization("Java Online Projects", "http://javaops.ru/",
                        new Organization.Position(LocalDate.of(2013, Month.OCTOBER, 1),
                                LocalDate.of(2020, Month.OCTOBER, 1), "Автор проекта.",
                                "Создание, организация и проведение Java онлайн проектов и стажировок.")),
                new Organization("Wrike", "https://www.wrike.com/",
                        new Organization.Position(LocalDate.of(2014, Month.OCTOBER, 1),
                                LocalDate.of(2016, Month.JANUARY, 1),
                                "Старший разработчик (backend)",
                                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.")),
                new Organization("RIT Center", null,
                        new Organization.Position(LocalDate.of(2012, Month.APRIL, 1),
                                LocalDate.of(2014, Month.OCTOBER, 1), "Java архитектор",
                                "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python")),
                new Organization("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/",
                        new Organization.Position(LocalDate.of(2010, Month.DECEMBER, 1),
                                LocalDate.of(2012, Month.APRIL, 1), "Ведущий программист",
                                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.")),
                new Organization("Yota", "https://www.yota.ru/",
                        new Organization.Position(LocalDate.of(2008, Month.JUNE, 1),
                                LocalDate.of(2010, Month.DECEMBER, 1), "Ведущий специалист",
                                "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)")),
                new Organization("Enkata", "http://enkata.com/",
                        new Organization.Position(LocalDate.of(2007, Month.MARCH, 1),
                                LocalDate.of(2008, Month.JUNE, 1), "Разработчик ПО",
                                "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).")),
                new Organization("Siemens AG", "https://www.siemens.com/ru/ru/home.html",
                        new Organization.Position(LocalDate.of(2005, Month.JANUARY, 1),
                                LocalDate.of(2007, Month.FEBRUARY, 1), "Разработчик ПО",
                                "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).")),
                new Organization("Alcatel", "http://www.alcatel.ru/",
                        new Organization.Position(LocalDate.of(1997, Month.SEPTEMBER, 1),
                                LocalDate.of(2005, Month.JANUARY, 1),
                                "Инженер по аппаратному и программному тестированию", "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)."))));

        // Образование
        resume.addSection(SectionType.EDUCATION, new OrganizationSection(
                new Organization("Coursera", "https://www.coursera.org/course/progfun",
                        new Organization.Position(LocalDate.of(2013, Month.MARCH, 1),
                                LocalDate.of(2013, Month.MAY, 1),
                                "\"Functional Programming Principles in Scala\" by Martin Odersky", null)),
                new Organization("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
                        new Organization.Position(LocalDate.of(2011, Month.MARCH, 1),
                                LocalDate.of(2011, Month.APRIL, 1),
                                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", null)),
                new Organization("Siemens AG", "http://www.siemens.ru/",
                        new Organization.Position(LocalDate.of(2005, Month.JANUARY, 1),
                                LocalDate.of(2005, Month.APRIL, 1),
                                "3 месяца обучения мобильным IN сетям (Берлин)", null)),
                new Organization("Alcatel", "http://www.alcatel.ru/",
                        new Organization.Position(LocalDate.of(1997, Month.SEPTEMBER, 1),
                                LocalDate.of(1998, Month.MARCH, 1),
                                "6 месяцев обучения цифровым телефонным сетям (Москва)", null)),
                new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                        "http://www.ifmo.ru/",
                        new Organization.Position(LocalDate.of(1993, Month.SEPTEMBER, 1),
                                LocalDate.of(1996, Month.JULY, 1),
                                "Аспирантура (программист С, С++)", null),
                        new Organization.Position(LocalDate.of(1987, Month.SEPTEMBER, 1),
                                LocalDate.of(1993, Month.JULY, 1),
                                "Инженер (программист Fortran, C)", null)),
                new Organization("Заочная физико-техническая школа при МФТИ", "http://www.school.mipt.ru/",
                        new Organization.Position(LocalDate.of(1984, Month.SEPTEMBER, 1),
                                LocalDate.of(1987, Month.JUNE, 1),
                                "Закончил с отличием", null))));

        return resume;
    }

    private static void printResume(Resume resume) {
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println(resume.getFullName().toUpperCase());
        System.out.println("-------------------------------------------------------------------------------------");
        for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
            System.out.println(entry.getKey().getTitle().toUpperCase() + " " + entry.getValue());
        }
        System.out.println("-------------------------------------------------------------------------------------");
        for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
            System.out.println(entry.getKey().getTitle().toUpperCase() + " " + entry.getValue());
        }
        System.out.println("-------------------------------------------------------------------------------------");
    }
}
