package com.javaops.webapp.model;

import java.time.YearMonth;
import java.util.Objects;

public class Experience {
    private String title;
    private String link;
    private YearMonth dateFrom;
    private YearMonth dateTo;
    private String description;
    private String additionalInfo;

    public Experience(String title, String link, YearMonth dateFrom, YearMonth dateTo, String description, String additionalInfo) {
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(dateFrom, "dateFrom must not be null");
        Objects.requireNonNull(dateTo, "dateTo must not be null");
        Objects.requireNonNull(description, "description must not be null");
        this.title = title;
        this.link = link;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.description = description;
        this.additionalInfo = additionalInfo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public YearMonth getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(YearMonth dateFrom) {
        this.dateFrom = dateFrom;
    }

    public YearMonth getDateTo() {
        return dateTo;
    }

    public void setDateTo(YearMonth dateTo) {
        this.dateTo = dateTo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Experience that = (Experience) o;
        return title.equals(that.title) && Objects.equals(link, that.link) && dateFrom.equals(that.dateFrom) && dateTo.equals(that.dateTo) && description.equals(that.description) && Objects.equals(additionalInfo, that.additionalInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, link, dateFrom, dateTo, description, additionalInfo);
    }
}
