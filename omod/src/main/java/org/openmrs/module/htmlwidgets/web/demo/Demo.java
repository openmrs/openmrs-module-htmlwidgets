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
package org.openmrs.module.htmlwidgets.web.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.openmrs.Cohort;
import org.openmrs.Concept;
import org.openmrs.Drug;
import org.openmrs.EncounterType;
import org.openmrs.Form;
import org.openmrs.Location;
import org.openmrs.OrderType;
import org.openmrs.PatientIdentifierType;
import org.openmrs.Person;
import org.openmrs.PersonAttributeType;
import org.openmrs.Program;
import org.openmrs.ProgramWorkflow;
import org.openmrs.ProgramWorkflowState;
import org.openmrs.User;
import org.openmrs.api.PatientSetService.PatientLocationMethod;

/**
 * Demonstration / test bean to encapsulate the properties that can be set
 * using the Widget Framework
 */
public class Demo {
		
	// ******* CONSTRUCTORS ********
	public Demo() { }
	
	// ******* PROPERTIES ********
	private boolean booleanPrimitive = false;
	private int intPrimitive = 10;
	private long longPrimitive = 20L;
	private double doublePrimitive = 2.2;
	private float floatPrimitive = 3.14159F;
	private char charPrimitive = 'j';
	
	private Boolean booleanObj = Boolean.TRUE;
	private Class<?> classObj = Date.class;
	private Date dateObj = new Date();
	private Double doubleObj = new Double(10.22);
	private Integer integerObj = new Integer(89);
	private Object objectObj;
	private PatientLocationMethod enumObj = PatientLocationMethod.PATIENT_HEALTH_CENTER;
	private String stringObj = "Hello world";
	
	private Cohort cohortObj;
	private Concept conceptObj;
	private Drug drugObj;
	private EncounterType encounterTypeObj;
	private Form formObj;
	private Location locationObj;
	private OrderType orderTypeObj;
	private PatientIdentifierType patientIdentifierTypeObj;
	private PersonAttributeType personAttributeTypeObj;
	private Program programObj;
	private ProgramWorkflow programWorkflowObj;
	private ProgramWorkflowState programWorkflowStateObj;
	private User userObj;
	private Person personObj;
	
	private List<Location> listObj = new ArrayList<Location>();
	private Properties propertiesObj;
	private Set<Concept> setObj = new HashSet<Concept>();
	
	//***** PROPERTY ACCESS *****
	
