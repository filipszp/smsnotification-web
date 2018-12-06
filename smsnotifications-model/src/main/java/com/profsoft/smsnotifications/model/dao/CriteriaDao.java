package com.profsoft.smsnotifications.model.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import com.profsoft.smsnotifications.model.base.criteria.PagingCriteria;
import com.profsoft.smsnotifications.model.base.criteria.SearchCriteria;

/**
 *
 * @author Maroo
 * @param <E>
 * @param <PK>
 * @param <C>
 */
public interface CriteriaDao<E, PK extends Serializable, C extends SearchCriteria> extends AbstractDaoInterface<E, PK> {

	/**
	 * Sprawdza czy istnieje jakikolwiek obiekt spełniający kryteria.
	 *
	 * @param searchCriteria
	 * @return
	 */
	public boolean anyMatch(C searchCriteria);

	/**
	 * Zwraca listę obiektów na podstawie zadanych kryteriów uwzględniając sortowanie oraz stronicowanie.
	 *
	 * @param pagingCriteria
	 * @param searchCriteria
	 * @return
	 */
	public List<E> findByCriteria(PagingCriteria pagingCriteria, C searchCriteria);

	/**
	 * Zwraca listę obiektów na podstawie zadanych kryteriów.
	 *
	 * @param searchCriteria
	 * @return
	 */
	public List<E> findByCriteria(C searchCriteria);

	/**
	 * Zwraca pojedyńczy obiekt encji spełniający kryteria. Jeśli nie ma obiektu spełniającego kryteria, zostanie zwrócony null. Jęli jest więcej niż jeden obiekt spełniający kryteria, zostanie
	 * zgłoszony wyjątek NonUniqueResultException
	 *
	 * @param searchCriteria
	 * @return
	 */
	public E getByCriteria(C searchCriteria);

	/**
	 * Zlicza obiekty na podstawie podanych kryteriów
	 *
	 * @param searchCriteria
	 * @return
	 */
	public Long countByCriteria(C searchCriteria);

	/**
	 * Zwraca listę identyfikatorów encji spełniających kryteria.
	 *
	 * @param searchCriteria
	 * @return
	 */
	public List<PK> findIdsByCriteria(C searchCriteria);

	/**
	 *
	 * @param criteria
	 * @return
	 */
	long deleteByCriteria(C... criteria);

	/**
	 *
	 * @param <F>
	 * @param attribute
	 * @param value
	 * @param searchCriteria
	 * @return
	 */
	<F> long updateByCriteria(SingularAttribute<? super E, F> attribute, F value, C... searchCriteria);

}
