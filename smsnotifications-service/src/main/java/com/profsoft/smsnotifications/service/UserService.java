package com.profsoft.smsnotifications.service;

import java.util.List;

import com.profsoft.smsnotifications.model.data.User;

/**
 *
 * @author filip
 */
public interface UserService {

	User updateUser(User user);

	List<User> getAll();

}
