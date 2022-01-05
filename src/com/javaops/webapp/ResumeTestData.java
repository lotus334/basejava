package com.javaops.webapp;

import com.javaops.webapp.model.*;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.javaops.webapp.model.SectionTypes.CONTACTS;

public class ResumeTestData {
    private static final Resume MY_RESUME = new Resume("DmitriyVass");

    public static void main(String[] args) {
        Map<ContactTypes, String> contacts = new HashMap<>();
        contacts.put(ContactTypes.EMAIL, "lotus33497@yandex.ru");
        contacts.put(ContactTypes.TELEPHONE, "89994663481");
        contacts.put(ContactTypes.SKYPE, "lotus33497");
        contacts.remove(ContactTypes.SKYPE);
        MY_RESUME.setSection(CONTACTS, contacts);
        MY_RESUME.getSection(CONTACTS);

        MY_RESUME.setSection(SectionTypes.OBJECTIVE, "Developer");

        List<String> achievement = new ArrayList<>();
        achievement.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP)." +
                " Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. " +
                "Более 1000 выпускников. ");
        achievement.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk. ");
        MY_RESUME.setSection(SectionTypes.ACHIEVEMENT, achievement);

        List<Experience> experiences = new ArrayList<>();
        Experience exp1 = new Experience(
                "Java Online Projects",
                YearMonth.of(2013, 10),
                YearMonth.now(),
                "Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок."
        );
        Experience exp2 = new Experience(
                "Wrike",
                YearMonth.of(2014, 10),
                YearMonth.of(2016, 1),
                "Старший разработчик (backend).",
                "Проектирование и разработка онлайн платформы управления проектами Wrike " +
                        "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                        "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.."
        );
        experiences.add(exp1);
        experiences.add(exp2);
        MY_RESUME.setSection(SectionTypes.EXPERIENCE, experiences);

        printAll();
    }

    static void printAll() {
        System.out.println("----------------------------");
        for (SectionTypes section : MY_RESUME.getSections()) {
            System.out.println(section.title);
            switch (section) {
                case CONTACTS -> {
                    Map<ContactTypes, String> contacts = MY_RESUME.getSection(section);
                    for (ContactTypes contactType: contacts.keySet()) {
                        System.out.println(contactType.title + " - " + contacts.get(contactType));
                    }
                    System.out.println("----------------------------");
                    break;
                }
                case PERSONAL, OBJECTIVE -> {
                    System.out.println((String) MY_RESUME.getSection(section));
                    System.out.println("----------------------------");
                    break;
                }
                case ACHIEVEMENT, QUALIFICATIONS -> {
                    List<String> list = MY_RESUME.getSection(section);
                    for (String str : list) {
                        System.out.println(str);
                    }
                    System.out.println("----------------------------");
                    break;
                }
                case EDUCATION, EXPERIENCE -> {
                    List<Experience> list = MY_RESUME.getSection(section);
                    System.out.println("============================");
                    for (Experience exp : list) {
                        System.out.println(exp.title);
                        System.out.println(exp.dateFrom + " - " + exp.dateTo);
                        System.out.println(exp.description);
                        System.out.println(exp.additionalInfo);
                        System.out.println("============================");
                    }
                    System.out.println("----------------------------");
                    break;
                }
            }
        }
    }
}
