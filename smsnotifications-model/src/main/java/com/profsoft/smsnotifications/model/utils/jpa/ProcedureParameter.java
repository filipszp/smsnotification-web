package com.profsoft.smsnotifications.model.utils.jpa;
/**
 *
 * @author Marek
 * @param <E>
 */
public class ProcedureParameter<E> {

    public ProcedureParameter(Class<E> paramType, String name, Object value) {
        this.name = name;
        this.value = value;
        this.paramType = paramType;
    }
    private final String name;
    private final Class<E> paramType;
    private final Object value;
    public String getName() {
        return name;
    }

    public Class<E> getParamType() {
        return paramType;
    }

    public Object getValue() {
        return value;
    }
}
