package com.profsoft.smsnotifications.model.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CommonAbstractCriteria;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.transaction.annotation.Transactional;

import com.profsoft.smsnotifications.model.base.criteria.PagingCriteria;
import com.profsoft.smsnotifications.model.base.criteria.SearchCriteria;
import com.profsoft.smsnotifications.model.base.criteria.SortDirection;
import com.profsoft.smsnotifications.model.base.criteria.SortField;
import com.profsoft.smsnotifications.model.commonutils.CollectionUtils;
import com.profsoft.smsnotifications.model.commonutils.StringUtils;
import com.profsoft.smsnotifications.model.data.AbstractEntity;

/**
 *
 * @author Maroo
 * @param <E>
 * @param <PK>
 * @param <C>
 */
public class AbstractCriteriaDaoImpl<E extends AbstractEntity, PK extends Serializable, C extends SearchCriteria> extends AbstractDaoBean<E, PK> {

	public AbstractCriteriaDaoImpl() {
		super();
	}

	/**
	 * @see CriteriaDao#anyMatch(C searchCriteria)
	 */
	@Transactional(readOnly = true)
	public boolean anyMatch(C searchCriteria) {
		Collection<PK> ids = findIdsByCriteria(searchCriteria);
		return !ids.isEmpty();
	}

	/**
	 * @see CriteriaDao#findByCriteria(PagingCriteria criteria, C searchCriteria)
	 */
	@Transactional(readOnly = true)
	public List<E> findByCriteria(PagingCriteria criteria, C searchCriteria) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(entityClass);
		Root<E> root = criteriaQuery.from(entityClass);
		criteriaQuery.select(root);
		Predicate predicate = getCriteriaPredicate(criteriaQuery, root, searchCriteria);
		if (predicate != null) {
			criteriaQuery.where(predicate);
		}
		completeSortPart(criteria.getSortFields(), root, criteriaQuery);
		TypedQuery<E> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(criteria.getDisplayStart());
		query.setMaxResults(criteria.getDisplaySize());
		return query.getResultList();
	}

	/**
	 * @see CriteriaDao#findByCriteria(C searchCriteria)
	 */
	@Transactional(readOnly = true)
	public List<E> findByCriteria(C searchCriteria) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(entityClass);
		Root<E> root = criteriaQuery.from(entityClass);
		criteriaQuery.select(root);
		Predicate predicate = getCriteriaPredicate(criteriaQuery, root, searchCriteria);
		if (predicate != null) {
			criteriaQuery.where(predicate);
		}
		TypedQuery<E> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}

	/**
	 * @see CriteriaDao#getByCriteria(C searchCriteria)
	 */
	@Transactional(readOnly = true)
	public E getByCriteria(C searchCriteria) {
		Collection<PK> ids = findIdsByCriteria(searchCriteria);
		if (ids.isEmpty()) {
			return null;
		} else if (ids.size() > 1) {
			throw new NonUniqueResultException("Expected 0 or 1 result but found " + ids.size());
		}
		return find(ids.iterator().next());
	}

	/**
	 * @see CriteriaDao#countByCriteria(C searchCriteria)
	 */
	@Transactional(readOnly = true)
	public Long countByCriteria(C searchCriteria) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<E> root = criteriaQuery.from(entityClass);
		criteriaQuery.select(criteriaBuilder.count(root));
		Predicate predicate = getCriteriaPredicate(criteriaQuery, root, searchCriteria);
		if (predicate != null) {
			criteriaQuery.where(predicate);
		}
		Long count = entityManager.createQuery(criteriaQuery).getSingleResult();
		return count;
	}

	/**
	 * @see CriteriaDao#findIdsByCriteria(C searchCriteria)
	 * @param searchCriteria
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<PK> findIdsByCriteria(C searchCriteria) {
		EntityType<E> entityType = entityManager.getMetamodel().entity(entityClass);
		SingularAttribute<? super E, PK> idAtribute = entityType.getId(primaryKeyClass);

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PK> criteriaQuery = criteriaBuilder.createQuery(idAtribute.getJavaType());
		Root<E> root = criteriaQuery.from(entityClass);
		criteriaQuery.select(root.get(idAtribute));
		Predicate predicate = getCriteriaPredicate(criteriaQuery, root, searchCriteria);
		if (predicate != null) {
			criteriaQuery.where(predicate);
		}
		TypedQuery<PK> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}

	/**
	 * @see ReadWriteCriteriaDao#deleteByCriteria(SearchCriteria...)
	 */
	@Transactional
	public long deleteByCriteria(@SuppressWarnings("unchecked") C... criteria) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaDelete<E> criteriaDelete = builder.createCriteriaDelete(entityClass);

		Root<E> root = criteriaDelete.from(entityClass);
		Predicate predicate = getCriteriaPredicates(criteriaDelete, root, false, criteria);
		if (predicate != null) {
			criteriaDelete.where(predicate);
		}

		Query deleteQuery = entityManager.createQuery(criteriaDelete);
		return deleteQuery.executeUpdate();
	}

	/**
	 * @see ReadWriteCriteriaDao#updateByCriteria(SingularAttribute, Object, SearchCriteria...)
	 */
	@Transactional
	public <F> long updateByCriteria(SingularAttribute<? super E, F> attribute, F value, @SuppressWarnings("unchecked") C... searchCriteria) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaUpdate<E> criteriaUpdate = builder.createCriteriaUpdate(entityClass);

		Root<E> root = criteriaUpdate.from(entityClass);
		Predicate predicate = getCriteriaPredicates(criteriaUpdate, root, false, searchCriteria);
		if (predicate != null) {
			criteriaUpdate.where(predicate);
		}

		criteriaUpdate.set(attribute, value);

		Query updateQuery = entityManager.createQuery(criteriaUpdate);
		return updateQuery.executeUpdate();
	}

	protected void completeSortPart(List<SortField> sorts, Root<E> root, CriteriaQuery query) {
		if (sorts != null && sorts.size() > 0) {
			List<Order> orders = new ArrayList<Order>();
			for (SortField s : sorts) {
				orders.add(createOrder(root, s.getField(), s.getDirection()));
			}
			query.orderBy(orders);
		}
	}

	protected Collection<Predicate> createCriteriaPredicate(CriteriaBuilder builder, CommonAbstractCriteria query, Root<E> root, C searchCriteria) {
		return new ArrayList<>();
	}

	protected final <A> Predicate equal(A value, Expression<A> expression) {
		if (value == null) {
			return null;
		}
		return entityManager.getCriteriaBuilder().equal(expression, value);
	}

	protected final <A extends Comparable<? super A>> Predicate between(A leftValue, A rightValue, Expression<A> expression, boolean isRightClosed) {
		if ((leftValue == null) && (rightValue == null)) {
			return null;
		}
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		List<Predicate> predicates = new ArrayList<>();
		if (leftValue != null) {
			predicates.add(builder.greaterThanOrEqualTo(expression, leftValue));
		}
		if (rightValue != null) {
			if (isRightClosed) {
				predicates.add(builder.lessThanOrEqualTo(expression, rightValue));
			} else {
				predicates.add(builder.lessThan(expression, rightValue));
			}
		}
		return builder.and(predicates.toArray(new Predicate[predicates.size()]));
	}

	protected final <A extends Comparable<? super A>> Predicate lessThanOrEqualTo(A rightValue, Expression<A> expression, boolean isRightClosed) {
		if (rightValue == null) {
			return null;
		}
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		List<Predicate> predicates = new ArrayList<>();

		if (rightValue != null) {
			if (isRightClosed) {
				predicates.add(builder.lessThanOrEqualTo(expression, rightValue));
			} else {
				predicates.add(builder.lessThan(expression, rightValue));
			}
		}
		return builder.and(predicates.toArray(new Predicate[predicates.size()]));
	}

	protected final <A extends Comparable<? super A>> Predicate greaterThanOrEqualTo(A leftValue, Expression<A> expression, boolean isLeftClosed) {
		if (leftValue == null) {
			return null;
		}
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		List<Predicate> predicates = new ArrayList<>();

		if (leftValue != null) {
			if (isLeftClosed) {
				predicates.add(builder.greaterThanOrEqualTo(expression, leftValue));
			} else {
				predicates.add(builder.greaterThan(expression, leftValue));
			}
		}
		return builder.and(predicates.toArray(new Predicate[predicates.size()]));
	}

	protected final Predicate likeNoCase(Object value, Expression<String>... expressions) {
		return like(value, true, Arrays.asList(expressions));
	}

	protected final Predicate like(Object value, Expression<String>... expressions) {
		return like(value, false, Arrays.asList(expressions));
	}

	protected final Predicate like(Object value, boolean ignoreCase, Expression<String>... expressions) {
		return like(value, ignoreCase, Arrays.asList(expressions));
	}

	protected final Predicate like(Object value, boolean ignoreCase, List<Expression<String>> expressions) {
		if (!StringUtils.isEmpty(value)) {
			String like = StringUtils.toSqlLike(ignoreCase ? value.toString().toUpperCase() : value.toString());

			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			List<Predicate> predicates = new ArrayList<>();
			for (Expression<String> expression : expressions) {
				if (ignoreCase) {
					predicates.add(builder.like(builder.upper(expression), like, StringUtils.SQL_LIKE_ESCAPE_CHAR));
				} else {
					predicates.add(builder.like(expression, like, StringUtils.SQL_LIKE_ESCAPE_CHAR));
				}
			}

			return builder.or(predicates.toArray(new Predicate[predicates.size()]));
		}
		return null;
	}

	protected final <A> Predicate in(Collection<A> values, Expression<A> expression) {
		if (values == null) {
			return null;
		}

		values = values.stream().filter(value -> value != null).collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(values)) {
			return expression.in(values);
		}

		return null;
	}

	/**
	 * Sprawdza czy wartość pola jest null
	 *
	 * @param expression
	 * @return
	 */
	protected final <A> Predicate isNull(Expression<A> expression) {
		return entityManager.getCriteriaBuilder().isNull(expression);
	}

	/**
	 * Sprawdza czy wartość pola nie jest null
	 *
	 * @param expression
	 * @return
	 */
	protected final <A> Predicate isNotNull(Expression<A> expression) {
		return entityManager.getCriteriaBuilder().isNotNull(expression);
	}

	/**
	 * Sprawdza czy wartość pola jest null albo not null w zależności od warunku condition = true oznacza sprawdzenie czy not null.
	 *
	 * @param expression
	 *
	 * @return
	 */
	protected final <A> Predicate isSet(Boolean condition, Expression<A> expression) {
		if (condition == null) {
			return null;
		}

		if (condition.equals(Boolean.TRUE)) {
			return isNotNull(expression);
		}

		return isNull(expression);
	}

	/**
	 * Odpowiednik {@link CriteriaBuilder#or(Predicate...)} odporny na nulle. W przekazanej liście predykatów może być dowolna mieszanka rzeczywistych predykatów i nulli, zwracany predykat zawiera
	 * alternatywę tylko tych, które != null.
	 *
	 * @param predicates predykaty (w elementach dopuszczalne są nulle)
	 *
	 * @return alternatywa wszystkich niepustych predykatów przekazanych jako argument
	 */
	protected final Predicate or(Predicate... predicates) {
		if (predicates != null) {
			List<Predicate> notNullPredicates = new ArrayList<>();
			for (Predicate predicate : predicates) {
				if (predicate != null) {
					notNullPredicates.add(predicate);
				}
			}
			if (!notNullPredicates.isEmpty()) {
				return entityManager.getCriteriaBuilder().or(notNullPredicates.toArray(new Predicate[notNullPredicates.size()]));
			}
		}
		return null;
	}

	/**
	 * Negacja podanego predykatu.
	 *
	 * @param predicate
	 *
	 * @return
	 */
	protected final Predicate not(Expression<Boolean> predicate) {
		if (predicate == null) {
			return null;
		}
		return entityManager.getCriteriaBuilder().not(predicate);
	}

	private Predicate getCriteriaPredicate(CommonAbstractCriteria query, Root<E> root, C searchCriteria) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		List<Predicate> predicates = new ArrayList<>();
		Collection<Predicate> predicatesForCriteria = createCriteriaPredicate(builder, query, root, searchCriteria);
		predicatesForCriteria.removeIf(Objects::isNull);
		predicates.add(builder.and(predicatesForCriteria.toArray(new Predicate[0])));
		return builder.or(predicates.toArray(new Predicate[0]));
	}

	private Order createOrder(Root<E> root, String fieldName, SortDirection sortDirection) {
		Path<?> sortPath = root;
		String[] sortFields = StringUtils.split(fieldName, ".");
		for (String sortField : sortFields) {
			sortPath = sortPath.get(sortField);
		}
		switch (sortDirection) {
			case ASC:
				return entityManager.getCriteriaBuilder().asc(sortPath);
			case DESC:
				return entityManager.getCriteriaBuilder().desc(sortPath);
			default:
				return entityManager.getCriteriaBuilder().asc(sortPath);
		}
	}

	/**
	 * Tworzy predykaty na podstawie kryteriów wyszukiwania. Dla każdego obiektu przekazanego w parametrze criteria tworzony jest za pomocą
	 * {@link #createCriteriaPredicates(Root, AbstractSearchCriteria)} predykat (który może zawierać kilka warunków), a następnie wszystkie te predykaty są łączone spójnikiem OR. Jeżeli parametr
	 * forceFetch == true, to wywoływane są metody {@link #createFetchClauses(Root)} oraz {@link #createFetchClauses(Root, AbstractSearchCriteria)}
	 *
	 * @param root zapytanie, do którego będą tworzone predykaty
	 * @param criteria kryteria, które będą tłumaczone na predykaty
	 *
	 * @return
	 */
	private Predicate getCriteriaPredicates(CommonAbstractCriteria query, Root<E> root, boolean forceFetch, @SuppressWarnings("unchecked") C... criteria) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		List<Predicate> predicates = new ArrayList<>();

		if (forceFetch) {
			createFetchClauses(root);
		}

		if (criteria.length > 0) {
			for (C c : criteria) {
				Collection<Predicate> predicatesForCriteria = createCriteriaPredicate(builder, query, root, c);
				predicatesForCriteria.removeAll(Collections.singleton(null));
				predicates.add(builder.and(predicatesForCriteria.toArray(new Predicate[0])));
				if (forceFetch) {
					createFetchClauses(root, c);
				}
			}
			return builder.or(predicates.toArray(new Predicate[0]));
		}

		return null;
	}

	/**
	 * Dodaje do zapytanie klauzule FETCH powodujące zachłanne pobieranie atrybutów. Ta metoda jest wywoływana raz przy tworzeniu zapytania.
	 * <p>
	 * Domyślna implementacja jest pusta.
	 *
	 * @param root
	 */
	protected void createFetchClauses(Root<E> root) {
	}

	/**
	 * Dodaje do zapytanie klauzule FETCH powodujące zachłanne pobieranie atrybutów. Ta metoda jest wywoływana dla każdego obiektu C przekazanego do metody
	 * {@link #getCriteriaPredicates(Root, boolean, AbstractSearchCriteria...)} - dzięki temu można dodawać klauzule FETCH warunkowo zależnie od kryteriów wyszukiwania.
	 * <p>
	 * Domyślna implementacja jest pusta.
	 *
	 * @param root
	 * @param criteria
	 */
	protected void createFetchClauses(Root<E> root, C criteria) {
	}
}
