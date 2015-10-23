package org.openmrs.module.htmlwidgets.service.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.openmrs.api.db.hibernate.DbSessionFactory;  
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.openmrs.OpenmrsMetadata;
import org.openmrs.OpenmrsObject;
import org.openmrs.Retireable;
import org.openmrs.Voidable;
import org.openmrs.module.htmlwidgets.service.HtmlWidgetsService;

/**
 * Hibernate Implementation of the HtmlWidgetsDAO
 */
public class HibernateHtmlWidgetsDAO implements HtmlWidgetsDAO {
	
	//***** PROPERTIES
	private DbSessionFactory sessionFactory;
	
	/**
	 * Default Constructor
	 */
	public HibernateHtmlWidgetsDAO() {}
	
	/**
	 * @see HtmlWidgetsService#getAllMetadataByType(OpenmrsMetadata, boolean)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T extends OpenmrsMetadata> List<T> getAllMetadataByType(Class<T> type, boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(type);
		if (!includeRetired) {
			criteria.add(Expression.eq("retired", false));
		}
		criteria.addOrder(Order.asc("name"));
		return criteria.list();
	}

	/**
	 * @see HtmlWidgetsService#getAllObjectsByType(OpenmrsObject)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T extends OpenmrsObject> List<T> getAllObjectsByType(Class<T> type) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(type);
		if (Retireable.class.isAssignableFrom(type)) {
			criteria.add(Expression.eq("retired", false));
		}
		else if (Voidable.class.isAssignableFrom(type)) {
			criteria.add(Expression.eq("voided", false));
		}
		return criteria.list();
	}

	/**
	 * @see HtmlWidgetsService#getObject(OpenmrsObject, Integer)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T extends OpenmrsObject> T getObject(Class<T> type, Integer id) {
		return (T) sessionFactory.getCurrentSession().get(type, id);
	}
	
	/**
	 * @see HtmlWidgetsService#getUserNamesById(Class, String, List)
	 */
	@Override
	public Map<Integer, String> getUserNamesById(String query, List<String> roleNames) {
		
		StringBuilder hql = new StringBuilder();
		hql.append("select 		u.userId, pn.givenName, pn.familyName ");
		hql.append("from		User u, PersonName pn ");
		hql.append("where		u.person = pn.person ");
		
		// Not sure how to join on user_role using hql, so doing it this way for now...
		List<Integer> limitUserIds = getUserIdsForRoles(roleNames);
		if (limitUserIds != null) {
			hql.append("and		u.userId in (:limitUserIds) ");
		}
		if (StringUtils.isNotEmpty(query)) {
			hql.append("and (");
			String[] nameSplit = query.toLowerCase().split(" ");
			for (int i=0; i<nameSplit.length; i++) {
				String lc = "like '%" + nameSplit[i] + "%'";
				hql.append((i == 0 ? "" : "or ") + "lower(pn.givenName) " + lc + " or lower(pn.middleName) " + lc + " ");
				hql.append("or lower(u.username) " + lc + " or lower(pn.familyName) " + lc + " or lower(pn.familyName2) " + lc + " ");
			}
			hql.append(") ");
		}
		hql.append("order by	pn.preferred asc ");
		Query q = getSessionFactory().getCurrentSession().createQuery(hql.toString());
		if (limitUserIds != null) {
			q.setParameterList("limitUserIds", limitUserIds);
		}

		Map<Integer, String> m = new HashMap<Integer, String>();
		for (Object o : q.list()) {
			Object[] row = (Object[])o;
			m.put((Integer)row[0], row[2] + ", " + row[1]);
		}
		return m;
	}
	
	/**
	 * @see HtmlWidgetsService#getPersonNamesById(Class, String, List)
	 */
	@Override
	public Map<Integer, String> getPersonNamesById(String query, List<String> roleNames) {
		
		StringBuilder hql = new StringBuilder();
		hql.append("select 		p.personId, pn.givenName, pn.familyName ");
		hql.append("from		Person p, PersonName pn ");
		hql.append("where		p = pn.person ");
		
		// Not sure how to join on user_role using hql, so doing it this way for now...
		List<Integer> limitPersonIds = getPersonIdsForRoles(roleNames);
		if (limitPersonIds != null) {
			hql.append("and		p.personId in (:limitPersonIds) ");
		}
		if (StringUtils.isNotEmpty(query)) {
			hql.append("and (");
			String[] nameSplit = query.toLowerCase().split(" ");
			for (int i=0; i<nameSplit.length; i++) {
				String lc = "like '%" + nameSplit[i] + "%'";
				hql.append((i == 0 ? "" : "or ") + "lower(pn.givenName) " + lc + " or lower(pn.middleName) " + lc + " ");
				hql.append("or lower(u.username) " + lc + " or lower(pn.familyName) " + lc + " or lower(pn.familyName2) " + lc + " ");
			}
			hql.append(") ");
		}
		hql.append("order by	pn.preferred asc ");
		Query q = getSessionFactory().getCurrentSession().createQuery(hql.toString());
		if (limitPersonIds != null) {
			q.setParameterList("limitPersonIds", limitPersonIds);
		}

		Map<Integer, String> m = new HashMap<Integer, String>();
		for (Object o : q.list()) {
			Object[] row = (Object[])o;
			m.put((Integer)row[0], row[1] + ", " + row[2]);
		}
		return m;
	}
	
	/**
	 * @see HtmlWidgetsService#getUserIdsForRoles(List)
	 */
	@SuppressWarnings("unchecked")
	private List<Integer> getUserIdsForRoles(List<String> roleNames) {
		// Not sure how to join on user_role using hql, so doing it this way for now...
		List<Integer> limitUserIds = null;
		if (roleNames != null && roleNames.size() > 0) {
			String roleQuery = "select user_id from user_role where role in (:roleNames)";
			SQLQuery sq = sessionFactory.getCurrentSession().createSQLQuery(roleQuery);
			sq.setParameterList("roleNames", roleNames);
			limitUserIds = (List<Integer>)sq.list();
		}
		return limitUserIds;
	}
	
	/**
	 * @see HtmlWidgetsService#getUserIdsForRoles(List)
	 */
	@SuppressWarnings("unchecked")
	private List<Integer> getPersonIdsForRoles(List<String> roleNames) {
		// Not sure how to join on user_role using hql, so doing it this way for now...
		List<Integer> limitPersonIds = null;
		if (roleNames != null && roleNames.size() > 0) {
			String roleQuery = "select u.person_id from user_role r, users u where u.user_id = r.user_id and r.role in (:roleNames)";
			SQLQuery sq = sessionFactory.getCurrentSession().createSQLQuery(roleQuery);
			sq.setParameterList("roleNames", roleNames);
			limitPersonIds = (List<Integer>)sq.list();
		}
		return limitPersonIds;
	}
	
	//***** PROPERTY ACCESS *****

	/**
	 * @return the sessionFactory
	 */
	public DbSessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	public void setSessionFactory(DbSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}