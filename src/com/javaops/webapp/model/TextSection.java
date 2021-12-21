package com.javaops.webapp.model;

public class TextSection implements Section {
    String content;

    public TextSection(String content) {
        this.content = content;
    }

    @Override
    public <T> T getContent() {
        return (T) content;
    }

    @Override
    public String toString() {
        return content;
    }
}
