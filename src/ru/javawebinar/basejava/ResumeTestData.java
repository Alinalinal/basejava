package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");

        resume.addContact(ContactType.PHONE_NUMBER, new Link("+7(921) 855-0482", null));
        resume.addContact(ContactType.SKYPE, new Link("grigory.kislin", "skype:grigory.kislin"));
        resume.addContact(ContactType.EMAIL, new Link("gkislin@yandex.ru", "gkislin@yandex.ru"));
        resume.addContact(ContactType.LINKED_IN, new Link("", "https://www.linkedin.com/in/gkislin"));
        resume.addContact(ContactType.GIT_HUB, new Link("", "https://github.com/gkislin"));
        resume.addContact(ContactType.STACKOVERFLOW, new Link("", "https://stackoverflow.com/users/548473"));
        resume.addContact(ContactType.HOME_PAGE, new Link("", "http://gkislin.ru/"));

        resume.addSection(SectionType.OBJECTIVE, new StringSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));

        resume.addSection(SectionType.PERSONAL, new StringSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        resume.addSection(SectionType.ACHIEVEMENT, new ListSection("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.",
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.",
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).",
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."));

        resume.addSection(SectionType.QUALIFICATIONS, new ListSection("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,",
                "MySQL, SQLite, MS SQL, HSQLDB",
                "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,",
                "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,",
                "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).",
                "Python: Django.",
                "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js",
                "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka",
                "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.",
                "Инструменты: Maven + plugin development, Gradle, настройка Ngnix,",
                "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.",
                "Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования",
                "Родной русский, английский \"upper intermediate\""));


        Experience experience1 = new Experience(new Link("Java Online Projects", "http://javaops.ru/"),
                LocalDate.of(2013, Month.OCTOBER, 1), LocalDate.now(),
                "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок.");
        Experience experience2 = new Experience(new Link("Wrike", "https://www.wrike.com/"),
                LocalDate.of(2014, Month.OCTOBER, 1), LocalDate.of(2016, Month.JANUARY, 1),
                "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        Experience experience3 = new Experience(new Link("RIT Center", null),
                LocalDate.of(2012, Month.APRIL, 1), LocalDate.of(2014, Month.OCTOBER, 1),
                "Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");
        Experience experience4 = new Experience(new Link("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/"),
                LocalDate.of(2010, Month.DECEMBER, 1), LocalDate.of(2012, Month.APRIL, 1),
                "Ведущий программист", "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.");
        Experience experience5 = new Experience(new Link("Yota", "https://www.yota.ru/"),
                LocalDate.of(2008, Month.JUNE, 1), LocalDate.of(2010, Month.DECEMBER, 1),
                "Ведущий специалист", "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)");
        Experience experience6 = new Experience(new Link("Enkata", "http://enkata.com/"),
                LocalDate.of(2007, Month.MARCH, 1), LocalDate.of(2008, Month.JUNE, 1),
                "Разработчик ПО", "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).");
        Experience experience7 = new Experience(new Link("Siemens AG", "https://www.siemens.com/ru/ru/home.html"),
                LocalDate.of(2005, Month.JANUARY, 1), LocalDate.of(2007, Month.FEBRUARY, 1),
                "Разработчик ПО", "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).");
        Experience experience8 = new Experience(new Link("Alcatel", "http://www.alcatel.ru/"),
                LocalDate.of(1997, Month.SEPTEMBER, 1), LocalDate.of(2005, Month.JANUARY, 1),
                "Инженер по аппаратному и программному тестированию", "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).");
        resume.addSection(SectionType.EXPERIENCE, new ExperienceListSection(experience1, experience2, experience3,
                experience4, experience5, experience6, experience7, experience8));

        Experience education1 = new Experience(new Link("Coursera", "https://www.coursera.org/course/progfun"),
                LocalDate.of(2013, Month.MARCH, 1), LocalDate.of(2013, Month.MAY, 1),
                "\"Functional Programming Principles in Scala\" by Martin Odersky");
        Experience education2 = new Experience(new Link("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366"),
                LocalDate.of(2011, Month.MARCH, 1), LocalDate.of(2011, Month.APRIL, 1),
                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"");
        Experience education3 = new Experience(new Link("Siemens AG", "http://www.siemens.ru/"),
                LocalDate.of(2005, Month.JANUARY, 1), LocalDate.of(2005, Month.APRIL, 1),
                "3 месяца обучения мобильным IN сетям (Берлин)");
        Experience education4 = new Experience(new Link("Alcatel", "http://www.alcatel.ru/"),
                LocalDate.of(1997, Month.SEPTEMBER, 1), LocalDate.of(1998, Month.MARCH, 1),
                "6 месяцев обучения цифровым телефонным сетям (Москва)");
        Experience education5 = new Experience(new Link("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/"),
                LocalDate.of(1993, Month.SEPTEMBER, 1), LocalDate.of(1996, Month.JULY, 1),
                "Аспирантура (программист С, С++)");
        Experience education6 = new Experience(new Link("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/"),
                LocalDate.of(1987, Month.SEPTEMBER, 1), LocalDate.of(1993, Month.JULY, 1),
                "Инженер (программист Fortran, C)");
        Experience education7 = new Experience(new Link("Заочная физико-техническая школа при МФТИ", "http://www.school.mipt.ru/"),
                LocalDate.of(1984, Month.SEPTEMBER, 1), LocalDate.of(1987, Month.JUNE, 1),
                "Закончил с отличием");
        resume.addSection(SectionType.EDUCATION, new ExperienceListSection(education1, education2, education3, education4,
                education5, education6, education7));

        printResume(resume);
    }

    private static void printResume(Resume resume) {
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println(resume.getFullName().toUpperCase() + '\n');

        for (Map.Entry<ContactType, Link> entry : resume.getContacts().entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        System.out.println("-------------------------------------------------------------------------------------\n");

        for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
            System.out.println(entry.getKey() + "\n");
            System.out.println(entry.getValue());
            System.out.println("-------------------------------------------------------------------------------------\n");
        }
    }
}
