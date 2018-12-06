package com.profsoft.smsnotifications.model.base.criteria;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Maroo
 */
public class AbstractSearchCriteria implements SearchCriteria {

	@Override
	public boolean isEmpty() {
		// TODO uzupelnic
		return false;
	}

	@Override
	public void clean() {
		// TODO uzupelnic
	}

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
	}

}
