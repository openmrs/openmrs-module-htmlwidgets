package org.openmrs.module.htmlwidgets.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import org.openmrs.Concept;
import org.openmrs.ConceptClass;
import org.openmrs.ConceptDatatype;
import org.openmrs.ConceptWord;
import org.openmrs.OpenmrsMetadata;
import org.openmrs.OpenmrsObject;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.htmlwidgets.service.db.HtmlWidgetsDAO;
import org.springframework.transaction.annotation.Transactional;

/**
 * Base Implementation of the ReportService API
 */
public class HtmlWidgetsServiceImpl extends BaseOpenmrsService implements HtmlWidgetsService {

	//***** PROPERTIES *****
	private HtmlWidgetsDAO dao;
	
	/**
	 * Default Constructor
	 */
	public HtmlWidgetsServiceImpl() {}
	
	@Override
	@Transactional(readOnly = true)
	public List<ConceptWord> getConceptsList(String phrase, List<Locale> locales, boolean includeRetired,
        List<ConceptClass> requireClasses, List<ConceptClass> excludeClasses, List<ConceptDatatype> requireDatatypes,
        List<ConceptDatatype> excludeDatatypes, Concept answersToConcept, Integer start, Integer size)
        throws APIException
        {
		if (requireClasses == null)
			requireClasses = new Vector<ConceptClass>();
		if (excludeClasses == null)
			excludeClasses = new Vector<ConceptClass>();
		if (requireDatatypes == null)
			requireDatatypes = new Vector<ConceptDatatype>();
		if (excludeDatatypes == null)
			excludeDatatypes = new Vector<ConceptDatatype>();
		
		return dao.getConceptsList(phrase, locales, includeRetired, requireClasses, excludeClasses, requireDatatypes,
		    excludeDatatypes, answersToConcept, start, size);
		
        }

	
	/**
	 * @see HtmlWidgetsService#getAllMetadataByType(OpenmrsMetadata, boolean)
	 */
	@Override
	public <T extends OpenmrsMetadata> List<T> getAllMetadataByType(Class<T> type, boolean includeRetired) {
		return dao.getAllMetadataByType(type, includeRetired);
	}

	/**
	 * @see HtmlWidgetsService#getAllObjectsByType(OpenmrsObject)
	 */
	@Override
	public <T extends OpenmrsObject> List<T> getAllObjectsByType(Class<T> type) {
		return dao.getAllObjectsByType(type);
	}

	/**
	 * @see HtmlWidgetsService#getObject(OpenmrsObject, Integer)
	 */
	@Override
	public <T extends OpenmrsObject> T getObject(Class<T> type, Integer id) {
		return dao.getObject(type, id);
	}

	/**
	 * @see HtmlWidgetsService#getUserNamesById(String, List)
	 */
	@Override
	public Map<Integer, String> getUserNamesById(String query, List<String> roleNames) {
		return dao.getUserNamesById(query, roleNames);
	}	
	
	/**
	 * @see HtmlWidgetsService#getPersonNamesById(String, List)
	 */
	@Override
	public Map<Integer, String> getPersonNamesById(String query, List<String> roleNames) {
		return dao.getPersonNamesById(query, roleNames);
	}	
	
	//***** PROPERTY ACCESS *****

	/**
	 * @return the reportDAO
	 */
	public HtmlWidgetsDAO getDao() {
		return dao;
	}

	/**
	 * @param reportDAO the reportDAO to set
	 */
	public void setDao(HtmlWidgetsDAO dao) {
		this.dao = dao;
	}
}