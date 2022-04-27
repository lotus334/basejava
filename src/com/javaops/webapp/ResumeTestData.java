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

        //SET PERSONAL
        TextSection personalSection =
                new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность.");
        resume.setSection(PERSONAL, personalSection);

        //SET OBJECTIVE
        TextSection objectiveSection =
                new TextSection("Ведущий стажировок и корпоративного обучения");
        resume.setSection(OBJECTIVE, objectiveSection);

        //SET ACHIEVEMENT
        List<String> achievement = new ArrayList<>(List.of(
                "С 2013 года: разработка проектов"
        ));
        resume.setSection(ACHIEVEMENT, new ListSection(achievement));

        //SET
        List<String> qualificationSection = new ArrayList<>(List.of(
                "JEE AS",
                "Version control",
                "DB",
                "Languages",
                "Java Frameworks",
                "Python",
                "JavaScript",
                "Scala",
                "Технологии",
                "администрирование",
                "Отличное знание и опыт",
                "Родной русский, английский"
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
                                        "Проектирование и разработка онлайн платформы управления проектами Wrike"
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
                                        "Организация процесса разработки системы ERP для разных окружений"
                                )
                        )
                )
        ));
        OrganizationSection organizationSection = new OrganizationSection(organizations);
        resume.setSection(EXPERIENCE, organizationSection);
//
//        //SET EDUCATION
//        List<Organization> education = new ArrayList<>();
//        Organization edu1 = new Organization(
//                "Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
//                "https://itmo.ru/ru/",
//                List.of(
//                        new Position(
//                                YearMonth.of(1987, 9),
//                                YearMonth.of(1993, 7),
//                                "Инженер (программист Fortran, C)",
//                                null
//                        ),
//                        new Position(
//                                YearMonth.of(1993, 9),
//                                YearMonth.of(1996, 7),
//                                "Аспирантура (программист С, С++)",
//                                null
//                        )
//                )
//        );
//        education.add(edu1);
//        OrganizationSection educationSection = new OrganizationSection(education);
//        resume.setSection(EDUCATION, educationSection);
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
                System.out.println(section);
                System.out.println("----------------------------");
            }
        }
    }
}