	/**
	 * @return the booleanPrimitive
	 */
	public boolean getBooleanPrimitive() {
		return booleanPrimitive;
	}
	/**
	 * @param booleanPrimitive the booleanPrimitive to set
	 */
	public void setBooleanPrimitive(boolean booleanPrimitive) {
		this.booleanPrimitive = booleanPrimitive;
	}
	/**
	 * @return the intPrimitive
	 */
	public int getIntPrimitive() {
		return intPrimitive;
	}
	/**
	 * @param intPrimitive the intPrimitive to set
	 */
	public void setIntPrimitive(int intPrimitive) {
		this.intPrimitive = intPrimitive;
	}
	/**
	 * @return the longPrimitive
	 */
	public long getLongPrimitive() {
		return longPrimitive;
	}
	/**
	 * @param longPrimitive the longPrimitive to set
	 */
	public void setLongPrimitive(long longPrimitive) {
		this.longPrimitive = longPrimitive;
	}
	/**
	 * @return the doublePrimitive
	 */
	public double getDoublePrimitive() {
		return doublePrimitive;
	}
	/**
	 * @param doublePrimitive the doublePrimitive to set
	 */
	public void setDoublePrimitive(double doublePrimitive) {
		this.doublePrimitive = doublePrimitive;
	}
	/**
	 * @return the floatPrimitive
	 */
	public float getFloatPrimitive() {
		return floatPrimitive;
	}
	/**
	 * @param floatPrimitive the floatPrimitive to set
	 */
	public void setFloatPrimitive(float floatPrimitive) {
		this.floatPrimitive = floatPrimitive;
	}
	/**
	 * @return the charPrimitive
	 */
	public char getCharPrimitive() {
		return charPrimitive;
	}
	/**
	 * @param charPrimitive the charPrimitive to set
	 */
	public void setCharPrimitive(char charPrimitive) {
		this.charPrimitive = charPrimitive;
	}
	/**
	 * @return the booleanObj
	 */
	public Boolean getBooleanObj() {
		return booleanObj;
	}
	/**
	 * @param booleanObj the booleanObj to set
	 */
	public void setBooleanObj(Boolean booleanObj) {
		this.booleanObj = booleanObj;
	}
	/**
	 * @return the classObj
	 */
	public Class<?> getClassObj() {
		return classObj;
	}
	/**
	 * @param classObj the classObj to set
	 */
	public void setClassObj(Class<?> classObj) {
		this.classObj = classObj;
	}
	/**
	 * @return the dateObj
	 */
	public Date getDateObj() {
		return dateObj;
	}
	/**
	 * @param dateObj the dateObj to set
	 */
	public void setDateObj(Date dateObj) {
		this.dateObj = dateObj;
	}
	/**
	 * @return the doubleObj
	 */
	public Double getDoubleObj() {
		return doubleObj;
	}
	/**
	 * @param doubleObj the doubleObj to set
	 */
	public void setDoubleObj(Double doubleObj) {
		this.doubleObj = doubleObj;
	}
	/**
	 * @return the integerObj
	 */
	public Integer getIntegerObj() {
		return integerObj;
	}
	/**
	 * @param integerObj the integerObj to set
	 */
	public void setIntegerObj(Integer integerObj) {
		this.integerObj = integerObj;
	}
	/**
	 * @return the objectObj
	 */
	public Object getObjectObj() {
		return objectObj;
	}
	/**
	 * @param objectObj the objectObj to set
	 */
	public void setObjectObj(Object objectObj) {
		this.objectObj = objectObj;
	}
	/**
	 * @return the enumObj
	 */
	public PatientLocationMethod getEnumObj() {
		return enumObj;
	}
	/**
	 * @param enumObj the enumObj to set
	 */
	public void setEnumObj(PatientLocationMethod enumObj) {
		this.enumObj = enumObj;
	}
	/**
	 * @return the stringObj
	 */
	public String getStringObj() {
		return stringObj;
	}
	/**
	 * @param stringObj the stringObj to set
	 */
	public void setStringObj(String stringObj) {
		this.stringObj = stringObj;
	}
	/**
	 * @return the cohortObj
	 */
	public Cohort getCohortObj() {
		return cohortObj;
	}
	/**
	 * @param cohortObj the cohortObj to set
	 */
	public void setCohortObj(Cohort cohortObj) {
		this.cohortObj = cohortObj;
	}
	/**
	 * @return the conceptObj
	 */
	public Concept getConceptObj() {
		return conceptObj;
	}
	/**
	 * @param conceptObj the conceptObj to set
	 */
	public void setConceptObj(Concept conceptObj) {
		this.conceptObj = conceptObj;
	}
	/**
	 * @return the drugObj
	 */
	public Drug getDrugObj() {
		return drugObj;
	}
	/**
	 * @param drugObj the drugObj to set
	 */
	public void setDrugObj(Drug drugObj) {
		this.drugObj = drugObj;
	}
	/**
	 * @return the encounterTypeObj
	 */
	public EncounterType getEncounterTypeObj() {
		return encounterTypeObj;
	}
	/**
	 * @param encounterTypeObj the encounterTypeObj to set
	 */
	public void setEncounterTypeObj(EncounterType encounterTypeObj) {
		this.encounterTypeObj = encounterTypeObj;
	}
	/**
	 * @return the formObj
	 */
	public Form getFormObj() {
		return formObj;
	}
	/**
	 * @param formObj the formObj to set
	 */
	public void setFormObj(Form formObj) {
		this.formObj = formObj;
	}
	/**
	 * @return the locationObj
	 */
	public Location getLocationObj() {
		return locationObj;
	}
	/**
	 * @param locationObj the locationObj to set
	 */
	public void setLocationObj(Location locationObj) {
		this.locationObj = locationObj;
	}
	/**
	 * @return the orderTypeObj
	 */
	public OrderType getOrderTypeObj() {
		return orderTypeObj;
	}
	/**
	 * @param orderTypeObj the orderTypeObj to set
	 */
	public void setOrderTypeObj(OrderType orderTypeObj) {
		this.orderTypeObj = orderTypeObj;
	}
	/**
	 * @return the patientIdentifierTypeObj
	 */
	public PatientIdentifierType getPatientIdentifierTypeObj() {
		return patientIdentifierTypeObj;
	}
	/**
	 * @param patientIdentifierTypeObj the patientIdentifierTypeObj to set
	 */
	public void setPatientIdentifierTypeObj(
			PatientIdentifierType patientIdentifierTypeObj) {
		this.patientIdentifierTypeObj = patientIdentifierTypeObj;
	}
	/**
	 * @return the personAttributeTypeObj
	 */
	public PersonAttributeType getPersonAttributeTypeObj() {
		return personAttributeTypeObj;
	}
	/**
	 * @param personAttributeTypeObj the personAttributeTypeObj to set
	 */
	public void setPersonAttributeTypeObj(PersonAttributeType personAttributeTypeObj) {
		this.personAttributeTypeObj = personAttributeTypeObj;
	}
	/**
	 * @return the programObj
	 */
	public Program getProgramObj() {
		return programObj;
	}
	/**
	 * @param programObj the programObj to set
	 */
	public void setProgramObj(Program programObj) {
		this.programObj = programObj;
	}
	/**
	 * @return the programWorkflowObj
	 */
	public ProgramWorkflow getProgramWorkflowObj() {
		return programWorkflowObj;
	}
	/**
	 * @param programWorkflowObj the programWorkflowObj to set
	 */
	public void setProgramWorkflowObj(ProgramWorkflow programWorkflowObj) {
		this.programWorkflowObj = programWorkflowObj;
	}
	/**
	 * @return the programWorkflowStateObj
	 */
	public ProgramWorkflowState getProgramWorkflowStateObj() {
		return programWorkflowStateObj;
	}
	/**
	 * @param programWorkflowStateObj the programWorkflowStateObj to set
	 */
	public void setProgramWorkflowStateObj(
			ProgramWorkflowState programWorkflowStateObj) {
		this.programWorkflowStateObj = programWorkflowStateObj;
	}
	/**
	 * @return the userObj
	 */
	public User getUserObj() {
		return userObj;
	}
	/**
	 * @param userObj the userObj to set
	 */
	public void setUserObj(User userObj) {
		this.userObj = userObj;
	}
	/**
	 * @return the personObj
	 */
	public Person getPersonObj() {
		return personObj;
	}
	/**
	 * @param personObj the personObj to set
	 */
	public void setPersonObj(Person personObj) {
		this.personObj = personObj;
	}
	/**
	 * @return the listObj
	 */
	public List<Location> getListObj() {
		return listObj;
	}
	/**
	 * @param listObj the listObj to set
	 */
	public void setListObj(List<Location> listObj) {
		this.listObj = listObj;
	}
	/**
	 * @return the propertiesObj
	 */
	public Properties getPropertiesObj() {
		return propertiesObj;
	}
	/**
	 * @param propertiesObj the propertiesObj to set
	 */
	public void setPropertiesObj(Properties propertiesObj) {
		this.propertiesObj = propertiesObj;
	}
	/**
	 * @return the setObj
	 */
	public Set<Concept> getSetObj() {
		return setObj;
	}
	/**
	 * @param setObj the setObj to set
	 */
	public void setSetObj(Set<Concept> setObj) {
		this.setObj = setObj;
	}
}
