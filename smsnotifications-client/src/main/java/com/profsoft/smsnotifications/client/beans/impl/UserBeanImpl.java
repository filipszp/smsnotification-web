package com.profsoft.smsnotifications.client.beans.impl;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.profsoft.smsnotifications.client.commons.AbstractBean;
import com.profsoft.smsnotifications.service.UserService;

/**
 *
 * @author filip
 */
@ManagedBean(name = "userBean")
@ViewScoped
public class UserBeanImpl extends AbstractBean implements Serializable {

	@ManagedProperty("#{userService}")
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public Integer pobierzLiczbeUsers() {
		return userService.getAll().size();
	}

}
