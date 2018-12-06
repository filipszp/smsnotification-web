package com.profsoft.smsnotifications.model.commonutils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Pomocnicze funkcje związane z kolekcjami
 * @author Maroo
 */
public class CollectionUtils {

	public static <T> List<T> list(T... values) {
		return new ArrayList<>(Arrays.asList(values));
	}
	
	public static boolean isEmpty(Collection<?> collection) {
		return org.apache.commons.collections4.CollectionUtils.isEmpty(collection);
	}
	
	public static int size(Object someKindOfCollection) {
		return org.apache.commons.collections4.CollectionUtils.size(someKindOfCollection);
	}
	
	public static boolean containsAny(Collection<?> coll1, Collection<?> coll2) {
		return org.apache.commons.collections4.CollectionUtils.containsAny(coll1, coll2);
	}
	
	public static <K, V> Map<K, V> map(Object... keysAndValues) {
		Map<K, V> map = new HashMap<K, V>();
		if ((keysAndValues.length % 2) != 0) {
			throw new IllegalArgumentException("Invalid keysAndValues array length");
		}
		for (int i = 0 ; i < keysAndValues.length ; i += 2) {
			@SuppressWarnings("unchecked")
			K key = (K) keysAndValues[i];
			@SuppressWarnings("unchecked")
			V value = (V) keysAndValues[i + 1];
			map.put(key, value);
		}
		return map;
	}
	
	@SafeVarargs
	public static <T> Set<T> set(T... values) {
		Set<T> set = new HashSet<>();
		for (T value : values) {
			set.add(value);
		}
		return set;
	}
	
	public static <T> T first(Collection<T> collection) {
		if (size(collection) > 0) {
			return collection.iterator().next();
		}
		return null;
	}
	
	public static <T> T last(Collection<T> collection) {
		if (size(collection) == 0) {
			return null;
		}
		if (collection instanceof List) {
			List<T> list = (List<T>) collection;
			return list.get(list.size() - 1);
		} else {
			Iterator<T> iterator = collection.iterator();
			while (true) {
				T element = iterator.next();
				if (!iterator.hasNext()) {
					return element;
				}
			}
		}
	}
	
	public static <T> List<T> unmodifiableList(T... values) {
		return Collections.unmodifiableList(Arrays.asList(values));
	}
}
