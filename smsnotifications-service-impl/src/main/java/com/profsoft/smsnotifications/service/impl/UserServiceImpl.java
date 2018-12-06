package com.profsoft.smsnotifications.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.profsoft.smsnotifications.model.dao.UserDao;
import com.profsoft.smsnotifications.model.data.User;
import com.profsoft.smsnotifications.service.UserService;

/**
 * @author filips
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	@Transactional
	public User updateUser(User user) {
		return userDao.update(user);
	}

	@Override
	public List<User> getAll() {
		return userDao.findAll();
	}

}
