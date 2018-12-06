package com.profsoft.smsnotifications.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.profsoft.smsnotifications.model.dao.ParametrSystemowyDao;
import com.profsoft.smsnotifications.model.data.ParametrSystemowy;
import com.profsoft.smsnotifications.service.ParametrSystemowyService;

/**
 * @author filips
 *
 */
@Service("parametrSystemowyService")
public class ParametrSystemowyServiceImpl implements ParametrSystemowyService {

	@Autowired
	private ParametrSystemowyDao parametrSystemowyDao;

	@Override
	public List<ParametrSystemowy> getAll() {
		return parametrSystemowyDao.findAll();
	}

}
