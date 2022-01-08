package com.javaops.webapp;

import com.javaops.webapp.model.*;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.javaops.webapp.model.SectionTypes.*;

public class ResumeTestData {
    private static final Resume MY_RESUME = new Resume("DmitriyVass");

    public static void main(String[] args) {
        // SET CONTACTS
        Map<ContactTypes, String> contacts = new HashMap<>();
        contacts.put(ContactTypes.EMAIL, "lotus33497@yandex.ru");
        contacts.put(ContactTypes.TELEPHONE, "89994663481");
        contacts.put(ContactTypes.SKYPE, "lotus33497");
        MY_RESUME.setContacts(contacts);

        //ADD CONTACTS
        Map<ContactTypes, String> additionalContacts = MY_RESUME.getContacts();
        additionalContacts.putAll(Map.of(ContactTypes.GITHUB, "lotus334"));
        MY_RESUME.setContacts(additionalContacts);

        //TO DO - REMOVE CONTACTS

        //SET OBJECTIVE
        MY_RESUME.setSection(OBJECTIVE, "Developer");

        //REMOVE OBJECTIVE
        MY_RESUME.getSection(OBJECTIVE).removeContent("");

        //SET ACHIEVEMENT
        List<String> achievement = new ArrayList<>();
        achievement.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP)." +
                " Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. " +
                "Более 1000 выпускников. ");
        achievement.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk. ");
        MY_RESUME.setSection(ACHIEVEMENT, achievement);

        //ADD ACHIEVEMENT
        List<String> additionalAchievement = List.of("Налаживание процесса разработки и непрерывной интеграции ERP " +
                "системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления " +
                "окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных " +
                "ERP модулей, интеграция CIFS/SMB java сервера. ");
        MY_RESUME.getSection(ACHIEVEMENT).addContent(additionalAchievement);

        //REMOVE ACHIEVEMENT
        MY_RESUME.getSection(ACHIEVEMENT).removeContent("Налаживание процесса разработки и непрерывной интеграции ERP " +
                "системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления " +
                "окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных " +
                "ERP модулей, интеграция CIFS/SMB java сервера. ");

        //SET EXPERIENCE
        List<Experience> experiences = new ArrayList<>();
        Experience exp1 = new Experience(
                "Java Online Projects",
                "https://javaops.ru/",
                YearMonth.of(2013, 10),
                YearMonth.now(),
                "Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок."
        );
        Experience exp2 = new Experience(
                "Wrike",
                "https://www.wrike.com/",
                YearMonth.of(2014, 10),
                YearMonth.of(2016, 1),
                "Старший разработчик (backend).",
                "Проектирование и разработка онлайн платформы управления проектами Wrike " +
                        "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                        "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.."
        );
        experiences.add(exp1);
        experiences.add(exp2);
        MY_RESUME.setSection(EXPERIENCE, experiences);

        //ADD EXPERIENCE
        List<Experience> additionalExperiences = List.of(
                new Experience(
                        "RIT Center",
                        null,
                        YearMonth.of(2012, 4),
                        YearMonth.of(2014, 10),
                        "Java архитектор",
                        "Организация процесса разработки системы ERP для разных окружений: " +
                                "релизная политика, версионирование, ведение CI (Jenkins), " +
                                "миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), " +
                                "AAA via SSO. Архитектура БД и серверной части системы. " +
                                "Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), " +
                                "сервисов общего назначения (почта, экспорт в pdf, doc, html). " +
                                "Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. " +
                                "Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat," +
                                "WSO2, xcmis, OpenCmis, Bonita, Python scripting, " +
                                "Unix shell remote scripting via ssh tunnels, PL/Python"
                )
        );
        MY_RESUME.getSection(EXPERIENCE).addContent(additionalExperiences);

        //REMOVE EXPERIENCE
        MY_RESUME.getSection(EXPERIENCE).removeContent("RIT Center");

        List<Experience> education = new ArrayList<>();
        Experience edu1 = new Experience(
                "Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                "https://itmo.ru/ru/",
                YearMonth.of(1987, 9),
                YearMonth.of(1993, 7),
                "Инженер (программист Fortran, C)",
                null
        );
        Experience edu2 = new Experience(
                "Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                "https://itmo.ru/ru/",
                YearMonth.of(1993, 9),
                YearMonth.of(1996, 7),
                "Аспирантура (программист С, С++)",
                null);
        experiences.add(edu1);
        experiences.add(edu2);
        MY_RESUME.setSection(EDUCATION, education);

        printAll();
    }

    static void printAll() {
        System.out.println("----------------------------");
        Map<ContactTypes, String> contacts = MY_RESUME.getContacts();
        for (ContactTypes key : MY_RESUME.getContacts().keySet()) {
            System.out.println(key + " - " + contacts.get(key));
        }
        for (SectionTypes section : MY_RESUME.getSections()) {
            if (MY_RESUME.getSectionStorage(section) == null) continue;
            System.out.println(section.title);
            switch (section) {
                case PERSONAL, OBJECTIVE -> {
                    System.out.println((String) MY_RESUME.getSectionStorage(section));
                    System.out.println("----------------------------");
                    break;
                }
                case ACHIEVEMENT, QUALIFICATIONS -> {
                    List<String> list = MY_RESUME.getSectionStorage(section);
                    for (String str : list) {
                        System.out.println(str);
                    }
                    System.out.println("----------------------------");
                    break;
                }
                case EDUCATION, EXPERIENCE -> {
                    List<Experience> list = MY_RESUME.getSectionStorage(section);
                    System.out.println("============================");
                    for (Experience exp : list) {
                        System.out.println(exp.getTitle());
                        System.out.println(exp.getDateFrom() + " - " + exp.getDateTo());
                        System.out.println(exp.getDescription());
                        System.out.println(exp.getAdditionalInfo());
                        System.out.println("============================");
                    }
                    System.out.println("----------------------------");
                    break;
                }
            }
        }
    }
}
