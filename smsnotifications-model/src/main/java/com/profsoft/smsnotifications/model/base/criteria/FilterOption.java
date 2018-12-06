package com.profsoft.smsnotifications.model.base.criteria;

public class FilterOption {

    private String field;
    private Object value;
    private FilterOperator type;

    public FilterOption() {
    }

    public FilterOption(String field, Object value) {
        this(field, value, FilterOperator.EQUALS);
    }

    public FilterOption(String field, Object value, FilterOperator type) {
        this.field = field;
        this.value = value;
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }

    public FilterOperator getType() {
        return type;
    }

}
