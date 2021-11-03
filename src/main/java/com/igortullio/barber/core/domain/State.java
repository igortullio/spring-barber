package com.igortullio.barber.core.domain;

public class State extends AbstractDomain {

    private String name;
    private String initials;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

}
