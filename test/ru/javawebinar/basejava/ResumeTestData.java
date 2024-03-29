package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.Map;
import java.util.UUID;

public class ResumeTestData {

    public static final String UUID_1 = "11111111-1111-1111-1111-111111111111";
    public static final String UUID_2 = "22222222-2222-2222-2222-222222222222";
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();

    public static final Resume R1;
    public static final Resume R2;
    public static final Resume R3;
    public static final Resume R4;

    static {
        R1 = getCompletedResume1(UUID_1, "Григорий Кислин");
        R2 = getCompletedResume2(UUID_2, "Эрнест Хемингуэй");
        R3 = getCompletedResume1(UUID_3, "Name Three");
        R4 = getCompletedResume1(UUID_4, "Name Four");
    }

    public static Resume getCompletedResume1(String uuid, String fullName) {
        Resume resume;
        if (uuid == null) {
            resume = new Resume(fullName);
        } else {
            resume = new Resume(uuid, fullName);
        }

        // Контакты
        if (fullName.equals("Name Four")) {
            resume.setContact(ContactType.PHONE_NUMBER, "44444");
            resume.setContact(ContactType.SKYPE, "Skype");
        } else {
            resume.setContact(ContactType.PHONE_NUMBER, "+7(921) 855-0482");
            resume.setContact(ContactType.SKYPE, "skype:grigory.kislin");
            resume.setContact(ContactType.EMAIL, "gkislin@yandex.ru");
            resume.setContact(ContactType.LINKED_IN, "https://www.linkedin.com/in/gkislin");
            resume.setContact(ContactType.GIT_HUB, "https://github.com/gkislin");
            resume.setContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
            resume.setContact(ContactType.HOME_PAGE, "http://gkislin.ru/");
        }

        // Позиция
        resume.setSection(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));

