package com.profsoft.smsnotifications.model.criteria;

import com.profsoft.smsnotifications.model.base.criteria.AbstractSearchCriteria;

/**
 * Kryteria wyszukiwania encji User.
 *
 * @author fszpunar
 */
public class UserSC extends AbstractSearchCriteria {

	//@Like(path = "login")
	private String login;

	//@Like(path = "firstName")
	private String firstName;

	//@Like(path = "lastName")
	private String lastName;

}
