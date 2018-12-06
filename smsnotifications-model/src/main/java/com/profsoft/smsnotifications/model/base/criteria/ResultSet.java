package com.profsoft.smsnotifications.model.base.criteria;

import java.util.Collections;
import java.util.List;

public final class ResultSet<T> {

	private final List<T> rows;
	private final Long totalRecords;

	public ResultSet(List<T> rows, Long totalRecords) {
		this.rows = rows;
		this.totalRecords = totalRecords;
	}

	public List<T> getRows() {
		return Collections.unmodifiableList(rows);
	}

	public Long getTotalRecords() {
		return totalRecords;
	}
}
