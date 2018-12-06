package com.profsoft.smsnotifications.model.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.profsoft.smsnotifications.model.dao.AbstractDaoInterface;
import com.profsoft.smsnotifications.model.data.AbstractEntity;
import com.profsoft.smsnotifications.model.utils.jpa.ProcedureParameter;

/**
 * @param <E>
 * @param <PK>
 * @author Marek
 */
public class AbstractDaoBean<E extends AbstractEntity, PK extends Serializable> implements AbstractDaoInterface<E, PK> {

	/**
	 * entity manager.
	 */
	@PersistenceContext
	protected EntityManager entityManager;

	protected final Class<E> entityClass;

	protected final Class<PK> primaryKeyClass;

	protected AbstractDaoBean() {
		super();
		this.entityClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.primaryKeyClass = (Class<PK>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}

	@Override
	public E save(final E instance) {
		entityManager.persist(instance);
		entityManager.flush();
		return instance;
	}

	@Override
	public E find(PK id) {
		E result = entityManager.find(entityClass, id);
		if (result == null) {
			throw new EntityNotFoundException("Can't find " + entityClass.getSimpleName() + " with id " + id);
		}
		return result;
	}

	@Override
	public E findByQuery(String baseQuery) {
		TypedQuery<E> resultQuery = entityManager.createQuery(baseQuery, entityClass);
		return resultQuery.getSingleResult();
	}

	@Override
	public E findByQuery(String baseQuery, Map<String, Object> paramaters) {
		TypedQuery<E> resultQuery = entityManager.createQuery(baseQuery, entityClass);
		for (String key : paramaters.keySet()) {
			resultQuery.setParameter(key, paramaters.get(key));
		}
		return resultQuery.getSingleResult();
	}

	@Override
	public E findByNativeQuery(String baseQuery, Map<String, Object> paramaters) {
		Query resultQuery = entityManager.createNativeQuery(baseQuery, entityClass);
		for (String key : paramaters.keySet()) {
			resultQuery.setParameter(key, paramaters.get(key));
		}
		return (E) resultQuery.getSingleResult();
	}

	@Override
	public void delete(E instance) {
		entityManager.remove(entityManager.merge(instance));

	}

	@Override
	public E update(E instance) {
		E merge = entityManager.merge(instance);
		entityManager.flush();
		return merge;
	}

	@Override
	public void refresh(E instance) {
		entityManager.refresh(instance);
	}

	@Override
	public List<E> findAll() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(entityClass);
		Root<E> root = criteriaQuery.from(entityClass);
		criteriaQuery.select(root);
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public List<E> findAll(String baseQuery) {
		TypedQuery<E> resultQuery = entityManager.createQuery(baseQuery, entityClass);
		return resultQuery.getResultList();
	}

	@Override
	public List<E> findAll(String baseQuery, Map<String, Object> paramaters) {
		TypedQuery<E> resultQuery = entityManager.createQuery(baseQuery, entityClass);
		for (String key : paramaters.keySet()) {
			resultQuery.setParameter(key, paramaters.get(key));
		}
		return resultQuery.getResultList();
	}

	@Override
	public Long count(String baseQuery, Map<String, Object> paramaters) {
		TypedQuery<Long> resultQuery = entityManager.createQuery(baseQuery, Long.class);
		for (String key : paramaters.keySet()) {
			resultQuery.setParameter(key, paramaters.get(key));
		}
		return resultQuery.getSingleResult();
	}

	@Override
	public List<E> findAllAndSort(String sortColumnName) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(entityClass);
		Root<E> root = criteriaQuery.from(entityClass);
		criteriaQuery.select(root);
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get(sortColumnName)));
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public Object callProcedure(String procedureName, List<ProcedureParameter> parametersIn) {
		StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery(procedureName);
		for (ProcedureParameter parameter : parametersIn) {
			procedureQuery.registerStoredProcedureParameter(parameter.getName(), parameter.getParamType(), ParameterMode.IN);
			procedureQuery.setParameter(parameter.getName(), parameter.getValue());
		}
		return procedureQuery.execute();
	}

	@Override
	public Object callProcedureAndGetValue(String procedureName, List<ProcedureParameter> parametersOut, String parameterName) {
		StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery(procedureName);
		for (ProcedureParameter parameter : parametersOut) {
			procedureQuery.registerStoredProcedureParameter(parameter.getName(), parameter.getParamType(), ParameterMode.OUT);
		}
		procedureQuery.execute();
		return procedureQuery.getOutputParameterValue(parameterName);
	}

}
