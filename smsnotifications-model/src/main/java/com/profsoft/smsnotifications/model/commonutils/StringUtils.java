package com.profsoft.smsnotifications.model.commonutils;

import java.util.Arrays;

/**
 *
 * @author Maroo
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

	/**
	 * Znaki końca linii \r\n
	 */
	public static final String CRLF = CR + LF;

	/**
	 * Znaki końca linii \r\n jako tablica bajtów
	 */
	public static final byte[] CRLF_BYTES = {0x0D, 0x0A};

	/**
	 * Znak końca pliku (SUB aka CTRL-Z)
	 */
	public static final String EOF = "\u001A";

	/**
	 * Znak końca pliku (SUB aka CTRL-Z) jako tablica bajtów
	 */
	public static final byte[] EOF_BYTES = {0x1A};

	/**
	 * Znak specjalny do funkcji like
	 */
	public static final char SQL_LIKE_ESCAPE_CHAR = '\\';

	/**
	 * Znaki używane we wzorcach przekazywanych do {@link #like(Object, boolean, List)}
	 */
	private static final String[] WILDCARD_SEARCH_LIST = {"%", "_", "*", "?"};

	/**
	 * Znaki, na które będą zamieniane {@link #WILDCARD_SEARCH_LIST} przy tworzeniu zapytania
	 */
	private static final String[] WILDCARD_REPLACEMENT_LIST = {StringUtils.join(SQL_LIKE_ESCAPE_CHAR, '%'), StringUtils.join(SQL_LIKE_ESCAPE_CHAR, '_'), "%", "_"};
	
	public static boolean isEmpty(Object... objs) {
		return Arrays.stream(objs).allMatch(o -> isEmpty(o));
	}

	public static boolean isEmpty(Object o) {
		if (o != null) {
			if (o.getClass().isArray()) {
				return ((Object[]) o).length == 0;
			} else {
				return o.toString().length() == 0;
			}
		}
		return true;
	}

	public static String toSqlLike(String text) {
		return replaceEach(text, WILDCARD_SEARCH_LIST, WILDCARD_REPLACEMENT_LIST);
	}

}
