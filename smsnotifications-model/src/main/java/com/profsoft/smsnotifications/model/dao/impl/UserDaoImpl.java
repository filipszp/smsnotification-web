package com.profsoft.smsnotifications.model.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.profsoft.smsnotifications.model.criteria.UserSC;
import com.profsoft.smsnotifications.model.dao.UserDao;
import com.profsoft.smsnotifications.model.data.User;

/**
 *
 * @author filips
 */
@Repository("userDao")
public class UserDaoImpl extends AbstractCriteriaDaoImpl<User, Long, UserSC> implements UserDao {

	@Override
	public List<User> getAll() {
		List<User> users = new ArrayList<>();
		User entity = entityManager.find(User.class, 1);
		users.add(entity);
		return users;
	}

}
