package com.profsoft.smsnotifications.model.base.criteria;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author Maroo
 * @param <T>
 */
public class DataTablesResultSet<T> {

    private final Integer draw;
    private final Long recordsTotal;
    private final Long recordsFiltered;
    private final List<T> data;

    public DataTablesResultSet(PagingCriteria pc, ResultSet<T> rs) {
        this.draw = pc.getPageNumber();
        this.data = rs.getRows();
        this.recordsTotal = rs.getTotalRecords();
        this.recordsFiltered = rs.getTotalRecords();
    }

    public DataTablesResultSet(Integer pageNumber, ResultSet<T> rs) {
        this.draw = pageNumber;
        this.data = rs.getRows();
        this.recordsTotal = rs.getTotalRecords();
        this.recordsFiltered = rs.getTotalRecords();
    }

    public Integer getsDraw() {
        return draw;
    }

    public Long getRecordsTotal() {
        return recordsTotal;
    }

    public Long getRecordsFiltered() {
        return recordsFiltered;
    }

    public List<T> getData() {
        return Collections.unmodifiableList(data);
    }
}
