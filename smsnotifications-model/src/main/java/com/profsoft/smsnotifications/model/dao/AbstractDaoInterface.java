package com.profsoft.smsnotifications.model.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.profsoft.smsnotifications.model.utils.jpa.ProcedureParameter;

/**
 * @author Marek
 * @param <E>
 * @param <PK>
 */
public interface AbstractDaoInterface<E, PK extends Serializable> {

	E save(E instance);

	E find(PK id);

	E findByQuery(String baseQuery);

	E findByQuery(String baseQuery, Map<String, Object> paramaters);

	E findByNativeQuery(String baseQuery, Map<String, Object> paramaters);

	void delete(E instance);

	E update(E instance);

	List<E> findAll();

	List<E> findAll(String baseQuery);

	List<E> findAll(String baseQuery, Map<String, Object> paramaters);

	Long count(String baseQuery, Map<String, Object> paramaters);

	void refresh(E instance);

	Object callProcedure(String procedureName, List<ProcedureParameter> queryArgs);

	List<E> findAllAndSort(String sortColumnName);

	Object callProcedureAndGetValue(String procedureName, List<ProcedureParameter> parametersOut, String parameterName);

}
