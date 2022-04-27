package com.javaops.webapp.model;

import com.javaops.webapp.util.XmlYearMonthAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.Month;
import java.time.YearMonth;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Position implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlJavaTypeAdapter(XmlYearMonthAdapter.class)
    private YearMonth dateFrom;
    @XmlJavaTypeAdapter(XmlYearMonthAdapter.class)
    private YearMonth dateTo;
    private String description;
    private String additionalInfo;

    public Position(int startYear, Month startMonth, String description, String additionalInfo) {
        this(YearMonth.of(startYear, startMonth), description, additionalInfo);
    }

    public Position(int startYear, Month startMonth, int endYear, Month endMonth, String description, String additionalInfo) {
        this(YearMonth.of(startYear, startMonth), YearMonth.of(endYear, endMonth), description, additionalInfo);
    }

    public Position(YearMonth dateFrom, String description, String additionalInfo) {
        this(dateFrom, YearMonth.now(), description, additionalInfo);
    }

    public Position(YearMonth dateFrom, YearMonth dateTo, String description, String additionalInfo) {
        Objects.requireNonNull(dateFrom, "dateFrom must not be null");
        Objects.requireNonNull(dateTo, "dateTo must not be null");
        Objects.requireNonNull(description, "description must not be null");
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.description = description;
        this.additionalInfo = additionalInfo;
    }

    public Position() {}

    public YearMonth getDateFrom() {
        return dateFrom;
    }

    public YearMonth getDateTo() {
        return dateTo;
    }

    public String getDescription() {
        return description;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return dateFrom.equals(position.dateFrom) &&
                dateTo.equals(position.dateTo) &&
                description.equals(position.description) &&
                Objects.equals(additionalInfo, position.additionalInfo);
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
