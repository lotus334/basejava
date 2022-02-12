package com.javaops.webapp.model;

import java.util.Objects;

public class TextSection implements Section {
    private String article;

    public TextSection(String article) {
        Objects.requireNonNull(article, "article must not be null");
        this.article = article;
    }

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
