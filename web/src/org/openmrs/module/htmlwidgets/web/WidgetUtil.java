/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.htmlwidgets.web;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.openmrs.module.htmlwidgets.util.ReflectionUtil;
import org.openmrs.module.htmlwidgets.web.handler.WidgetHandler;
import org.openmrs.util.HandlerUtil;

/**
 * Utility library for Widgets
 */
public class WidgetUtil {
	
	/**
	 * Return the object value of the passed type, given it's String representation
	 * 
	 * @param input the String representation
	 * @param type the type to return
	 * @return the Object of the passed type, given the passed input
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Object> T parseInput(String input, Class<? extends T> type) {
		try {
			WidgetHandler handler = HandlerUtil.getPreferredHandler(WidgetHandler.class, type);
			return (T) handler.parse(input, type);
		}
		catch (Exception e) {
			throw new RuntimeException("Unable to convert input <" + input + "> to type " + type.getSimpleName(), e);
		}
	}
	
	/**
	 * Return the object value of the passed type, given it's String representation
	 * 
	 * @param input the String representation
	 * @param type the type to return
	 * @return the Object of the passed type, given the passed input
	 */
	@SuppressWarnings("unchecked")
	public static Object parseInput(Object input, Class<?> type, Class<? extends Collection> collectionType) {
		try {
			if (input instanceof Object[] || input instanceof Iterable) {
				Collection<Object> collection = Set.class.isAssignableFrom(collectionType) ? new HashSet<Object>()
				        : new ArrayList<Object>();
				
				if (input instanceof Object[]) {
					input = Arrays.asList((Object[]) input);
				}
				
				for (Object object : (Iterable<Object>) input) {
					WidgetHandler handler = HandlerUtil.getPreferredHandler(WidgetHandler.class, type);
					Object parsed = handler.parse(object.toString(), type);
					collection.add(parsed);
				}
				return collection;
			} else {
				WidgetHandler handler = HandlerUtil.getPreferredHandler(WidgetHandler.class, type);
				return handler.parse(input.toString(), type);
			}
		}
		catch (Exception e) {
			throw new RuntimeException("Unable to convert input <" + input + "> to type " + type.getSimpleName(), e);
		}
	}
	
	/**
	 * Attempts to retrieve, from the request, the parameter with the given parameter name. Then
	 * attempts to retrieve a Field from the passed object with the given property name. Then
	 * transforms the request parameter into an object of this type
	 * 
	 * @param request the request to retrieve the value from
	 * @param parameterName the name of the parameter to retrieve from the request
	 * @param object the object to search for an appropriate property
	 * @param propertyName the name of the property
	 * @return the Object of the passed type, given the passed input
	 */
	public static Object getFromRequest(HttpServletRequest request, String parameterName, Object object, String propertyName) {
		if (object == null || propertyName == null) {
			throw new IllegalArgumentException("All parameters must be non-null");
		}
		Field f = ReflectionUtil.getField(object.getClass(), propertyName);
		return getFromRequest(request, parameterName, f);
	}
	
	/**
	 * Return the object value of the passed type, given it's String representation
	 */
	@SuppressWarnings("unchecked")
	public static Object getFromRequest(HttpServletRequest request, String parameterName, Field field) {
		Class<? extends Collection<?>> collectionType = null;
		Class<?> type = null;
		if (ReflectionUtil.isCollection(field)) {
			collectionType = (Class<? extends Collection<?>>) ReflectionUtil.getFieldType(field);
			type = (Class<?>) ReflectionUtil.getGenericTypes(field)[0];
		} else {
			type = ReflectionUtil.getFieldType(field);
		}
		return getFromRequest(request, parameterName, type, collectionType);
	}
	
	/**
	 * Return the object value of the passed type, given it's String representation
	 */
	@SuppressWarnings("unchecked")
	public static Object getFromRequest(HttpServletRequest request, String paramName, Class<?> type,
	                                    Class<? extends Collection> collectionType) {
		Object ret = null;
		if (collectionType != null) {
			String[] paramVals = request.getParameterValues(paramName);
			if (paramVals != null) {
				Collection defaultValue = Set.class.isAssignableFrom(collectionType) ? new HashSet() : new ArrayList();
				for (String val : paramVals) {
					if (StringUtils.isNotEmpty(val)) {
						WidgetHandler h = HandlerUtil.getPreferredHandler(WidgetHandler.class, type);
						defaultValue.add(h.parse(val, type));
					}
				}
				if (!defaultValue.isEmpty()) {
					ret = defaultValue;
				}
			}
		} else {
			String paramVal = request.getParameter(paramName);
			if (StringUtils.isNotEmpty(paramVal)) {
				WidgetHandler h = HandlerUtil.getPreferredHandler(WidgetHandler.class, type);
				ret = h.parse(paramVal, type);
			}
		}
		return ret;
	}
}
