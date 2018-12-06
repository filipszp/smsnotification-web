package com.profsoft.smsnotifications.model.dao;

import java.util.List;

import com.profsoft.smsnotifications.model.criteria.UserSC;
import com.profsoft.smsnotifications.model.data.User;

/**
 *
 * @author filips
 */
public interface UserDao extends CriteriaDao<User, Long, UserSC> {

	List<User> getAll();

}
