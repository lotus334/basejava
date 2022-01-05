package com.javaops.webapp.model;

import java.util.List;

public class ExperienceSection implements Section {
    List<Experience> list;

    public ExperienceSection(List<Experience> list) {
        this.list = list;
    }

    @Override
    public List<Experience> getContent() {
        return list;
    }
}
