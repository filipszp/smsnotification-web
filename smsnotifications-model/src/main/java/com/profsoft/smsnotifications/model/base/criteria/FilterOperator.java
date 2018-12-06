package com.profsoft.smsnotifications.model.base.criteria;

/**
 *
 * @author Maroo
 */
public enum FilterOperator {
    LIKE("like"),
    EQUALS("equal"),
    EQUALS_DATE("equalDate"),
    LESSER("lessThan"),
    GREATER("greaterThan"),
    LIKE_NO_CASE("likeNoCase"),
    GREATER_EQUALS("greaterThanOrEqualTo"),
    NOT_EQUALS("notEqual"),
    LESSER_EQUALS("lessThanOrEqualTo"),
    IS_NULL("isNull"),
    IS_NOT_NULL("isNotNull"),
    IS_EMPTY("isEmpty"),
    IS_NOT_EMPTY("isNotEmpty"),
    IN("in"),
    CONTAIN("contain"),
    CONTAIN_NO_CASE("containNoCase"),
    START_WITH("startWith"),
    START_WITH_NO_CASE("startWithNoCase"),
    END_WITH("endWith"),
    END_WITH_NO_CASE("endWithNoCase");

    private String code;

    FilterOperator(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static FilterOperator getByCode(String code) {
        for (FilterOperator e : values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }
}
