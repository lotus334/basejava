package com.javaops.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class TextSection extends Section {

    private static final long serialVersionUID = 1L;

    private String article;

    public TextSection(String article) {
        Objects.requireNonNull(article, "article must not be null");
        this.article = article;
    }

    public TextSection() {}

    public String getArticle() {
        return article;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return article.equals(that.article);
    }

    @Override
    public int hashCode() {
        return Objects.hash(article);
    }

    @Override
    public String toString() {
        return article;
    }
}