        // Личные качества
        resume.setSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        // Достижения
        resume.setSection(SectionType.ACHIEVEMENT, new ListSection("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.\n",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.\n",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.\n",
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.\n",
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).\n",
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.\n"));

        // Квалификация
        resume.setSection(SectionType.QUALIFICATIONS, new ListSection("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2\n",
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
        resume.setSection(SectionType.EXPERIENCE, new OrganizationSection(
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
        resume.setSection(SectionType.EDUCATION, new OrganizationSection(
                new Organization("Coursera", "https://www.coursera.org/course/progfun",
                        new Organization.Position(LocalDate.of(2013, Month.MARCH, 1),
                                LocalDate.of(2013, Month.MAY, 1),
                                "Functional Programming Principles in Scala by Martin Odersky", null)),
                new Organization("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
                        new Organization.Position(LocalDate.of(2011, Month.MARCH, 1),
                                LocalDate.of(2011, Month.APRIL, 1),
                                "Курс Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.", null)),
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

    public static Resume getCompletedResume2(String uuid, String fullName) {
        Resume resume;
        if (uuid == null) {
            resume = new Resume(fullName);
        } else {
            resume = new Resume(uuid, fullName);
        }

        // Контакты
        resume.setContact(ContactType.HOME_PAGE,
                "http://https://www.nobelprize.org/prizes/literature/1954/hemingway/biographical/");

        // Позиция
        resume.setSection(SectionType.OBJECTIVE, new TextSection("Журналист, писатель"));

        // Личные качества
        resume.setSection(SectionType.PERSONAL, new TextSection("Харизма, твердость характера. Увлечение рыболовством, охотой, боксом, корридой, кошками, путешествиями"));

        // Достижения
        resume.setSection(SectionType.ACHIEVEMENT, new ListSection("Получил широкое признание благодаря своим романам и многочисленным рассказам и своей жизни, полной приключений и неожиданностей\n",
                "Значительное влияние на литературу XX века\n", "Получил Пулитцеровскую премию\n",
                "Получил Нобелевскую примеию\n"));

        // Квалификация
        resume.setSection(SectionType.QUALIFICATIONS, new ListSection("Написание прозы высокго качества\n",
                "Написание статей для различных журналов, репортерская деятельность\n",
                "Подготовка исследовательстких и аналитических материалов на широкий спектр тем\n",
                "Написание кино-сценариев\n",
                "Английский язык (родной), французский (могу говорить), немецкий (могу говорить строго), испанский (могу говорить)\n"));

        // Опыт работы
        resume.setSection(SectionType.EXPERIENCE, new OrganizationSection(
                new Organization("Газета The Kansas City Star, Канзас-Сити", "https://www.kansascity.com",
                        new Organization.Position(LocalDate.of(1917, Month.JANUARY, 1),
                                LocalDate.of(1917, Month.DECEMBER, 1), "Полицейский репортер",
                                "Патрулировал город, выезжал на происшествия, оттачивал стиль")),
                new Organization("Фронт Первой мировой войны, Италия", null,
                        new Organization.Position(LocalDate.of(1918, Month.JANUARY, 1),
                                LocalDate.of(1918, Month.DECEMBER, 1),
                                "Водитель санитарной машины)",
                                "Не попал в армию по зрению, устроился добровольцем в Красный Крест, попал под обстрел, навидался всякого")),
                new Organization("Газета Toronto Star, Торонто", "https://www.thestar.com",
                        new Organization.Position(LocalDate.of(1920, Month.JANUARY, 1),
                                LocalDate.of(1924, Month.DECEMBER, 1), "Репортер",
                                "Удаленная работа, командировки на греко-турецкую войну, в Италию, Испанию, Германию и т. п.")),
                new Organization("Газета Cooperative Commonwealth, Чикаго", null,
                        new Organization.Position(LocalDate.of(1921, Month.JANUARY, 1),
                                LocalDate.of(1921, Month.FEBRUARY, 1), "Редактор",
                                "Работал в газете два месяца, пока та не закрылась из-за скандала\n")),
                new Organization("Freelance", null,
                        new Organization.Position(LocalDate.of(1923, Month.JANUARY, 1),
                                DateUtil.NOW, "Публикуемый автор",
                                "Начал со сборника «Три истории и десять поэм», с 1929 года, после «Прощай, оружие», полностью обеспечивал себя писательскими гонорарами\n")),
                new Organization("Испания, страна", null,
                        new Organization.Position(LocalDate.of(1932, Month.JANUARY, 1),
                                LocalDate.of(1933, Month.JANUARY, 1), "Автор-исследователь",
                                "Исследовательская работа по боям быков, сбор материала для «Смерти после полудня»")),
                new Organization("Африка, материк", null,
                        new Organization.Position(LocalDate.of(1933, Month.FEBRUARY, 1),
                                LocalDate.of(1934, Month.DECEMBER, 1), "Охотник-путешественник",
                                "10-недельное сафари по Африке, охота на дичь, сбор материала для «Зеленых холмов Африки»")),
                new Organization("Испания, страна", null,
                        new Organization.Position(LocalDate.of(1937, Month.JANUARY, 1),
                                LocalDate.of(1937, Month.JUNE, 1), "Военный корреспондент",
                                "Североамериканская газетная ассоциация. Репортаж с места военных действий (Гражданская войнв в Испании), удаленная работа.")),
                new Organization("Freelance", null,
                        new Organization.Position(LocalDate.of(1937, Month.JULY, 1),
                                LocalDate.of(1937, Month.DECEMBER, 1), "Сценарист, драматург",
                                "Работа над сценарием фильма «Испанская земля»")),
                new Organization("Личный катер Pilar (Куба, Карибское море)", null,
                        new Organization.Position(LocalDate.of(1942, Month.JANUARY, 1),
                                LocalDate.of(1943, Month.DECEMBER, 1), "Охотник за подводными лодками",
                                "Нагрузил катер друзьями, отправился искать в море, не нашел")),
                new Organization("Collier's Magazine", "https://onlinebooks.library.upenn.edu/webbin/serial?id=colliers",
                        new Organization.Position(LocalDate.of(1944, Month.JANUARY, 1),
                                LocalDate.of(1945, Month.DECEMBER, 1), "Военный корреспондент",
                                "Участие в боевых действиях, разведывательных полетах, высадке союзников в Нормандии, взятии Парижа")),
                new Organization("Журнал Life", "https://www.life.com",
                        new Organization.Position(LocalDate.of(1952, Month.JANUARY, 1),
                                LocalDate.of(1952, Month.DECEMBER, 1), "Писатель",
                                "Публикация повести «Старик и море», Пулитцеровская премия, Нобелевская премия"))));

        // Образование
        resume.setSection(SectionType.EDUCATION, new OrganizationSection(
                new Organization("Старшая школа Oak Park and River Forest High School, Чикаго", null,
                        new Organization.Position(LocalDate.of(1913, Month.OCTOBER, 1),
                                LocalDate.of(1917, Month.JUNE, 1),
                                "Начал заниматься боксом, играть в футбол и писать для школьной газеты", null)),
                new Organization("Самообразование, самодисциплина", null,
                        new Organization.Position(LocalDate.of(1921, Month.JANUARY, 1),
                                LocalDate.of(1961, Month.JULY, 1),
                                "Много читал и писал по 500 слов в день, редактировал, снова писал", null))));

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
