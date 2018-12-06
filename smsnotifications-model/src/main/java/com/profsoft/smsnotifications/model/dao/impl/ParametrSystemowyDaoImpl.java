package com.profsoft.smsnotifications.model.dao.impl;

import org.springframework.stereotype.Repository;

import com.profsoft.smsnotifications.model.criteria.ParametrSystemowySC;
import com.profsoft.smsnotifications.model.dao.ParametrSystemowyDao;
import com.profsoft.smsnotifications.model.data.ParametrSystemowy;

/**
 *
 * @author filips
 */
@Repository("parametrSystemowyDao")
public class ParametrSystemowyDaoImpl extends AbstractCriteriaDaoImpl<ParametrSystemowy, Long, ParametrSystemowySC> implements ParametrSystemowyDao {

}
