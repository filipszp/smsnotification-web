package com.profsoft.smsnotifications.client.beans.views;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.profsoft.smsnotifications.client.commons.AbstractBean;
import com.profsoft.smsnotifications.model.data.ParametrSystemowy;
import com.profsoft.smsnotifications.service.ParametrSystemowyService;

/**
 *
 * @author filip
 */
@ManagedBean(name = "paramListBean")
@ViewScoped
public class ParamListBean extends AbstractBean implements Serializable {

	@ManagedProperty("#{parametrSystemowyService}")
	private ParametrSystemowyService parametrSystemowyService;

	public ParametrSystemowyService getParametrSystemowyService() {
		return parametrSystemowyService;
	}

	public void setParametrSystemowyService(ParametrSystemowyService parametrSystemowyService) {
		this.parametrSystemowyService = parametrSystemowyService;
	}

	private List<ParametrSystemowy> parametryList;

	public List<ParametrSystemowy> getParametryList() {
		return parametryList;
	}

	public void setParametryList(List<ParametrSystemowy> parametryList) {
		this.parametryList = parametryList;
	}

	@PostConstruct
	public void initParamList() {
		L.info("POST CONSTRUCT");
		this.parametryList = parametrSystemowyService.getAll();
		L.info("#params: {}", parametryList.size());
	}

	@PreDestroy
	public void cleanUp() throws Exception {
		L.info("PRE-DESTROY");
		this.parametryList.clear();
	}
}
