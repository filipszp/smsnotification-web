package com.profsoft.smsnotifications.model.base.criteria;

public enum SortDirection {

    ASC("asc"), DESC("desc");

    private final String direction;

    private SortDirection(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return this.direction;
    }

    public static SortDirection valueOfCaseInsensitive(String value) {
        String valueUpper = value.toUpperCase();
        SortDirection sd = SortDirection.valueOf(valueUpper);
        return sd;
    }
}
