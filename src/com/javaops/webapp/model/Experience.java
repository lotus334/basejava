package com.javaops.webapp.model;

import java.time.YearMonth;

public class Experience {
    public String title;
    public YearMonth dateFrom;
    public YearMonth dateTo;
    public String description;
    public String additionalInfo;

    public Experience(String title, YearMonth dateFrom, YearMonth dateTo, String description, String additionalInfo) {
        this.title = title;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.description = description;
        this.additionalInfo = additionalInfo;
    }
}
