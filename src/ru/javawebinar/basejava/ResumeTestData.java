package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.DateUtil;

import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");

        resume.setContact(ContactType.PHONE_NUMBER, "+7(921) 855-0482");
        resume.setContact(ContactType.SKYPE, "grigory.kislin"); // "skype:grigory.kislin"
        resume.setContact(ContactType.EMAIL, "gkislin@yandex.ru"); // "gkislin@yandex.ru"
        resume.setContact(ContactType.LINKED_IN, ""); // "https://www.linkedin.com/in/gkislin"
        resume.setContact(ContactType.GIT_HUB, ""); // "https://github.com/gkislin")
        resume.setContact(ContactType.STACKOVERFLOW, ""); // "https://stackoverflow.com/users/548473"
        resume.setContact(ContactType.HOME_PAGE, ""); // "http://gkislin.ru/"

        resume.setSection(SectionType.OBJECTIVE, new StringSection("Ведущий стажировок и корпоративного обучения " +
                "по Java Web и Enterprise технологиям"));

        resume.setSection(SectionType.PERSONAL, new StringSection("Аналитический склад ума, сильная логика, " +
                "креативность, инициативность. Пурист кода и архитектуры."));

        List<String> achievementContent = new ArrayList<>();
        Collections.addAll(achievementContent, "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.",
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.",
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).",
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        resume.setSection(SectionType.ACHIEVEMENT, new ListSection(achievementContent));

        List<String> qualificationsContent = new ArrayList<>();
        Collections.addAll(qualificationsContent, "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
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
                "Родной русский, английский \"upper intermediate\"");
        resume.setSection(SectionType.QUALIFICATIONS, new ListSection(qualificationsContent));

        Organization organization1 = new Organization("Java Online Projects", "http://javaops.ru/",
                DateUtil.of(2014, Month.OCTOBER), DateUtil.of(2016, Month.JANUARY),
                "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        Organization organization2 = new Organization("Wrike", "https://www.wrike.com/",
                DateUtil.of(2014, Month.OCTOBER), DateUtil.of(2016, Month.JANUARY),
                "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        Organization organization3 = new Organization("RIT Center", null,
                DateUtil.of(2012, Month.APRIL), DateUtil.of(2014, Month.OCTOBER),
                "Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");
        Organization organization4 = new Organization("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/",
                DateUtil.of(2010, Month.DECEMBER), DateUtil.of(2012, Month.APRIL),
                "Ведущий программист", "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.");
        Organization organization5 = new Organization("Yota", "https://www.yota.ru/",
                DateUtil.of(2008, Month.JUNE), DateUtil.of(2010, Month.DECEMBER),
                "Ведущий специалист", "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)");
        Organization organization6 = new Organization("Enkata", "http://enkata.com/",
                DateUtil.of(2007, Month.MARCH), DateUtil.of(2008, Month.JUNE),
                "Разработчик ПО", "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).");
        Organization organization7 = new Organization("Siemens AG", "https://www.siemens.com/ru/ru/home.html",
                DateUtil.of(2005, Month.JANUARY), DateUtil.of(2007, Month.FEBRUARY),
                "Разработчик ПО", "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).");
        Organization organization8 = new Organization("Alcatel", "http://www.alcatel.ru/",
                DateUtil.of(1997, Month.SEPTEMBER), DateUtil.of(2005, Month.JANUARY),
                "Инженер по аппаратному и программному тестированию", "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).");
        List<Organization> experienceContent = new ArrayList<>();
        Collections.addAll(experienceContent, organization1, organization2, organization3, organization4, organization5,
                organization6, organization7, organization8);
        resume.setSection(SectionType.EXPERIENCE, new OrganizationSection(experienceContent));

        Organization education1 = new Organization("Coursera", "https://www.coursera.org/course/progfun",
                DateUtil.of(2013, Month.MARCH), DateUtil.of(2013, Month.MAY),
                "\"Functional Programming Principles in Scala\" by Martin Odersky", null);
        Organization education2 = new Organization("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
                DateUtil.of(2011, Month.MARCH), DateUtil.of(2011, Month.APRIL),
                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", null);
        Organization education3 = new Organization("Siemens AG", "http://www.siemens.ru/",
                DateUtil.of(2005, Month.JANUARY), DateUtil.of(2005, Month.APRIL),
                "3 месяца обучения мобильным IN сетям (Берлин)", null);
        Organization education4 = new Organization("Alcatel", "http://www.alcatel.ru/",
                DateUtil.of(1997, Month.SEPTEMBER), DateUtil.of(1998, Month.MARCH),
                "6 месяцев обучения цифровым телефонным сетям (Москва)", null);
        Organization education5 = new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/",
                DateUtil.of(1993, Month.SEPTEMBER), DateUtil.of(1996, Month.JULY),
                "Аспирантура (программист С, С++)", null);
        Organization education6 = new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/",
                DateUtil.of(1987, Month.SEPTEMBER), DateUtil.of(1993, Month.JULY),
                "Инженер (программист Fortran, C)", null);
        Organization education7 = new Organization("Заочная физико-техническая школа при МФТИ", "http://www.school.mipt.ru/",
                DateUtil.of(1984, Month.SEPTEMBER), DateUtil.of(1987, Month.JUNE),
                "Закончил с отличием", null);
        List<Organization> educationContent = new ArrayList<>();
        Collections.addAll(educationContent, education1, education2, education3, education4, education5, education6, education7);
        resume.setSection(SectionType.EDUCATION, new OrganizationSection(educationContent));

        printResume(resume);
    }

    private static void printResume(Resume resume) {
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println(resume.getFullName().toUpperCase() + '\n');

        for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
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
