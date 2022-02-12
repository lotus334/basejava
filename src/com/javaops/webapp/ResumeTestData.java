package com.javaops.webapp;

import com.javaops.webapp.model.*;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.javaops.webapp.model.SectionTypes.*;

public class ResumeTestData {

    public static void main(String[] args) {
        Resume myResume = createFilledResume("uuid1", "Григорий Кислин");

        printAll(myResume);
    }

    public static Resume createFilledResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        // SET CONTACTS
        Map<ContactTypes, String> contacts = new HashMap<>();
        contacts.put(ContactTypes.TELEPHONE, "+7(921) 855-0482");
        contacts.put(ContactTypes.SKYPE, "grigory.kislin");
        contacts.put(ContactTypes.EMAIL, "gkislin@yandex.ru");
        resume.setContacts(contacts);

        //SET OBJECTIVE
        TextSection objectiveSection =
                new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        resume.setSection(OBJECTIVE, objectiveSection);

        //SET ACHIEVEMENT
        List<String> achievement = new ArrayList<>(List.of(
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                        "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP)." +
                        " Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. " +
                        "Более 1000 выпускников. ",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                        "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk. ",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. " +
                        "Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления " +
                        "окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и " +
                        "авторизации различных ERP модулей, интеграция CIFS/SMB java сервера. ",
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, " +
                        "Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.",
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов " +
                        "(SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и " +
                        "информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента " +
                        "для администрирования и мониторинга системы по JMX (Jython/ Django). ",
                "Реализация протоколов по приему платежей всех основных платежных системы России " +
                        "(Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."
        ));
        resume.setSection(ACHIEVEMENT, new ListSection(achievement));

        //SET
        List<String> qualificationSection = new ArrayList<>(List.of(
                "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2 ",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce ",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, ",
                "MySQL, SQLite, MS SQL, HSQLDB ",
                "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy, ",
                "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts, ",
                "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring " +
                        "(MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, " +
                        "GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, " +
                        "Selenium (htmlelements). ",
                "Python: Django. ",
                "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js ",
                "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka ",
                "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB," +
                        " JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT. ",
                "Инструменты: Maven + plugin development, Gradle, настройка Ngnix, ",
                "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, " +
                        "OpenCmis, Bonita, pgBouncer. ",
                "Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, " +
                        "UML, функционального программирования ",
                "Родной русский, английский \"upper intermediate\""
        ));
        resume.setSection(QUALIFICATIONS, new ListSection(qualificationSection));

        //SET EXPERIENCE
        List<Organization> organizations = new ArrayList<>(List.of(
                new Organization(
                        "Java Online Projects",
                        "https://javaops.ru/",
                        List.of(
                                new Position(
                                        YearMonth.of(2013, 10),
                                        YearMonth.now(),
                                        "Автор проекта.",
                                        "Создание, организация и проведение Java онлайн проектов и стажировок."
                                )
                        )
                ),
                new Organization(
                        "Wrike",
                        "https://www.wrike.com/",
                        List.of(
                                new Position(
                                        YearMonth.of(2014, 10),
                                        YearMonth.of(2016, 1),
                                        "Старший разработчик (backend).",
                                        "Проектирование и разработка онлайн платформы управления проектами Wrike " +
                                                "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                                                "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.."
                                )
                        )
                ),
                new Organization(
                        "RIT Center",
                        null,
                        List.of(
                                new Position(
                                        YearMonth.of(2010, 12),
                                        YearMonth.of(2012, 4),
                                        "Java архитектор",
                                        "Организация процесса разработки системы ERP для разных окружений: " +
                                                "релизная политика, версионирование, ведение CI (Jenkins), миграция базы " +
                                                "(кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), " +
                                                "AAA via SSO. Архитектура БД и серверной части системы. Разработка " +
                                                "интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего " +
                                                "назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN " +
                                                "для online редактирование из браузера документов MS Office. " +
                                                "Maven + plugin development, Ant, Apache Commons, Spring security, " +
                                                "Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, " +
                                                "Unix shell remote scripting via ssh tunnels, PL/Python"
                                )
                        )
                )
        ));
        OrganizationSection organizationSection = new OrganizationSection(organizations);
        resume.setSection(EXPERIENCE, organizationSection);

        //SET EDUCATION
        List<Organization> education = new ArrayList<>();
        Organization edu1 = new Organization(
                "Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                "https://itmo.ru/ru/",
                List.of(
                        new Position(
                                YearMonth.of(1987, 9),
                                YearMonth.of(1993, 7),
                                "Инженер (программист Fortran, C)",
                                null
                        ),
                        new Position(
                                YearMonth.of(1993, 9),
                                YearMonth.of(1996, 7),
                                "Аспирантура (программист С, С++)",
                                null
                        )
                )
        );
        education.add(edu1);
        OrganizationSection educationSection = new OrganizationSection(education);
        resume.setSection(EDUCATION, educationSection);
        return resume;
    }

    static void printAll(Resume resume) {
        System.out.println("----------------------------");
        Map<ContactTypes, String> contacts = resume.getContacts();
        for (ContactTypes key : resume.getContacts().keySet()) {
            System.out.println(key.getTitle() + " - " + contacts.get(key));
            System.out.println("----------------------------");
        }
        for (SectionTypes sectionType : SectionTypes.values()) {
            Section section = resume.getSection(sectionType);
            if (section != null) {
                System.out.println(sectionType.getTitle());
                System.out.println(section.toString());
                System.out.println("----------------------------");
            }
        }
    }
}
