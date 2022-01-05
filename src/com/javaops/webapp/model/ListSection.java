package com.javaops.webapp.model;

import java.util.List;

public class ListSection implements Section {
    List<String> content;

    public ListSection(List<String> content) {
        this.content = content;
    }

    @Override
    public List<String> getContent() {
        return content;
    }
}
