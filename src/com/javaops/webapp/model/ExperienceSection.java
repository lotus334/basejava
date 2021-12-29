package com.javaops.webapp.model;

import java.time.YearMonth;

public class ExperienceSection implements Section {
    String title;
    YearMonth dateFrom;
    YearMonth dateTo;
    String description;
    String additionalInfo;

    @Override
    public <T> T getContent() {
        return null;
    }
}
