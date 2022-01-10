package com.javaops.webapp.model;

import java.time.YearMonth;
import java.util.Objects;

public class Position {
    private YearMonth dateFrom;
    private YearMonth dateTo;
    private String description;
    private String additionalInfo;

    public Position(YearMonth dateFrom, YearMonth dateTo, String description, String additionalInfo) {
        Objects.requireNonNull(dateFrom, "dateFrom must not be null");
        Objects.requireNonNull(dateTo, "dateTo must not be null");
        Objects.requireNonNull(description, "description must not be null");
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.description = description;
        this.additionalInfo = additionalInfo;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return dateFrom.equals(position.dateFrom) && dateTo.equals(position.dateTo) && description.equals(position.description) && Objects.equals(additionalInfo, position.additionalInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateFrom, dateTo, description, additionalInfo);
    }

    @Override
    public String toString() {
        return "Position{" +
                "dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", description='" + description + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                '}';
    }
}
